package com.access.myapp.repository;

import com.access.myapp.domain.PtmAccount;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PtmAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PtmAccountRepository extends JpaRepository<PtmAccount, Long>, JpaSpecificationExecutor<PtmAccount> {
}
