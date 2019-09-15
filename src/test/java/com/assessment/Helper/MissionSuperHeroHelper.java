package com.assessment.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.assessment.Entity.Mission;
import com.assessment.Entity.SuperHero;
import com.assessment.View.MissionView;
import com.assessment.View.SuperHeroView;

public class MissionSuperHeroHelper {

	public static Mission createMission(final String id)
	{
		final Mission mission = createMissionWithoutHeroes(id);
		final SuperHero hero = createHero("2");
		mission.setSuperHeroes(Arrays.asList(hero));
		return mission;
	}

	public static Mission createMissionWithoutHeroes(final String id) {
		final Mission mission = new Mission();
		mission.setMissionName("Mission");
		mission.setId(id);
		mission.setCompleted(false);
		mission.setDeleted(false);
		return mission;
	}
	
	public static SuperHero createHero(final String id)
	{
		final SuperHero superHero = new SuperHero();
		superHero.setFirstName("First_Name");
		superHero.setLastName("Last_Name");
		superHero.setId(id);
		superHero.setSuperHeroName("Super_Hero");
		return superHero;
	}
	
	public static SuperHero createHeroWithMissions(final String id)
	{
		final SuperHero superHero = new SuperHero();
		superHero.setFirstName("First_Name");
		superHero.setLastName("Last_Name");
		superHero.setId(id);
		superHero.setSuperHeroName("Super_Hero");
		
		final Mission mission1 = new Mission();
		mission1.setMissionName("Mission_1");
		mission1.setId("1");
		mission1.setCompleted(false);
		mission1.setDeleted(false);
		
		final Mission mission2 = new Mission();
		mission2.setMissionName("Mission_2");
		mission2.setId("2");
		mission2.setCompleted(false);
		mission2.setDeleted(false);
		
		final List<Mission> missions = new ArrayList<>();
		missions.add(mission1);
		missions.add(mission2);
		superHero.setMissions(missions);
		
		return superHero;
	}
	
	public static MissionView convertToViewMission(final Mission mission)
	{
		final MissionView missionView = new MissionView();
		missionView.setId(mission.getId());
		missionView.setCompleted(mission.getCompleted());
		missionView.setDeleted(mission.getDeleted());
		missionView.setMissionName(mission.getMissionName());
		return missionView;
	}
	
	public static MissionView createMissionView(final String id)
	{
		final MissionView missionView = createMissionViewWithoutSuperHero(id);
		final SuperHeroView heroView = createHeroView("2");
		missionView.setSuperHeroes(Arrays.asList(heroView));
		return missionView;
	}

	public static MissionView createMissionViewWithoutSuperHero(final String id) {
		final MissionView missionView = new MissionView();
		missionView.setMissionName("Mission");
		missionView.setId(id);
		missionView.setCompleted(false);
		missionView.setDeleted(false);
		return missionView;
	}
	
	public static SuperHeroView createHeroView(final String id)
	{
		final SuperHeroView superHeroView = new SuperHeroView();
		superHeroView.setFirstName("First_Name");
		superHeroView.setLastName("Last_Name");
		superHeroView.setId(id);
		superHeroView.setSuperHeroName("Super_Hero");
		return superHeroView;
	}
}
