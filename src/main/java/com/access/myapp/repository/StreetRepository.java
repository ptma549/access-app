package com.access.myapp.repository;

import com.access.myapp.domain.Street;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Street entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StreetRepository extends JpaRepository<Street, Long>, JpaSpecificationExecutor<Street> {
}
