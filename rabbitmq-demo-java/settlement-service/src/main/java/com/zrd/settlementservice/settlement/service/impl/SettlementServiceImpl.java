package com.zrd.settlementservice.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrd.settlementservice.settlement.entity.SettlementEntity;
import com.zrd.settlementservice.settlement.mapper.SettlementMapper;
import com.zrd.settlementservice.settlement.param.SettlementQueryParam;
import com.zrd.settlementservice.settlement.service.SettlementService;
import com.zrd.settlementservice.settlement.vo.SettlementQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @Description  服务实现类
 * @Author ZRD
 * @Date 2025/03/16
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SettlementServiceImpl extends ServiceImpl<SettlementMapper, SettlementEntity> implements SettlementService {

    @Resource
    private SettlementMapper settlementMapper;


    @Override
    public Long addSettlement(SettlementQueryVo settlementQueryVo) throws Exception {
        SettlementEntity settlementEntity = new SettlementEntity();
        BeanUtils.copyProperties(settlementQueryVo, settlementEntity);
        super.save(settlementEntity);
        return settlementEntity.getId();
    }


    @Override
    public boolean updateSettlement(SettlementQueryVo settlementQueryVo) throws Exception {
        SettlementEntity settlementEntity = new SettlementEntity();
        BeanUtils.copyProperties(settlementQueryVo, settlementEntity);
        return super.updateById(settlementEntity);
    }

    @Override
    public boolean deleteSettlement(String ids) throws Exception {
        String[] idArray = ids.split(",");
        return super.removeByIds(Arrays.asList(idArray));
    }

    @Override
    public boolean deleteSettlement(SettlementQueryParam settlementQueryParam) throws Exception {
        LambdaQueryWrapper<SettlementEntity> lambdaQueryWrapper = new LambdaQueryWrapper();

        return super.remove(lambdaQueryWrapper);
    }

    @Override
    public SettlementQueryVo getSettlementById(Serializable id) throws Exception {
		//SettlementQueryVo settlementQueryVo = settlementMapper.getSettlementById(id);

        SettlementEntity settlementEntity = super.getById(id);
        SettlementQueryVo settlementQueryVo = new SettlementQueryVo();
        BeanUtils.copyProperties(settlementEntity, settlementQueryVo);
        return settlementQueryVo;
    }


    @Override
    public List<SettlementQueryVo> getSettlementList(SettlementQueryParam settlementQueryParam) throws Exception {
        List<SettlementQueryVo> settlementQueryVoList = settlementMapper.getSettlementList(settlementQueryParam);
        return settlementQueryVoList;
    }

    @Override
    public Long settlement(Long accountId, BigDecimal price) {
        return System.currentTimeMillis();
    }


}