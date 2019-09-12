package com.assessment;


public class MissionFactory {

	public Mission create(final MissionView missionView) {
		final Mission mission = new Mission();
		mission.setId(missionView.getId());
		mission.setMissionName(missionView.getMissionName());
		mission.setDeleted(missionView.isDeleted());
		mission.setCompleted(missionView.isCompleted());
		return mission;
	}
	
	public Mission setDelete(final Mission mission)
	{
		mission.setDeleted(true);
		return mission; // return is for the sake of unit test
	}
	
	public Mission update(final Mission mission, final MissionView missionView) {
		
		mission.setMissionName(missionView.getMissionName() != null ? missionView.getMissionName() : mission.getMissionName());
		return mission; // return is for the sake of unit test
	}
}
