package com.access.myapp.repository;

import com.access.myapp.domain.Town;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Town entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TownRepository extends JpaRepository<Town, Long>, JpaSpecificationExecutor<Town> {
}
