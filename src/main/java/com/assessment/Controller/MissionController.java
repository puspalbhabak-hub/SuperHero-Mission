package com.assessment.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.Entity.Mission;
import com.assessment.Service.MissionService;
import com.assessment.View.MissionView;

@RestController
@RequestMapping("/mission")
public class MissionController {

	@Autowired  
    private MissionService missionService;
	
	@GetMapping
	public ResponseEntity<List<MissionView>> getAllMissions(){  
        return missionService.findAll();  
    } 
	
	@PostMapping
	public ResponseEntity<String> addMission(@RequestBody final Mission mission){  
		return missionService.save(mission);  
    } 
	
	@GetMapping("/{id}")
	 public ResponseEntity<Object> getMission(@PathVariable final String id){  
        return missionService.getMissionById(id);  
    }
	
	@PutMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable final String id) {
		return missionService.delete(id);
    }
	
	@PutMapping
	public ResponseEntity<String> updateMission(@RequestBody final MissionView missionView){
		return missionService.update(missionView);
	}
	
}
