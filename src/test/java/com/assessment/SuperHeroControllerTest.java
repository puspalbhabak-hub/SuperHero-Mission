package com.assessment;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SuperHeroControllerTest {

	private static final String ID2 = "2";
	private static final String ID1 = "1";
	@InjectMocks
	private SuperHeroController superHeroController;
	@Mock
	private SuperHeroService superHeroService;
	
	@Before
    public void before()
    {
		superHeroService = new SuperHeroService();
		superHeroController = new SuperHeroController();
		MockitoAnnotations.initMocks(this);
    }
	
    @After
    public void after()
    {
    	superHeroService = null;
    	superHeroController = null;
    }
    
    @Test
    public void testDeleteMission()
    {
    	superHeroController.deleteMission(ID1, ID2);
    	verify(superHeroService, times(1)).deleteMission(ID1, ID2);
    }
    
    @Test
    public void testAddMissionsToSuperHero()
    {
    	final HeroMissionUtility heroMissionUtility = new HeroMissionUtility();
    	superHeroController.addMissionsToSuperHero(heroMissionUtility);
    	verify(superHeroService, times(1)).addMissions(heroMissionUtility);
    }
    
    @Test
    public void testDelete()
    {
    	superHeroController.delete(ID1);
    	verify(superHeroService, times(1)).delete(ID1);
    }
    
    @Test
    public void testGetSuperHeroById()
    {
    	superHeroController.getSuperHeroById(ID1);
    	verify(superHeroService, times(1)).getSuperHeroById(ID1);
    }
    
    @Test
    public void testUpdateSuperHero()
    {
    	final SuperHeroView heroView = MissionSuperHeroHelper.createHeroView(ID2);
    	superHeroController.updateSuperHero(heroView);
    	verify(superHeroService, times(1)).update(heroView);
    }
    
    @Test
    public void testAddSuperHero()
    {
    	final SuperHero hero = MissionSuperHeroHelper.createHero(ID2);
    	superHeroController.addSuperHero(hero);
    	verify(superHeroService, times(1)).save(hero);
    }
    
    @Test
    public void testGetAllSuperHeroes()
    {
    	superHeroController.getAllSuperHeroes();
    	verify(superHeroService, times(1)).findAll();
    }
}
