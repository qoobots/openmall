package com.qoobot.openmall.portal.repository;

import com.qoobot.openmall.common.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品分类Repository
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * 根据父分类ID查询子分类
     */
    List<Category> findByParentIdAndIsDeletedOrderBySortOrder(Long parentId, Integer isDeleted);

    /**
     * 查询所有一级分类
     */
    List<Category> findByLevelAndIsDeletedAndIsShowOrderBySortOrder(Integer level, Integer isDeleted, Integer isShow);

    /**
     * 根据分类级别查询
     */
    List<Category> findByLevelAndIsDeletedOrderBySortOrder(Integer level, Integer isDeleted);
}
