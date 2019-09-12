package com.assessment;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SuperHeroView {
	
	@JsonProperty
	private Long id;
	@JsonProperty
	private String firstName;
	@JsonProperty
	private String lastName;
	@JsonProperty
	private String superHeroName;
	@JsonProperty
	private List<MissionView> missions = new ArrayList<>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getSuperHeroName() {
		return superHeroName;
	}
	public void setSuperHeroName(String superHeroName) {
		this.superHeroName = superHeroName;
	}
	public List<MissionView> getMissions() {
		return missions;
	}
	public void setMissions(List<MissionView> missions) {
		this.missions = missions;
	}

}
