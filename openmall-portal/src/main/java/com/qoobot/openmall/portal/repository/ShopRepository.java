package com.qoobot.openmall.portal.repository;

import com.qoobot.openmall.common.domain.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    Optional<Shop> findByIdAndIsDeleted(Long id, Integer isDeleted);
}
