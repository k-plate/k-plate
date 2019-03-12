package com.doug.kplate.service.recharge;

import com.doug.kplate.dto.recharge.RechargeDto;
import com.doug.kplate.entity.recharge.Recharge;

import java.util.List;
import java.util.Map;

public interface RechargeService {

    List<RechargeDto> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    Recharge getRechargeById(Long rechargeId);
}
