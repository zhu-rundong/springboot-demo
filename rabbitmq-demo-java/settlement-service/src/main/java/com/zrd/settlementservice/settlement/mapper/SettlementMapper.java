package com.zrd.settlementservice.settlement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrd.settlementservice.settlement.entity.SettlementEntity;
import com.zrd.settlementservice.settlement.param.SettlementQueryParam;
import com.zrd.settlementservice.settlement.vo.SettlementQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @Description  Mapper 接口
 * @Author ZRD
 * @Date 2025/03/16
 **/
@Mapper
public interface SettlementMapper extends BaseMapper<SettlementEntity> {

    /**
     * 根据ID获取  对象
     *
     * @param id
     * @return
     */
    SettlementQueryVo getSettlementById(Serializable id);

    /**
     * 获取  对象列表
     *
     * @param settlementQueryParam
     * @return
     */
    List<SettlementQueryVo> getSettlementList(@Param("param") SettlementQueryParam settlementQueryParam);

}