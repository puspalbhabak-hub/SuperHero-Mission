package com.assessment;

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
