package com.assessment;

import java.util.ArrayList;
import java.util.List;

public class HeroMissionUtility {

	private String heroId;
	
	private List<String> missionsIds = new ArrayList<>();

	public String getHeroId() {
		return heroId;
	}

	public void setHeroId(String heroId) {
		this.heroId = heroId;
	}

	public List<String> getMissionsIds() {
		return missionsIds;
	}

	public void setMissionsIds(List<String> missionsIds) {
		this.missionsIds = missionsIds;
	}

	
	
}
