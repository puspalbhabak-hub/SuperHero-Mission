package com.assessment.Factory;

import java.util.List;
import java.util.stream.Collectors;

import com.assessment.Entity.Mission;
import com.assessment.Entity.SuperHero;
import com.assessment.View.MissionView;
import com.assessment.View.SuperHeroView;

public class SuperHeroFactory {
	
	private MissionFactory missionFactory;
	
	public SuperHeroFactory()
	{
		missionFactory = new MissionFactory();
	}

	public SuperHero update(final SuperHero superHero, final SuperHeroView superHeroView) {
		
		superHero.setId(superHeroView.getId() != null ? superHeroView.getId() : superHero.getId());
		superHero.setFirstName(superHeroView.getFirstName() != null ? superHeroView.getFirstName() : superHero.getFirstName());
		superHero.setLastName(superHeroView.getLastName() != null ? superHeroView.getLastName() : superHero.getLastName());
		superHero.setSuperHeroName(superHeroView.getSuperHeroName() != null ? superHeroView.getSuperHeroName() : superHero.getSuperHeroName());
		final List<MissionView> missionViews = superHeroView.getMissions();
		if(missionViews != null && !missionViews.isEmpty()) {
			for(MissionView missionView : missionViews) {
				final Mission mission = missionFactory.create(missionView);
				final List<Mission> missionsToDelete = superHero.getMissions().stream().filter(heroMission -> 
    											heroMission.getId().equals(mission.getId())).collect(Collectors.toList());
				superHero.removeMission(missionsToDelete.get(0));
				superHero.addMission(mission);
			}
		}
		return superHero; // return is for the sake of unit test
	}
}
