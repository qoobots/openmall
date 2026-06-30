package com.qoobot.openmall.platform.service;

import com.qoobot.openmall.common.core.result.PageResult;
import com.qoobot.openmall.common.domain.entity.SysConfig;

/**
 * 系统配置服务
 */
public interface SysConfigService {

    PageResult<SysConfig> listPage(int pageNum, int pageSize);

    SysConfig getById(Long id);

    SysConfig getByKey(String configKey);

    SysConfig save(SysConfig config);

    SysConfig update(SysConfig config);

    void delete(Long id);
}
