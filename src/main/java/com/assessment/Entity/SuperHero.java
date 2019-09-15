package com.assessment.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "super_hero")
public class SuperHero implements Serializable {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "super_hero_name")
	private String superHeroName;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(
			name = "super_hero_mission",
			joinColumns = {@JoinColumn(name = "super_hero_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "mission_id", referencedColumnName = "id")}
	)
	private List<Mission> missions = new ArrayList<>();

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuperHero hero = (SuperHero) o;
        return getId() == hero.getId() &&
                firstName.equals(hero.getFirstName()) && lastName.equals(hero.getLastName()) && superHeroName.equals(hero.getSuperHeroName());
    }
	
	@Override
	public int hashCode()
	{
		return getId() == null ? baseHashCode() + Objects.hash(firstName, lastName, superHeroName) : baseHashCode();
	}
	
	private int baseHashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
    }
	
	public void removeMission(final Mission mission)
	{
		missions.remove(mission);
	}
	
	public void addMission(final Mission mission)
	{
		missions.add(mission);
	}
		
	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getSuperHeroName() {
		return superHeroName;
	}

	public void setSuperHeroName(final String superHeroName) {
		this.superHeroName = superHeroName;
	}

	public List<Mission> getMissions() {
		return missions;
	}

	public void setMissions(final List<Mission> missions) {
		this.missions = missions;
	}

}
