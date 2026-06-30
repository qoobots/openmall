package com.qoobot.openmall.portal.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.qoobot.openmall.common.domain.entity.LogisticsTracking;
import com.qoobot.openmall.portal.repository.LogisticsTrackingRepository;
import com.qoobot.openmall.portal.service.LogisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogisticsServiceImpl implements LogisticsService {

    private final LogisticsTrackingRepository logisticsRepo;

    @Override
    public LogisticsTracking queryTracking(String orderNo) {
        return logisticsRepo.findByOrderNo(orderNo).orElse(null);
    }

    @Override
    @Transactional
    public LogisticsTracking createTracking(String orderNo, String logisticsCompany, String logisticsNo) {
        LogisticsTracking tracking = logisticsRepo.findByOrderNo(orderNo).orElse(new LogisticsTracking());

        List<String> trackNodes = Arrays.asList(
                "【" + logisticsCompany + "】已揽收，快递员已取件",
                "【" + logisticsCompany + "】快件已到达{" + logisticsCompany + "}分拨中心",
                "【" + logisticsCompany + "】快件已从分拨中心发出",
                "【" + logisticsCompany + "】快件正运往目的地城市",
                "【" + logisticsCompany + "】快件已到达目的地分拨中心",
                "【" + logisticsCompany + "】快件正在派送中，快递员电话咨询可见详单",
                "【" + logisticsCompany + "】快件已签收"
        );

        tracking.setOrderNo(orderNo);
        tracking.setLogisticsNo(logisticsNo);
        tracking.setLogisticsCompany(logisticsCompany);
        tracking.setCurrentStatus("运输中");
        tracking.setTrackDetail(JSONUtil.toJsonStr(trackNodes));
        tracking.setEstimatedArrive(LocalDateTime.now().plusDays(3));
        tracking.setLastQueryTime(LocalDateTime.now());
        tracking.setCreateBy(1L);
        tracking.setUpdateBy(1L);

        return logisticsRepo.save(tracking);
    }

    @Override
    public List<String> getMockTrackNodes(String orderNo) {
        Optional<LogisticsTracking> opt = logisticsRepo.findByOrderNo(orderNo);
        if (opt.isPresent() && opt.get().getTrackDetail() != null) {
            try {
                return JSONUtil.toList(opt.get().getTrackDetail(), String.class);
            } catch (Exception e) {
                // fall back to empty
            }
        }
        return List.of();
    }

    @Override
    @Transactional
    public void refreshTracking(String orderNo) {
        Optional<LogisticsTracking> opt = logisticsRepo.findByOrderNo(orderNo);
        opt.ifPresent(tracking -> {
            tracking.setCurrentStatus("配送中");
            tracking.setLastQueryTime(LocalDateTime.now());
            logisticsRepo.save(tracking);
        });
    }
}
