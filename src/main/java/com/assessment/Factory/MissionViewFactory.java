package com.assessment.Factory;

import com.assessment.Entity.Mission;
import com.assessment.View.MissionView;

public class MissionViewFactory {

	public MissionView create(final Mission mission) {
		final MissionView missionView = new MissionView();
		missionView.setId(mission.getId());
		missionView.setMissionName(mission.getMissionName() != null ? mission.getMissionName() : missionView.getMissionName());
		missionView.setCompleted((Boolean) mission.getCompleted() != null ? mission.getCompleted() : missionView.getCompleted());
		missionView.setDeleted((Boolean) mission.getDeleted() != null ? mission.getDeleted() : missionView.getDeleted());
		return missionView;
	}
}
