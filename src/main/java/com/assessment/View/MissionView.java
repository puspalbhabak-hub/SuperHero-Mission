package com.assessment.View;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class MissionView {

	@JsonProperty
	private String id;
	@JsonProperty
	private String missionName;
	@JsonProperty
	private Boolean completed;
	@JsonProperty
	private Boolean deleted;
	@JsonProperty
	private List<SuperHeroView> superHeroes = new ArrayList<>();
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMissionName() {
		return missionName;
	}
	public void setMissionName(String missionName) {
		this.missionName = missionName;
	}
	
	public Boolean getCompleted() {
		return completed;
	}
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public List<SuperHeroView> getSuperHeroes() {
		return superHeroes;
	}
	public void setSuperHeroes(List<SuperHeroView> superHeroes) {
		this.superHeroes = superHeroes;
	}
	
}
