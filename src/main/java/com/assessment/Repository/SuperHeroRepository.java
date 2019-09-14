package com.assessment.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assessment.Entity.SuperHero;

@Repository
@Transactional
public interface SuperHeroRepository extends CrudRepository<SuperHero, String>
{
	@Modifying
	@Query("DELETE FROM SuperHero sp WHERE sp.id=(:id)")
	public void deleteByHeroId(@Param("id") String id);
}
