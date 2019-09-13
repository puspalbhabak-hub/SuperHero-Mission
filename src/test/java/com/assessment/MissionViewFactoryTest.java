package com.assessment;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class MissionViewFactoryTest {

	@InjectMocks
	private MissionViewFactory missionViewFactory;
	
	@Before
    public void before()
    {
		missionViewFactory = new MissionViewFactory();
		MockitoAnnotations.initMocks(this);
    }
	
    @After
    public void after()
    {
    	missionViewFactory = null;
    }
    
    @Test
    public void testCreate()
    {
    	final Mission mission = MissionSuperHeroHelper.createMission("1");
    	final MissionView result = missionViewFactory.create(mission);
    	
    	assertEquals(mission.getId(), result.getId());
    	assertEquals(mission.getMissionName(), result.getMissionName());
    	assertEquals(mission.isDeleted(), result.isDeleted());
    	assertEquals(mission.isCompleted(), result.isCompleted());
    }
}
