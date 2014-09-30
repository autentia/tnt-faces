/**

 * Copyright 2014 Autentia Real Business Solutions S.L.
 * 
 * This file is part of the code that develops in Autentia.
 * 
 * This code is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.autentia.tnt.faces.components.query.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autentia.tnt.faces.utils.ReflectionUtils;

/**
 * Esta clase representa un {@link Criteria} de búsqueda sobre una entidad (entidad 'raiz).
 * <p>
 * Esta clase no implementa la interfaz {@link Criterion} ya que no se quiere que se puedan anidar las
 * {@link EntityCriteria}. Esto es porque la entidad 'raiz' sobre la que se hace la consulta, es sólo una (la que está
 * definida en esta clase).
 * <p>
 * Para hacer un join de esta entidad con otra entidad se puede usar el método {@link EntityCriteria#join(String)}. En
 * este método indicaremos la propiedad de la entidad asociada con esta clase por la que se quiere hacer el join. El
 * método devolverá una nueva {@link EntityCriteria} con la que podremos refinar la búsqueda, e incluso hacer joins
 * anidados.
 * 
 * @see Criterion
 */
public class EntityCriteria extends Criteria {

	/**
	 * Se podría usar directamente la clase {@link Pair}, pero se crea esta clase para que al leer el código, este tenga
	 * más sentido. La izquierda representa la propiedad sobre la que se hace el join, y la derecha el propio join.
	 */
	private class PropertyNameAndJoin extends Pair<String, EntityCriteria> {

		private static final long serialVersionUID = -3512647270379867332L;

		private final String propertyName;
		
		private final  EntityCriteria entityCriteria;
		
		public PropertyNameAndJoin(String propertyName, EntityCriteria entityCriteria) {
			this.propertyName = propertyName;
			this.entityCriteria = entityCriteria;
		}

		EntityCriteria getJoin() {
			return getRight();
		}

		String getPropertyName() {
			return getLeft();
		}

		@Override
		public EntityCriteria setValue(EntityCriteria entityCriteria) {
			return entityCriteria;
		}

		@Override
		public String getLeft() {
			return propertyName;
		}

		@Override
		public EntityCriteria getRight() {
			return entityCriteria;
		}

	}

	private static final Logger LOG = LoggerFactory.getLogger(EntityCriteria.class);

	private final String alias;

	/** Clase de la entidad sobre la que se va a aplicar la criteria. */
	private final Class<?> entityClass;

	private final List<Object> hqlValues = new ArrayList<Object>();

	/**
	 * Mapa donde guardamos los joins con otras entidades. La clave será el alias del join (no podemos usar el nombre de
	 * la propiedad del join, porque podemos hacer varios joins por la misma propiedad.
	 */
	private final Map<String, PropertyNameAndJoin> joins = new LinkedHashMap<String, PropertyNameAndJoin>();

	private boolean sortAscending;

	private String sortProperty;

	/**
	 * Es como llamar a: <code>new EntityCriteria(entityClass, {@link MatchMode#ALL})</code>.
	 * 
	 * @param entityClass la clase sobre el que aplica este criterio.
	 */
	public EntityCriteria(Class<?> entityClass) {
		this(entityClass, MatchMode.ALL);
	}

	/**
	 * Crea una nueva instancia de esta clase.
	 * 
	 * @param entityClass la clase sobre el que aplica este criterio.
	 * @param matchMode el {@link MatchMode} para los criterios.
	 */
	public EntityCriteria(Class<?> entityClass, MatchMode matchMode) {
		this(ClassUtils.getShortClassName(entityClass), entityClass, matchMode);
	}

	private EntityCriteria(String alias, Class<?> entityClass, MatchMode matchMode) {
		super(matchMode);
		this.alias = alias;
		this.entityClass = entityClass;
	}

