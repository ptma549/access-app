package com.access.myapp.repository;

import com.access.myapp.domain.County;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the County entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountyRepository extends JpaRepository<County, Long>, JpaSpecificationExecutor<County> {
}
