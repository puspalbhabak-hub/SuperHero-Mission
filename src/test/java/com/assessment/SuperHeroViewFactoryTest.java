package com.assessment;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class SuperHeroViewFactoryTest {

	@InjectMocks
	private SuperHeroViewFactory superHeroViewFactory;
	
	@Before
    public void before()
    {
		superHeroViewFactory = new SuperHeroViewFactory();
		MockitoAnnotations.initMocks(this);
    }
	
    @After
    public void after()
    {
    	superHeroViewFactory = null;
    }
    
    @Test
    public void testCreate()
    {
    	final SuperHero hero = MissionSuperHeroHelper.createHero("1");
    	final SuperHeroView result = superHeroViewFactory.create(hero);
    	assertEquals(hero.getId(), result.getId());
    	assertEquals(hero.getFirstName(), result.getFirstName());
    	assertEquals(hero.getLastName(), result.getLastName());
    	assertEquals(hero.getLastName(), result.getLastName());
    	assertEquals(hero.getSuperHeroName(), result.getSuperHeroName());
    }
}
