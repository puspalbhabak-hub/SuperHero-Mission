package com.assessment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/superhero")
public class SuperHeroController {

	@Autowired  
    private SuperHeroService superHeroService;
	
	@GetMapping
	public ResponseEntity<List<SuperHeroView>> getAllSuperHeroes(){  
        return superHeroService.findAll();  
    } 
	
	@PostMapping
	public ResponseEntity<String> addSuperHero(@RequestBody final SuperHero superHero){  
		return superHeroService.save(superHero);  
    } 
	
	@GetMapping("/{id}")
	 public  ResponseEntity<Object> getSuperHeroById(@PathVariable final long id){  
        return superHeroService.getSuperHero(id);  
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable final long id) {
		return superHeroService.delete(id);
    }
	
	@PostMapping("/addMission")
	public ResponseEntity<String> addMissionsToSuperHero(@RequestBody final HeroMissionUtility heroMissionUtility) {
		return superHeroService.addMissions(heroMissionUtility);
	}
	
	@PutMapping("/{heroId}/deleteMission/{missionId}")
	public ResponseEntity<String> deleteMission(@PathVariable final long heroId, @PathVariable final long missionId) {
		return superHeroService.deleteMission(heroId, missionId);
	}
	
	@PutMapping
	public ResponseEntity<String> updateSuperHero(@RequestBody final SuperHeroView superHeroView){
		return superHeroService.update(superHeroView);
	}
	
}
