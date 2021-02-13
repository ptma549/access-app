package com.access.myapp.repository;

import com.access.myapp.domain.JobVisit;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the JobVisit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobVisitRepository extends JpaRepository<JobVisit, Long>, JpaSpecificationExecutor<JobVisit> {
}
