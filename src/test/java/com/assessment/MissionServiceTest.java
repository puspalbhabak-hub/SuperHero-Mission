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

public class MissionServiceTest {
	
	@Mock
	private MissionRepository missionRepository;
	
	@InjectMocks
	private MissionService missionService;
	
	@Spy
	private MissionFactory missionFactory;
	@Spy
	private MissionViewFactory missionViewFactory;
	@Spy
	private SuperHeroViewFactory superHeroViewFactory;
	
	@Before
    public void before()
    {
		missionService = new MissionService();
		missionFactory = new MissionFactory();
		superHeroViewFactory = new SuperHeroViewFactory();
		MockitoAnnotations.initMocks(this);
    }
    @After
    public void after()
    {
    	missionService = null;
    	missionFactory = null;
    	superHeroViewFactory = null;
    }
	
	@Test
	public void testSave()
	{
		final Mission mission = MissionSuperHeroHelper.createMission("1");
		when(missionRepository.save(mission)).thenReturn(mission);
		final ResponseEntity<String> save = missionService.save(mission);
		assertNotNull(save);
		assertEquals("New Mission created", save.getBody());
		assertTrue(save.getStatusCode() == HttpStatus.OK);
		verify(missionRepository, times(1)).save(mission);
	}
	
	@Test
	public void testSaveWhenNoData()
	{
		final ResponseEntity<String> save = missionService.save(null);
		assertNotNull(save);
		assertEquals("Null value. Mission can't be created", save.getBody());
		assertTrue(save.getStatusCode() == HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void testUpdateWhenNullId()
	{
		final MissionView missionView = new MissionView();
		missionView.setId(null);
		final ResponseEntity<String> result = missionService.update(missionView);
		assertNotNull(result);
		assertEquals("Id is null, cannot be updated", result.getBody());
		assertTrue(result.getStatusCode() == HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void testUpdateWhenIdNotExists()
	{
		final MissionView missionView = new MissionView();
		String id = "1";
		missionView.setId(id);
		when(missionRepository.findById(id)).thenReturn(Optional.empty());
		final ResponseEntity<String> result = missionService.update(missionView);
		assertNotNull(result);
		assertEquals("Mission does not exist", result.getBody());
		assertTrue(result.getStatusCode() == HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void testUpdateWhenIdExists()
	{
		final MissionView missionView = new MissionView();
		String id = "1";
		missionView.setId(id);
		missionView.setMissionName("New_Name");
		final Mission mission = MissionSuperHeroHelper.createMission(id);
		when(missionRepository.findById(id)).thenReturn(Optional.of(mission));
		when(missionFactory.update(mission, missionView)).thenReturn(mission);
		final ResponseEntity<String> result = missionService.update(missionView);
		assertNotNull(result);
		assertEquals("Mission is updated", result.getBody());
		assertTrue(result.getStatusCode() == HttpStatus.OK);
		verify(missionRepository, times(1)).findById(id);
		verify(missionFactory, times(1)).update(mission, missionView);
	}
	
	@Test
	public void testGetMissionById()
	{
		final String id = "1";
		final Mission mission = MissionSuperHeroHelper.createMission(id);
		final MissionView missionViewWithoutHero = MissionSuperHeroHelper.createMissionViewWithoutSuperHero("5");
		final SuperHeroView heroView = MissionSuperHeroHelper.createHeroView("8");
		final SuperHero superHero = MissionSuperHeroHelper.createHero("9");
		
		when(missionRepository.findById(id)).thenReturn(Optional.of(mission));
		when(missionViewFactory.create(mission)).thenReturn(missionViewWithoutHero);
		when(superHeroViewFactory.create(superHero)).thenReturn(heroView);
		
		final ResponseEntity<Object> result = missionService.getMissionById(id);
		assertNotNull(result);
		assertTrue(result.getBody().getClass().equals(MissionView.class));
		assertTrue(result.getStatusCode() == HttpStatus.OK);
		verify(missionRepository, times(1)).findById(id);
		verify(missionViewFactory, times(1)).create(mission);
	}
	
	@Test
	public void testGetMissionByIdWhenIdNotExists()
	{
		final String id = "1";
		when(missionRepository.findById(id)).thenReturn(Optional.empty());
		final ResponseEntity<Object> result = missionService.getMissionById(id);
		assertNotNull(result);
		assertEquals("Mission does not exist", result.getBody());
		assertTrue(result.getStatusCode() == HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void testDeleteWhenIdNotExists()
	{
		final String id = "1";
		when(missionRepository.findById(id)).thenReturn(Optional.empty());
		final ResponseEntity<String> result = missionService.delete(id);
		assertNotNull(result);
		assertEquals("Mission does not exist", result.getBody());
		assertTrue(result.getStatusCode() == HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void testDeleteWhenIdExists()
	{
		final String id = "1";
		final Mission mission = MissionSuperHeroHelper.createMission(id);
		
		when(missionRepository.findById(id)).thenReturn(Optional.of(mission));
		final ResponseEntity<String> result = missionService.delete(id);
		assertNotNull(result);
		assertEquals("Mission has been soft deleted", result.getBody());
		assertTrue(result.getStatusCode() == HttpStatus.OK);
	}
	

	@Test
	public void testFindAll()
	{
		final Mission mission = MissionSuperHeroHelper.createMission("1");
		final MissionView missionViewWithoutHero = MissionSuperHeroHelper.createMissionViewWithoutSuperHero("5");
		final SuperHeroView heroView = MissionSuperHeroHelper.createHeroView("8");
		final SuperHero superHero = MissionSuperHeroHelper.createHero("9");
		when(missionRepository.findAll()).thenReturn(Arrays.asList(mission));
		when(missionViewFactory.create(mission)).thenReturn(missionViewWithoutHero);
		when(superHeroViewFactory.create(superHero)).thenReturn(heroView);
		
		final ResponseEntity<List<MissionView>> result = missionService.findAll();
		assertNotNull(result);
		assertTrue(result.getBody().get(0).getClass().equals(MissionView.class));
		assertTrue(result.getStatusCode() == HttpStatus.OK);
		
	}
	
}

