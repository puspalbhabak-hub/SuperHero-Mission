package com.assessment.Repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.assessment.Entity.Mission;

@Repository
@Transactional
public interface MissionRepository extends CrudRepository<Mission, String>
{

}
