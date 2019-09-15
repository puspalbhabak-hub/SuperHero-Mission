package com.assessment.Factory;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.assessment.Entity.Mission;
import com.assessment.Factory.MissionViewFactory;
import com.assessment.Helper.MissionSuperHeroHelper;
import com.assessment.View.MissionView;

@SpringBootTest
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
    	assertEquals(mission.getDeleted(), result.getDeleted());
    	assertEquals(mission.getCompleted(), result.getCompleted());
    }
}
