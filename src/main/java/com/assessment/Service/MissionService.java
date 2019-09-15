package com.assessment.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.assessment.Entity.Mission;
import com.assessment.Factory.MissionFactory;
import com.assessment.Factory.MissionViewFactory;
import com.assessment.Factory.SuperHeroViewFactory;
import com.assessment.Repository.MissionRepository;
import com.assessment.View.MissionView;
import com.assessment.View.SuperHeroView;

@Service
public class MissionService {

	@Autowired
	private MissionRepository missionRepository;
	
	private SuperHeroViewFactory superHeroViewFactory;
	private MissionViewFactory missionViewFactory;
	private MissionFactory missionFactory;
	
	public MissionService() {
		superHeroViewFactory = new SuperHeroViewFactory();
		missionViewFactory = new MissionViewFactory();
		missionFactory = new MissionFactory();
	}
	
	public ResponseEntity<String> save(final Mission mission)
	{
		if(mission == null) {
			return new ResponseEntity<>(
	        		"Null value. Mission can't be created", 
			          HttpStatus.BAD_REQUEST);
		}
		missionRepository.save(mission);
		return new ResponseEntity<>(
        		"New Mission created", 
		          HttpStatus.OK);
	}
	
	public ResponseEntity<String> update(final MissionView missionView){
		if(missionView.getId() == null) {
			return new ResponseEntity<>(
			          "Id is null, cannot be updated", 
			          HttpStatus.BAD_REQUEST);
		}
		
		final Optional<Mission> optionalMission = missionRepository.findById(missionView.getId());
    	if (!optionalMission.isPresent()) {
			return new ResponseEntity<>(
			          "Mission does not exist", 
			          HttpStatus.BAD_REQUEST);
		}
    	
    	final Mission mission = optionalMission.get();
    	missionFactory.update(mission, missionView);
    	missionRepository.save(mission);
    	return new ResponseEntity<>(
        		"Mission is updated", 
		          HttpStatus.OK);  
	}
	
	
	public ResponseEntity<List<MissionView>> findAll(){  
        List<MissionView> missionRecords = new ArrayList<>();  
        missionRepository.findAll().forEach(mission -> {
        	final MissionView missionView = createMissionView(mission);
        	missionRecords.add(missionView);
        });
        return new ResponseEntity<>(
        		missionRecords, 
		          HttpStatus.OK);  
    } 
	
    public ResponseEntity<Object> getMissionById(final String id){  
    	final Optional<Mission> optionalMission = missionRepository.findById(id);
    	if (!optionalMission.isPresent()) {
			return new ResponseEntity<>(
			          "Mission does not exist", 
			          HttpStatus.BAD_REQUEST);
		}
    	
    	final Mission mission = optionalMission.get();
    	final MissionView missionView = createMissionView(mission);
    	return new ResponseEntity<>(
    			missionView, 
		          HttpStatus.OK);
    }

	private MissionView createMissionView(final Mission mission) {
		final MissionView missionView = missionViewFactory.create(mission);
    	final List<SuperHeroView> superHeroViews = new ArrayList<>();

    	mission.getSuperHeroes().forEach(superHero -> 
    	{
    		final SuperHeroView superHeroView = superHeroViewFactory.create(superHero);
    		superHeroViews.add(superHeroView);
    	});
    	missionView.setSuperHeroes(superHeroViews);
		return missionView;
	} 
   
    public ResponseEntity<String> delete(final String id){  
    	final Optional<Mission> optionalMission = missionRepository.findById(id);
    	if (!optionalMission.isPresent()) {
			return new ResponseEntity<>(
			          "Mission does not exist", 
			          HttpStatus.BAD_REQUEST);
		} 
    	final Mission mission = optionalMission.get();
    	missionFactory.setDelete(mission);
    	missionRepository.save(mission);
		return new ResponseEntity<>(
        		"Mission has been soft deleted", 
		          HttpStatus.OK);
    	
    }
  
}
