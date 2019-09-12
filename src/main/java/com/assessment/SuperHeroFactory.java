package com.assessment;

import java.util.List;
import java.util.stream.Collectors;

public class SuperHeroFactory {
	
	private MissionFactory missionFactory;
	
	SuperHeroFactory()
	{
		missionFactory = new MissionFactory();
	}

	public SuperHero update(final SuperHero superHero, final SuperHeroView superHeroView) {
		
		superHero.setFirstName(superHeroView.getFirstName() != null ? superHeroView.getFirstName() : superHero.getFirstName());
		superHero.setLastName(superHeroView.getLastName() != null ? superHeroView.getLastName() : superHero.getLastName());
		superHero.setSuperHeroName(superHeroView.getSuperHeroName() != null ? superHeroView.getSuperHeroName() : superHero.getSuperHeroName());
		if(superHeroView.getMissions() != null && !superHeroView.getMissions().isEmpty()) {
			superHeroView.getMissions().forEach(missionView -> {
				final Mission mission = missionFactory.create(missionView);
				final List<Mission> missionsToDelete = superHero.getMissions().stream().filter(heroMission -> 
    											heroMission.getId().equals(mission.getId())).collect(Collectors.toList());
				superHero.removeMission(missionsToDelete.get(0));
				superHero.addMission(mission);
			});
		}
		return superHero; // return is for the sake of unit test
	}
}
