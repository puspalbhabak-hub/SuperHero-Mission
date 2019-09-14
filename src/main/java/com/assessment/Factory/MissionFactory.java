package com.assessment.Factory;

import com.assessment.Entity.Mission;
import com.assessment.View.MissionView;

public class MissionFactory {

	public Mission create(final MissionView missionView) {
		final Mission mission = new Mission();
		mission.setId(missionView.getId());
		update(mission, missionView);
		return mission;
	}
	
	public Mission setDelete(final Mission mission)
	{
		mission.setDeleted(true);
		return mission; // return is for the sake of unit test
	}
	
	public Mission update(final Mission mission, final MissionView missionView) {
		
		mission.setMissionName(missionView.getMissionName() != null ? missionView.getMissionName() : mission.getMissionName());
		mission.setCompleted(missionView.getCompleted() != null ? missionView.getCompleted() : mission.getCompleted());
		mission.setDeleted(missionView.getDeleted() != null ? missionView.getDeleted() : mission.getDeleted());
		return mission; // return is for the sake of unit test
	}
}
