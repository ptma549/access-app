package com.access.myapp.repository;

import com.access.myapp.domain.Priority;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Priority entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long>, JpaSpecificationExecutor<Priority> {
}
