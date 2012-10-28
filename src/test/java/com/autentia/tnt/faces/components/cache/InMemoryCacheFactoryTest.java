package com.autentia.tnt.faces.components.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class InMemoryCacheFactoryTest {

	private InMemoryCacheFactory inMemoryCacheFactory;
	
	@Before
	public void init(){
		inMemoryCacheFactory = new InMemoryCacheFactory();	
	}
	
	@Test
	public void shouldInitializeRegion(){
		assertTrue(inMemoryCacheFactory.getCache("header").values().isEmpty());
		inMemoryCacheFactory.getCache("header").put("key1", String.valueOf("test"));
		assertEquals(1, inMemoryCacheFactory.getCache("header").values().size());
	}
	
	@Test
	public void shouldProvideAllCacheNames(){
		
		inMemoryCacheFactory.getCache("header");
		assertEquals(1, inMemoryCacheFactory.getCacheNames().size());
	} 
}
