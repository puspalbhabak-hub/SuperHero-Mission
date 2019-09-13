package com.assessment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SuperHeroService {

	@Autowired
	private SuperHeroRepository superHeroRepository;
	@Autowired
	private MissionRepository missionRepository;
	
	private SuperHeroViewFactory superHeroViewFactory;
	private MissionViewFactory missionViewFactory;
	private SuperHeroFactory superHeroFactory;
	
	public SuperHeroService()
	{
		superHeroViewFactory = new SuperHeroViewFactory();
		missionViewFactory = new MissionViewFactory();
		superHeroFactory = new SuperHeroFactory();
	}
	
	public ResponseEntity<String> save(final SuperHero superHero)
	{
		if(superHero == null) {
			return new ResponseEntity<>(
	        		"Null value. Super Hero can't be created", 
			          HttpStatus.BAD_REQUEST);
		}
		superHeroRepository.save(superHero);
		return new ResponseEntity<>(
		          "New Super Hero created", 
		          HttpStatus.OK);
	}
	
	public ResponseEntity<String> deleteMission(final String heroId, final String missionId){
		final Optional<SuperHero> optionalSuperHero = superHeroRepository.findById(heroId);
    	if (!optionalSuperHero.isPresent()) {
			return new ResponseEntity<>(
			          "Super Hero does not exist", 
			          HttpStatus.BAD_REQUEST);
		}
    	
    	final Optional<Mission> optionalMission = missionRepository.findById(missionId);
    	if (!optionalMission.isPresent()) {
			return new ResponseEntity<>(
			          "Mission does not exist", 
			          HttpStatus.BAD_REQUEST);
		}
    	
    	final SuperHero superHero = optionalSuperHero.get();
    	final Mission mission = optionalMission.get();
    	
    	if(superHero.getMissions().contains(mission))
    	{
    		List<Mission> missions = superHero.getMissions().stream().filter(heroMission -> 
    			heroMission.getId().equals(mission.getId())).collect(Collectors.toList());
    		final Mission superMission = missions.get(0);
			if(!superMission.isCompleted()) {
    			superHero.removeMission(superMission);
    			superHeroRepository.save(superHero);
    			return new ResponseEntity<>(
  			          "Mission deleted from super hero", 
  			          HttpStatus.OK);
    		}
			else
			{
				return new ResponseEntity<>(
				          "Mission is complete. Cannot be removed", 
				          HttpStatus.BAD_REQUEST);
			}
    	}
    	else
    	{
    		return new ResponseEntity<>(
			          "Super Hero does not have this mission", 
			          HttpStatus.BAD_REQUEST);
    	}
    	
	}
	
	public ResponseEntity<List<SuperHeroView>> findAll(){  
        List<SuperHeroView> superHeroRecords = new ArrayList<>();  
        superHeroRepository.findAll().forEach(superHero -> {
        	final SuperHeroView superHeroView = createSuperHeroView(superHero);
        	superHeroRecords.add(superHeroView);
        });
        return new ResponseEntity<>(
        		superHeroRecords, 
		          HttpStatus.OK);  
    }
	
	public ResponseEntity<String> update(final SuperHeroView superHeroView){
		if(superHeroView.getId() == null) {
			return new ResponseEntity<>(
			          "Id is null, cannot be updated", 
			          HttpStatus.BAD_REQUEST);
		}
		
		final Optional<SuperHero> optionalSuperHero = superHeroRepository.findById(superHeroView.getId());
    	if (!optionalSuperHero.isPresent()) {
			return new ResponseEntity<>(
			          "Super Hero does not exist", 
			          HttpStatus.BAD_REQUEST);
		}
    	
    	final SuperHero superHero = optionalSuperHero.get();
    	superHeroFactory.update(superHero, superHeroView);
    	superHeroRepository.save(superHero);
    	return new ResponseEntity<>(
        		"Super hero is updated", 
		          HttpStatus.OK);  
	}
	
    public ResponseEntity<Object> getSuperHeroById(final String id){  
    	final Optional<SuperHero> optionalSuperHero = superHeroRepository.findById(id);
    	if (!optionalSuperHero.isPresent()) {
			return new ResponseEntity<>(
			          "Super Hero does not exist", 
			          HttpStatus.BAD_REQUEST);
		}
    	
    	final SuperHero superHero = optionalSuperHero.get();
    	final SuperHeroView superHeroView = createSuperHeroView(superHero);
    	return new ResponseEntity<>(
    			superHeroView, 
		          HttpStatus.OK);
    }

	private SuperHeroView createSuperHeroView(final SuperHero superHero) {
		final SuperHeroView superHeroView = superHeroViewFactory.create(superHero);
    	final List<MissionView> missionViews = new ArrayList<>();

    	superHero.getMissions().forEach(mission -> 
    	{
    		final MissionView missionView = missionViewFactory.create(mission);
    		missionViews.add(missionView);
    	});
    	superHeroView.setMissions(missionViews);
		return superHeroView;
	} 
   
    public ResponseEntity<String> delete(final String id){  
        superHeroRepository.deleteById(id);  
        return new ResponseEntity<>(
		          "Super Hero deleted", 
		          HttpStatus.OK);
    }


	public ResponseEntity<String> addMissions(final HeroMissionUtility heroMissionUtility) {
		
		final String heroId = heroMissionUtility.getHeroId();
		final Optional<SuperHero> superHeroOptional = superHeroRepository.findById(heroId);
		if (!superHeroOptional.isPresent()) {
			return new ResponseEntity<>(
			          "Super Hero does not exist", 
			          HttpStatus.BAD_REQUEST);
		}
		
		final SuperHero superHero = superHeroOptional.get();
		final List<Mission> missions = new ArrayList<>();
		
		List<String> missionIds = heroMissionUtility.getMissionsIds();
		for (final String missionId : missionIds)
		{
			final Optional<Mission> missionOptional = missionRepository.findById(missionId);
			if(missionOptional.isPresent())
			{
				final Mission mission = missionOptional.get();
				if(!mission.isDeleted()) {
					missions.add(mission);
				}
			}
		}
		superHero.setMissions(missions);
		superHeroRepository.save(superHero);
		return new ResponseEntity<>(
		          "Missions added", 
		          HttpStatus.OK);
	}  
}
