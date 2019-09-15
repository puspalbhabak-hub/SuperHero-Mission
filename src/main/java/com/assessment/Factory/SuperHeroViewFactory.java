package com.assessment.Factory;

import com.assessment.Entity.SuperHero;
import com.assessment.View.SuperHeroView;

public class SuperHeroViewFactory {

	public SuperHeroView create(final SuperHero superHero) {
		final SuperHeroView superHeroView = new SuperHeroView();
		superHeroView.setId(superHero.getId());
		superHeroView.setFirstName(superHero.getFirstName());
		superHeroView.setLastName(superHero.getLastName());
		superHeroView.setSuperHeroName(superHero.getSuperHeroName());
		return superHeroView;
	}
}
