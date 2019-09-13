package com.assessment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SuperHeroServiceTest {

	@Mock
	private MissionRepository missionRepository;
	@Mock
	private SuperHeroRepository superHeroRepository;
	
	@InjectMocks
	private SuperHeroService superHeroService;
	
	@Spy
	private SuperHeroFactory superHeroFactory;
	@Spy
	private MissionViewFactory missionViewFactory;
	@Spy
	private SuperHeroViewFactory superHeroViewFactory;
	
	@Before
    public void before()
    {
		superHeroService = new SuperHeroService();
		superHeroFactory = new SuperHeroFactory();
		superHeroViewFactory = new SuperHeroViewFactory();
		MockitoAnnotations.initMocks(this);
    }
    @After
    public void after()
    {
    	superHeroService = null;
    	superHeroFactory = null;
    	superHeroViewFactory = null;
    }
    
    @Test
	public void testUpdateWhenNullId()
	{
		final SuperHeroView heroView = new SuperHeroView();
		heroView.setId(null);
		final ResponseEntity<String> result = superHeroService.update(heroView);
		assertNotNull(result);
		assertEquals("Id is null, cannot be updated", result.getBody());
		assertTrue(result.getStatusCode() == HttpStatus.BAD_REQUEST);
	}
    
    @Test
	public void testUpdateWhenIdNotExists()
	{
    	final SuperHeroView heroView = new SuperHeroView();
		String id = "1";
		heroView.setId(id);
		when(superHeroRepository.findById(id)).thenReturn(Optional.empty());
		final ResponseEntity<String> result = superHeroService.update(heroView);
		assertNotNull(result);
		assertEquals("Super Hero does not exist", result.getBody());
		assertTrue(result.getStatusCode() == HttpStatus.BAD_REQUEST);
	}
    
    @Test
	public void testUpdateWhenIdExists()
	{
    	final SuperHeroView heroView = new SuperHeroView();
		String id = "1";
		heroView.setId(id);
		heroView.setSuperHeroName("New_Super_Hero");;
		final SuperHero superHero = MissionSuperHeroHelper.createHeroWithMissions(id);
		when(superHeroRepository.findById(id)).thenReturn(Optional.of(superHero));
		when(superHeroFactory.update(superHero, heroView)).thenReturn(superHero);
		final ResponseEntity<String> result = superHeroService.update(heroView);
		assertNotNull(result);
		assertEquals("Super hero is updated", result.getBody());
		assertTrue(result.getStatusCode() == HttpStatus.OK);
		verify(superHeroRepository, times(1)).findById(id);
		verify(superHeroFactory, times(1)).update(superHero, heroView);
	}
    
    @Test
	public void testGetSuperHeroById()
	{
		final String id = "1";
		final Mission mission = MissionSuperHeroHelper.createMission(id);
		final MissionView missionViewWithoutHero = MissionSuperHeroHelper.createMissionViewWithoutSuperHero("5");
		final SuperHeroView heroView = MissionSuperHeroHelper.createHeroView("8");
		final SuperHero superHero = MissionSuperHeroHelper.createHero("9");
		
		when(superHeroRepository.findById(id)).thenReturn(Optional.of(superHero));
		when(missionViewFactory.create(mission)).thenReturn(missionViewWithoutHero);
		when(superHeroViewFactory.create(superHero)).thenReturn(heroView);
		
		final ResponseEntity<Object> result = superHeroService.getSuperHeroById(id);
		assertNotNull(result);
		assertTrue(result.getBody().getClass().equals(SuperHeroView.class));
		assertTrue(result.getStatusCode() == HttpStatus.OK);
		verify(superHeroRepository, times(1)).findById(id);
		verify(superHeroViewFactory, times(1)).create(superHero);
	}
    
    @Test
	public void testGetSuperHeroByIdWhenIdNotExists()
	{
		final String id = "1";
		when(superHeroRepository.findById(id)).thenReturn(Optional.empty());
		final ResponseEntity<Object> result = superHeroService.getSuperHeroById(id);
		assertNotNull(result);
		assertEquals("Super Hero does not exist", result.getBody());
		assertTrue(result.getStatusCode() == HttpStatus.BAD_REQUEST);
	}
    
    @Test
	public void testFindAll()
	{
    	final SuperHero superHero = MissionSuperHeroHelper.createHeroWithMissions("1");
    	final Mission mission = MissionSuperHeroHelper.createMission("1");
		final MissionView missionViewWithoutHero = MissionSuperHeroHelper.createMissionViewWithoutSuperHero("5");
		final SuperHeroView heroView = MissionSuperHeroHelper.createHeroView("8");
		when(superHeroRepository.findAll()).thenReturn(Arrays.asList(superHero));
		when(missionViewFactory.create(mission)).thenReturn(missionViewWithoutHero);
		when(superHeroViewFactory.create(superHero)).thenReturn(heroView);
		
		final ResponseEntity<List<SuperHeroView>> result = superHeroService.findAll();
		assertNotNull(result);
		assertTrue(result.getBody().get(0).getClass().equals(SuperHeroView.class));
		assertTrue(result.getStatusCode() == HttpStatus.OK);
		
	}
    
    @Test
	public void testSave()
	{
		final SuperHero superHero = MissionSuperHeroHelper.createHeroWithMissions("1");
		when(superHeroRepository.save(superHero)).thenReturn(superHero);
		final ResponseEntity<String> save = superHeroService.save(superHero);
		assertNotNull(save);
		assertEquals("New Super Hero created", save.getBody());
		assertTrue(save.getStatusCode() == HttpStatus.OK);
		verify(superHeroRepository, times(1)).save(superHero);
	}
	
	@Test
	public void testSaveWhenNoData()
	{
		final ResponseEntity<String> save = superHeroService.save(null);
		assertNotNull(save);
		assertEquals("Null value. Super Hero can't be created", save.getBody());
		assertTrue(save.getStatusCode() == HttpStatus.BAD_REQUEST);
	}
}
