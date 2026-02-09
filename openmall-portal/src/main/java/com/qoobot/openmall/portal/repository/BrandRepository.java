package com.qoobot.openmall.portal.repository;

import com.qoobot.openmall.common.domain.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 品牌Repository
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    /**
     * 查询所有显示的品牌
     */
    List<Brand> findByIsDeletedAndIsShowOrderBySortOrder(Integer isDeleted, Integer isShow);

    /**
     * 根据ID查询品牌
     */
    Brand findByIdAndIsDeleted(Long id, Integer isDeleted);
}
