package com.access.myapp.repository;

import com.access.myapp.domain.Number;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Number entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NumberRepository extends JpaRepository<Number, Long>, JpaSpecificationExecutor<Number> {
}
