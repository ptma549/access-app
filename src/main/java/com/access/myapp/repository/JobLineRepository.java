package com.access.myapp.repository;

import com.access.myapp.domain.JobLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the JobLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobLineRepository extends JpaRepository<JobLine, Long>, JpaSpecificationExecutor<JobLine> {
}
