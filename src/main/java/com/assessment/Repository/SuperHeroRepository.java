package com.assessment.Repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.assessment.Entity.SuperHero;

@Repository
@Transactional
public interface SuperHeroRepository extends CrudRepository<SuperHero, String>
{

}
