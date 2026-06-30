package com.qoobot.openmall.portal.service;

import com.qoobot.openmall.common.domain.entity.LogisticsTracking;
import java.util.List;

public interface LogisticsService {

    /**
     * 查询物流轨迹
     */
    LogisticsTracking queryTracking(String orderNo);

    /**
     * 创建物流记录（发货时调用）
     */
    LogisticsTracking createTracking(String orderNo, String logisticsCompany, String logisticsNo);

    /**
     * 获取模拟物流轨迹节点
     */
    List<String> getMockTrackNodes(String orderNo);

    /**
     * 刷新物流状态（模拟更新）
     */
    void refreshTracking(String orderNo);
}
