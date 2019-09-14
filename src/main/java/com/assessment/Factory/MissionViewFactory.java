package com.assessment.Factory;

import com.assessment.Entity.Mission;
import com.assessment.View.MissionView;

public class MissionViewFactory {

	public MissionView create(final Mission mission) {
		final MissionView missionView = new MissionView();
		missionView.setId(mission.getId());
		missionView.setMissionName(mission.getMissionName());
		missionView.setCompleted(mission.isCompleted());
		missionView.setDeleted(mission.isDeleted());
		return missionView;
	}
}
