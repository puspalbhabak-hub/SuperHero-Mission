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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "mission")
public class Mission implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	
	@Column(name = "mission_name")
	private String missionName;
	@Column(name = "completed")
	private boolean completed;
	@Column(name = "deleted")
	private boolean deleted;

	@ManyToMany(
			mappedBy = "missions", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST
	)
	private List<SuperHero> superHeroes = new ArrayList<>();
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mission mission = (Mission) o;
        return getId() == mission.getId() &&
                missionName.equals(mission.getMissionName());
    }
	
	@Override
	public int hashCode()
	{
		return getId() == null ? baseHashCode() + Objects.hash(missionName) : baseHashCode();
	}

	private int baseHashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
    }
	
	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}
	
	public boolean getCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getMissionName() {
		return missionName;
	}

	public void setMissionName(final String missionName) {
		this.missionName = missionName;
	}

	public List<SuperHero> getSuperHeroes() {
		return superHeroes;
	}

	public void setSuperHeroes(final List<SuperHero> superHeroes) {
		this.superHeroes = superHeroes;
	}
	
	

}
