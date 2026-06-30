package com.qoobot.openmall.platform.service.impl;

import com.qoobot.openmall.common.core.result.PageResult;
import com.qoobot.openmall.common.domain.entity.SysConfig;
import com.qoobot.openmall.platform.repository.SysConfigRepository;
import com.qoobot.openmall.platform.service.SysConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 系统配置服务实现
 */
@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl implements SysConfigService {

    private final SysConfigRepository sysConfigRepository;

    @Override
    public PageResult<SysConfig> listPage(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<SysConfig> page = sysConfigRepository.findAll(pageable);
        return new PageResult<>(page.getContent(), page.getTotalElements(), pageNum, pageSize);
    }

    @Override
    public SysConfig getById(Long id) {
        return sysConfigRepository.findById(id).orElseThrow(() -> new RuntimeException("配置不存在"));
    }

    @Override
    public SysConfig getByKey(String configKey) {
        return sysConfigRepository.findByConfigKeyAndIsDeleted(configKey, 0).orElse(null);
    }

    @Override
    @Transactional
    public SysConfig save(SysConfig config) {
        config.setCreateTime(LocalDateTime.now());
        config.setUpdateTime(LocalDateTime.now());
        return sysConfigRepository.save(config);
    }

    @Override
    @Transactional
    public SysConfig update(SysConfig config) {
        SysConfig existing = getById(config.getId());
        existing.setConfigValue(config.getConfigValue());
        existing.setConfigName(config.getConfigName());
        existing.setConfigDesc(config.getConfigDesc());
        existing.setConfigType(config.getConfigType());
        existing.setUpdateTime(LocalDateTime.now());
        return sysConfigRepository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        SysConfig config = getById(id);
        config.setIsDeleted(1);
        config.setUpdateTime(LocalDateTime.now());
        sysConfigRepository.save(config);
    }
}