	private void addJoinsHql(StringBuilder hql, StringBuilder restrictionsHql) {
		if (!joins.isEmpty()) {
			if (restrictionsHql.length() > 0) {
				// Ya tiene restricciones en el 'where' así que hay que 'sumar' las restricciones de los joins
				restrictionsHql.append(" and ");
			}

			for (Map.Entry<String, PropertyNameAndJoin> entryMap : joins.entrySet()) {
				final String joinAlias = entryMap.getKey();
				final PropertyNameAndJoin nameAndJoin = entryMap.getValue();

				hql.append(" inner join ").append(alias).append(".").append(nameAndJoin.getPropertyName()).append(
						" as ").append(joinAlias);

				final int restrictionsHqlLength = restrictionsHql.length();

				nameAndJoin.getJoin().addHqlRestrictions(joinAlias, restrictionsHql, hqlValues);

				if (restrictionsHqlLength != restrictionsHql.length()) {
					restrictionsHql.append(" and ");
				}

			}
			restrictionsHql.setLength(restrictionsHql.length() - 5); // Remove last operand ' and '
		}
	}

	public void addOrder(String sortPropertyName, boolean isSortAscending) {
		this.sortProperty = sortPropertyName;
		this.sortAscending = isSortAscending;
	}

	private void addRawHql(StringBuilder hql) {
		hql.append("from ").append(entityClass.getSimpleName()).append(" as ").append(alias);

		// XXX se podría separar la generación de la cadena de la recuparción de los valores, para optimizar ??
		// La query se compone una vez y los valores se recalculan n veces
		final StringBuilder restrictionsHql = new StringBuilder();
		hqlValues.clear();

		addHqlRestrictions(alias, restrictionsHql, hqlValues);

		addJoinsHql(hql, restrictionsHql);

		if (restrictionsHql.length() > 0) {
			hql.append(" where ").append(restrictionsHql);
		}
	}

	public void clearJoins() {
		joins.clear();
	}

	private String createAlias(String propertyName) {
		return propertyName + joins.size();
	}

	public String getAlias() {
		return alias;
	}

	public Object[] getHqlValues() {
		return hqlValues.toArray();
	}

	/**
	 * Hace un join de la entidad representada por esta {@link Criteria} con la entidad referenciada por la propiedad
	 * que se pasa como parámetro.
	 * 
	 * @param propertyName el nombre de la propiedad de la entidad representada por esta {@link Criteria}, por la que se
	 *            quiere hacer el join.
	 * @return una nueva {@link EntityCriteria} sobre la entidad de la propidad indicada. Podemos usar estas
	 *         {@link EntityCriteria} para restringir el join.
	 */
	public EntityCriteria join(String propertyName) {
		
		Class<?> propertyClass = propertyClass = ReflectionUtils.getPropertyClass(entityClass, propertyName);
		
		if (propertyClass == null){
			throw new IllegalArgumentException("The class " + entityClass.getName() + " doesn't have the property "
					+ propertyName + ". Review if you spelled it correctly.");
		}

		final String joinAlias = createAlias(propertyName);
		final EntityCriteria join = new EntityCriteria(joinAlias, propertyClass, MatchMode.ALL);
		joins.put(joinAlias, new PropertyNameAndJoin(propertyName, join));

		return join;
	}

	private void log(String hql) {
		LOG.debug("HQL to execute: {} ", hql);
		if (LOG.isTraceEnabled() && !hqlValues.isEmpty()) {
			final StringBuilder sb = new StringBuilder();
			for (Object obj : hqlValues) {
				sb.append(obj).append(" --- ");
			}
			sb.setLength(sb.length() - 5); // para quitar el último ' --- '
			LOG.trace("HQL to execute, values: " + sb.toString());
		}
	}

	public String toCountHql() {
		final StringBuilder hql = new StringBuilder("select count(");
		if (!joins.isEmpty()) {
			hql.append("distinct ");
		}
		hql.append(alias).append(") ");

		addRawHql(hql);

		final String hqlToExecute = hql.toString();

		log(hqlToExecute);

		return hqlToExecute;
	}

	public String toHql() {
		final StringBuilder hql = new StringBuilder();
		if (!joins.isEmpty()) {
			hql.append("select distinct ").append(alias).append(" ");
		}

		addRawHql(hql);

		if (sortProperty != null) {
			hql.append(" order by ").append(alias).append(".").append(sortProperty);
			if (!sortAscending) {
				hql.append(" desc");
			}
		}

		final String hqlToExecute = hql.toString();

		log(hqlToExecute);

		return hqlToExecute;
	}

}
