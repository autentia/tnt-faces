/**
 * Copyright 2008 Autentia Real Business Solutions S.L. This file is part of Autentia WUIJA. Autentia WUIJA is free
 * software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, version 3 of the License. Autentia WUIJA is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details. You should have received a copy of
 * the GNU Lesser General Public License along with Autentia WUIJA. If not, see <http://www.gnu.org/licenses/>.
 */

package com.autentia.tnt.faces.components.query.widget;


/**
 * Este datatable con buscador opera a nivel de {@link Criteria}. Es un refinamiento de la clase
 * {@link QueriedDataTable} de forma que resulte más sencillo hacer los típicos datatables con un buscador por las
 * propiedades de un entidad (por ejemplo usando los buscadores {@link AdvancedQuery} o {@link QuickQuery}).
 * <p>
 * A esta clase se le pasa la {@link EntityCriteria} que se usará al dar al botón de búsqueda. De esta forma se puede
 * preparar buscadores complejos con condiciones predeterminadas que no puede cambiar el usuario.
 * 
 * @param <T> el tipo con el que trabaja esta clase.
 */
public class CriteriaQueriedDataTable<T> extends QueriedDataTable<T> {

//	public CriteriaQueriedDataTable(Property[] propertiesOfList, final EntityCriteria entityCriteria,
//			Query dataTableQuery, final Dao dao, CsvReportsService csvReportsService) {
//
//		final PagedListDataProvider<T> pagedListDataProvider = new PagedListDataProvider<T>() {
//
//			public Pair<List<T>, Long> getPage(int firstRow, int pageSize) {
//				entityCriteria.addOrder(pagedDataTable.getSortColumn(), pagedDataTable.isSortAscending());
//				return dao.findAndCount(entityCriteria, firstRow, pageSize);
//			}
//		};
//
//		init(propertiesOfList, pagedListDataProvider, dataTableQuery, csvReportsService);
//	}
}
