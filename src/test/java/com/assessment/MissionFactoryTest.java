package com.assessment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class MissionFactoryTest {
	
	private static final String ID = "1";
	@InjectMocks
	private MissionFactory missionFactory;
	
	@Before
    public void before()
    {
		missionFactory = new MissionFactory();
		MockitoAnnotations.initMocks(this);
    }
	
    @After
    public void after()
    {
    	missionFactory = null;
    }
    
    @Test
    public void testCreate()
    {
    	final MissionView missionView = MissionSuperHeroHelper.createMissionViewWithoutSuperHero(ID);
    	final Mission result = missionFactory.create(missionView);
    	assertEquals(missionView.getId(), result.getId());
    	assertEquals(missionView.getMissionName(), result.getMissionName());
    	assertEquals(missionView.isDeleted(), result.isDeleted());
    	assertEquals(missionView.isCompleted(), result.isCompleted());
    }
    
    @Test
    public void testSetDelete()
    {
    	final Mission mission = MissionSuperHeroHelper.createMission(ID);
    	assertFalse(mission.isDeleted());
    	
    	final Mission result = missionFactory.setDelete(mission);
    	assertTrue(result.isDeleted());
    }
    
    @Test
    public void testUpdate()
    {
    	final Mission mission = MissionSuperHeroHelper.createMission(ID);
    	assertTrue(mission.getMissionName().equals("Mission"));
    	
    	final MissionView missionView = new MissionView();
    	missionView.setMissionName("Update_Mission");
    	
    	final Mission result = missionFactory.update(mission, missionView);
    	assertTrue(result.getMissionName().equals("Update_Mission"));
    }
    
    @Test
    public void testUpdateWhenNoValue()
    {
    	final Mission mission = MissionSuperHeroHelper.createMission(ID);
    	assertTrue(mission.getMissionName().equals("Mission"));
    	
    	final MissionView missionView = new MissionView();
    	
    	final Mission result = missionFactory.update(mission, missionView);
    	assertTrue(result.getMissionName().equals("Mission"));
    }

}
