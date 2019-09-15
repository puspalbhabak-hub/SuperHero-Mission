package com.assessment.Factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import com.assessment.Entity.Mission;
import com.assessment.Entity.SuperHero;
import com.assessment.Factory.MissionFactory;
import com.assessment.Factory.SuperHeroFactory;
import com.assessment.Helper.MissionSuperHeroHelper;
import com.assessment.View.MissionView;
import com.assessment.View.SuperHeroView;

@SpringBootTest
public class SuperHeroFactoryTest {

	private static final String ID = "1";
	@InjectMocks
	private SuperHeroFactory superHeroFactory;
	@Spy
	private MissionFactory missionFactory;
	
	@Before
    public void before()
    {
		missionFactory = new MissionFactory();
		superHeroFactory = new SuperHeroFactory();
		MockitoAnnotations.initMocks(this);
    }
	
    @After
    public void after()
    {
    	missionFactory = null;
    	superHeroFactory = null;
    }
    
    @Test
    public void testUpdateWhenNoData()
    {
    	final SuperHero superHero = MissionSuperHeroHelper.createHeroWithMissions(ID);
    	final SuperHeroView superHeroView = new SuperHeroView();
    	
    	final SuperHero result = superHeroFactory.update(superHero, superHeroView);
    	
    	assertEquals(superHero.getId(), result.getId());
    	assertEquals(superHero.getFirstName(), result.getFirstName());
    	assertEquals(superHero.getLastName(), result.getLastName());
    	assertEquals(superHero.getMissions(), result.getMissions());
    	assertEquals(superHero.getSuperHeroName(), result.getSuperHeroName());
    }
    
    @Test
    public void testUpdateWhenMissionEmpty()
    {
    	final SuperHero superHero = MissionSuperHeroHelper.createHeroWithMissions(ID);
    	final SuperHeroView superHeroView = new SuperHeroView();
    	superHeroView.setMissions(Collections.emptyList());
    	
    	final SuperHero result = superHeroFactory.update(superHero, superHeroView);
    	assertEquals(superHero.getMissions(), result.getMissions());
    }
    
    @Test
    public void testUpdateWhenOneMissionChanged()
    {
    	final SuperHero superHero = MissionSuperHeroHelper.createHeroWithMissions(ID);
    	final SuperHeroView superHeroView = new SuperHeroView();
    	final Mission mission = superHero.getMissions().get(0);
    	final MissionView missionView = MissionSuperHeroHelper.convertToViewMission(mission);
    	assertFalse(missionView.getCompleted());
    	missionView.setCompleted(true);
    	missionView.setMissionName("New_Mission_1");
    	final List<MissionView> missionViews = new ArrayList<>();
    	missionViews.add(missionView);
    	superHeroView.setMissions(missionViews);
    	
    	final SuperHero result = superHeroFactory.update(superHero, superHeroView);
    	final List<Mission> resultMissions = result.getMissions();
		assertNotNull(resultMissions);
		assertTrue(resultMissions.size() == 2);
		
		final List<Mission> missionUpdated = resultMissions.stream().filter(heroMission -> 
					heroMission.getId().equals(missionViews.get(0).getId())).collect(Collectors.toList());
		final Mission checkUpdate = missionUpdated.get(0);
		assertTrue(checkUpdate.getCompleted());
		assertEquals(missionView.getMissionName(), checkUpdate.getMissionName());
		
		final List<Mission> missionNotUpdated = resultMissions.stream().filter(heroMission -> 
					!heroMission.getId().equals(missionViews.get(0).getId())).collect(Collectors.toList());
		List<Mission> originalMission = superHero.getMissions().stream().filter(heroMission -> 
					!heroMission.getId().equals(missionViews.get(0).getId())).collect(Collectors.toList());
		assertEquals(originalMission.get(0), missionNotUpdated.get(0));
    }
    
    @Test
    public void testUpdateWhenUpdateDataOtherThanMissions()
    {
    	final SuperHero superHero = MissionSuperHeroHelper.createHero(ID);
    	final SuperHeroView superHeroView = new SuperHeroView();
    	superHeroView.setFirstName("Update_First");
    	superHeroView.setLastName("Update_Last");
    	superHeroView.setSuperHeroName("Update_Hero");
    	
    	final SuperHero result = superHeroFactory.update(superHero, superHeroView);
    	
    	assertEquals(superHero.getId(), result.getId());
    	assertEquals("Update_First", result.getFirstName());
    	assertEquals("Update_Last", result.getLastName());
    	assertEquals("Update_Hero", result.getSuperHeroName());
    }
}
