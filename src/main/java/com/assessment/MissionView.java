package com.assessment;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class MissionView {

	@JsonProperty
	private Long id;
	@JsonProperty
	private String missionName;
	@JsonProperty
	private boolean isCompleted;
	@JsonProperty
	private boolean isDeleted;
	@JsonProperty
	private List<SuperHeroView> superHeroes = new ArrayList<>();
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMissionName() {
		return missionName;
	}
	public void setMissionName(String missionName) {
		this.missionName = missionName;
	}
	public boolean isCompleted() {
		return isCompleted;
	}
	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public List<SuperHeroView> getSuperHeroes() {
		return superHeroes;
	}
	public void setSuperHeroes(List<SuperHeroView> superHeroes) {
		this.superHeroes = superHeroes;
	}
	
}
