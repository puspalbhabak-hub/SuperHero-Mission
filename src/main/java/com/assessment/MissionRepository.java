package com.assessment;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface MissionRepository extends CrudRepository<Mission, String>
{

}
