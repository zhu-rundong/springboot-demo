package com.zrd.settlementservice.settlement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrd.settlementservice.settlement.entity.SettlementEntity;
import com.zrd.settlementservice.settlement.param.SettlementQueryParam;
import com.zrd.settlementservice.settlement.vo.SettlementQueryVo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Description  服务类 接口
 * @Author ZRD
 * @Date 2025/03/16
 **/
public interface SettlementService extends IService<SettlementEntity> {

    /**
     * 保存 
     *
     * @param settlementQueryVo
     * @return
     * @throws Exception
     */
    Long addSettlement(SettlementQueryVo settlementQueryVo) throws Exception;

    /**
     * 修改 
     *
     * @param settlementQueryVo
     * @return
     * @throws Exception
     */
    boolean updateSettlement(SettlementQueryVo settlementQueryVo) throws Exception;

    /**
     * 删除 
     *
     * @param ids
     * @return
     * @throws Exception
     */
    boolean deleteSettlement(String ids) throws Exception;
	
    /**
     * 根据条件删除 
     *
     * @param settlementQueryParam
     * @return
     * @throws Exception
     */
    boolean deleteSettlement(SettlementQueryParam settlementQueryParam) throws Exception;

    /**
     * 根据ID获取  对象
     *
     * @param id
     * @return
     * @throws Exception
     */
     SettlementQueryVo getSettlementById(Serializable id) throws Exception;


    /**
     * 获取  对象列表
     *
     * @param settlementQueryParam
     * @return
     * @throws Exception
     */
     List<SettlementQueryVo> getSettlementList(SettlementQueryParam settlementQueryParam) throws Exception;

     Long settlement(Long accountId,BigDecimal price);

}