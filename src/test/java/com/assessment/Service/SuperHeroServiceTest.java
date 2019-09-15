package com.assessment.Service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.assessment.Entity.Mission;
import com.assessment.Entity.SuperHero;
import com.assessment.Factory.MissionViewFactory;
import com.assessment.Factory.SuperHeroFactory;
import com.assessment.Factory.SuperHeroViewFactory;
import com.assessment.Helper.MissionSuperHeroHelper;
import com.assessment.Repository.MissionRepository;
import com.assessment.Repository.SuperHeroRepository;
import com.assessment.Service.SuperHeroService;
import com.assessment.Utility.HeroMissionUtility;
import com.assessment.View.MissionView;
import com.assessment.View.SuperHeroView;

@SpringBootTest
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
    public void testAddMissionsWhenSuperHeroNotExists()
    {
    	final HeroMissionUtility heroMissionUtility = new HeroMissionUtility();
    	final String id = "1";
    	heroMissionUtility.setHeroId(id);
    	heroMissionUtility.setMissionsIds(Arrays.asList(id));
    	
    	when(superHeroRepository.findById(id)).thenReturn(Optional.empty());
    	final ResponseEntity<String> result = superHeroService.addMissions(heroMissionUtility);
    	assertNotNull(result);
		assertEquals("Super Hero does not exist", result.getBody());
		assertTrue(result.getStatusCode() == HttpStatus.BAD_REQUEST);
    }
    
    @Test
    public void testAddMissionsWhenMissionNull()
    {
    	final HeroMissionUtility heroMissionUtility = new HeroMissionUtility();
    	heroMissionUtility.setMissionsIds(null);
    	checkForBadRequestAddMissions(heroMissionUtility);
    }
    
	private void checkForBadRequestAddMissions(final HeroMissionUtility heroMissionUtility) {
		final String id = "1";
    	heroMissionUtility.setHeroId(id);    	
    	
    	final SuperHero superHero = MissionSuperHeroHelper.createHeroWithMissions(id);
		when(superHeroRepository.findById(id)).thenReturn(Optional.of(superHero));
		
		final ResponseEntity<String> result = superHeroService.addMissions(heroMissionUtility);
    	assertNotNull(result);
		assertEquals("No Missions exist to add", result.getBody());
		assertTrue(result.getStatusCode() == HttpStatus.BAD_REQUEST);
	}
    
    @Test
    public void testAddMissionsWhenMissionEmpty()
    {
    	final HeroMissionUtility heroMissionUtility = new HeroMissionUtility();
    	heroMissionUtility.setMissionsIds(Collections.emptyList());
    	checkForBadRequestAddMissions(heroMissionUtility);
    }
    
    @Test
    public void testAddTwoMissionsWithOneRemoved()
    {
    	final String id1 = "1";
    	final String id2 = "2";
    	final HeroMissionUtility heroMissionUtility = new HeroMissionUtility();
    	heroMissionUtility.setHeroId(id1); 
    	heroMissionUtility.setMissionsIds(Arrays.asList(id1, id2));
    	
    	final SuperHero superHero = MissionSuperHeroHelper.createHeroWithMissions(id1);
		when(superHeroRepository.findById(id1)).thenReturn(Optional.of(superHero));
		
		final Mission mission1 = MissionSuperHeroHelper.createMissionWithoutHeroes(id1);
		final Mission mission2 = MissionSuperHeroHelper.createMissionWithoutHeroes(id2);
		mission2.setDeleted(true);
		when(missionRepository.findById(id1)).thenReturn(Optional.of(mission1));
		when(missionRepository.findById(id2)).thenReturn(Optional.of(mission2)); 
		
		final ResponseEntity<String> result = superHeroService.addMissions(heroMissionUtility);
    	assertNotNull(result);
		assertEquals("1 Missions added", result.getBody());
		assertTrue(result.getStatusCode() == HttpStatus.OK);
		verify(missionRepository, times(1)).findById(id1);
		verify(missionRepository, times(1)).findById(id2);
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
	public void testDeleteWhenNullId()
	{
		final ResponseEntity<String> result = superHeroService.delete(null);
		assertNotNull(result);
		assertEquals("Null value. Super Hero can't be deleted", result.getBody());
		assertTrue(result.getStatusCode() == HttpStatus.BAD_REQUEST);
	}
    
    @Test
    public void testDeleteMissionsWhenMissionNotExists()
    {
    	final String id = "1";    	
    	when(superHeroRepository.findById(id)).thenReturn(Optional.of(new SuperHero()));
    	when(missionRepository.findById(id)).thenReturn(Optional.empty());
    	final ResponseEntity<String> result = superHeroService.deleteMission(id, id);
    	assertNotNull(result);
		assertEquals("Mission does not exist", result.getBody());
		assertTrue(result.getStatusCode() == HttpStatus.BAD_REQUEST);
    }
    
    @Test
    public void testDeleteMissionsWhenMissionNotComplete()
    {
    	final String id = "1";  
    	
    	final SuperHero superHero = MissionSuperHeroHelper.createHero(id);
    	final Mission mission = MissionSuperHeroHelper.createMissionWithoutHeroes(id);
    	assertFalse(mission.getCompleted());
    	final List<Mission> missions = new ArrayList<>();
    	missions.add(mission);
    	superHero.setMissions(missions);
    	
    	when(superHeroRepository.findById(id)).thenReturn(Optional.of(superHero));
    	when(missionRepository.findById(id)).thenReturn(Optional.of(mission));
    	
    	final ResponseEntity<String> result = superHeroService.deleteMission(id, id);
    	assertNotNull(result);
		assertEquals("Mission deleted from super hero", result.getBody());
		assertTrue(result.getStatusCode() == HttpStatus.OK);
		verify(superHeroRepository, times(1)).save(superHero);
    }
    
    @Test
    public void testDeleteMissionsWhenMissionCompleted()
    {
    	final String id = "1";  
    	
    	final SuperHero superHero = MissionSuperHeroHelper.createHero(id);
    	final Mission mission = MissionSuperHeroHelper.createMissionWithoutHeroes(id);
    	mission.setCompleted(true);
    	final List<Mission> missions = new ArrayList<>();
    	missions.add(mission);
    	superHero.setMissions(missions);
    	
    	when(superHeroRepository.findById(id)).thenReturn(Optional.of(superHero));
    	when(missionRepository.findById(id)).thenReturn(Optional.of(mission));
    	
    	final ResponseEntity<String> result = superHeroService.deleteMission(id, id);
    	assertNotNull(result);
		assertEquals("Mission is complete. Cannot be removed", result.getBody());
		assertTrue(result.getStatusCode() == HttpStatus.BAD_REQUEST);
		verify(superHeroRepository, times(0)).save(superHero);
    }
    
    @Test
    public void testDeleteMissionsWhenMissionNotWithinSuperHero()
    {
    	final String id = "1";  
    	final String deleteMissionId = "2";  
    	
    	final SuperHero superHero = MissionSuperHeroHelper.createHero(id);
    	final Mission mission = MissionSuperHeroHelper.createMissionWithoutHeroes(id);
    	final List<Mission> missions = new ArrayList<>();
    	missions.add(mission);
    	superHero.setMissions(missions);
    	
    	final Mission deleteMission = MissionSuperHeroHelper.createMissionWithoutHeroes(deleteMissionId);
    	
    	when(superHeroRepository.findById(id)).thenReturn(Optional.of(superHero));
    	when(missionRepository.findById(deleteMissionId)).thenReturn(Optional.of(deleteMission));
    	
    	final ResponseEntity<String> result = superHeroService.deleteMission(id, deleteMissionId);
    	assertNotNull(result);
		assertEquals("Super Hero does not have this mission", result.getBody());
		assertTrue(result.getStatusCode() == HttpStatus.BAD_REQUEST);
		verify(superHeroRepository, times(0)).save(superHero);
    }
    
    @Test
    public void testDeleteMissionsWhenSuperHeroNotExists()
    {
    	final String id = "1";    	
    	when(superHeroRepository.findById(id)).thenReturn(Optional.empty());
    	final ResponseEntity<String> result = superHeroService.deleteMission(id, id);
    	assertNotNull(result);
		assertEquals("Super Hero does not exist", result.getBody());
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
   	public void testDeleteWhenIdExists()
   	{
   		final String id = "1";
   		final SuperHero superHero = MissionSuperHeroHelper.createHeroWithMissions(id);
   		when(superHeroRepository.findById(id)).thenReturn(Optional.of(superHero));
   		doNothing().when(superHeroRepository).deleteByHeroId(id);
		final ResponseEntity<String> result = superHeroService.delete(id);
   		assertNotNull(result);
   		assertEquals("Super Hero deleted", result.getBody());
   		assertTrue(result.getStatusCode() == HttpStatus.OK);
   		verify(superHeroRepository, times(1)).deleteByHeroId(id);
   	}
    
    @Test
	public void testDeleteWhenIdNotExists()
	{
		String id = "1";
		when(superHeroRepository.findById(id)).thenReturn(Optional.empty());
		final ResponseEntity<String> result = superHeroService.delete(id);
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
		verify(superHeroRepository, times(1)).save(superHero);
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
		verify(superHeroRepository, times(1)).findAll();
		verify(superHeroViewFactory, times(1)).create(superHero);
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
