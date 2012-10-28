/**
 * Copyright 2012 Autentia Real Business Solutions S.L.
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

package com.autentia.tnt.faces.components.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InMemoryCacheFactory implements CacheFactory{

	private ConcurrentMap<String, Map<String,Object>> regions = new ConcurrentHashMap<String, Map<String,Object>>();
	
	@Override
	public Map<String, Object> getCache(String region) {
		if (!regions.containsKey(region)){
			final Map<String,Object> values = new ConcurrentHashMap<String,Object>();
			regions.put(region, values);
			return values;
		}
		return regions.get(region);
	}

	public List<String> getCacheNames(){
		return new ArrayList<String>(regions.keySet());
	}
	
}
