package com.access.myapp.repository;

import com.access.myapp.domain.JobComment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the JobComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobCommentRepository extends JpaRepository<JobComment, Long>, JpaSpecificationExecutor<JobComment> {
}
