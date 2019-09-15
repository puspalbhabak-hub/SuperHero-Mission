package com.assessment.Controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.assessment.Controller.MissionController;
import com.assessment.Entity.Mission;
import com.assessment.Helper.MissionSuperHeroHelper;
import com.assessment.Service.MissionService;
import com.assessment.View.MissionView;

@SpringBootTest
public class MissionControllerTest {

	private static final String ID = "1";
	@InjectMocks
	private MissionController missionController;
	@Mock
	private MissionService missionService;
	
	@Before
    public void before()
    {
		missionService = new MissionService();
		missionController = new MissionController();
		MockitoAnnotations.initMocks(this);
    }
	
    @After
    public void after()
    {
    	missionService = null;
    	missionController = null;
    }
    
    @Test
    public void testGetMission()
    {
    	missionController.getMission(ID);
    	verify(missionService, times(1)).getMissionById(ID);
    }
    
    @Test
    public void testDelete()
    {
    	missionController.delete(ID);
    	verify(missionService, times(1)).delete(ID);
    }
    
    @Test
    public void testUpdateMission()
    {
    	final MissionView missionView = MissionSuperHeroHelper.createMissionView(ID);
    	missionController.updateMission(missionView);
    	verify(missionService, times(1)).update(missionView);
    }
    
    @Test
    public void testAddMission()
    {
    	final Mission mission = MissionSuperHeroHelper.createMission(ID);
    	missionController.addMission(mission);
    	verify(missionService, times(1)).save(mission);
    }
    
    @Test
    public void testGetAllMissions()
    {
    	missionController.getAllMissions();
    	verify(missionService, times(1)).findAll();
    }
}
