package com.zrd.deliverymanservice.deliveryman.service;

import com.zrd.deliverymanservice.deliveryman.entity.DeliverymanEntity;
import com.zrd.deliverymanservice.deliveryman.param.DeliverymanQueryParam;
import com.zrd.deliverymanservice.deliveryman.vo.DeliverymanQueryVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 骑手 服务类 接口
 * @Author ZRD
 * @Date 2023/03/19
 **/
public interface DeliverymanService extends IService<DeliverymanEntity> {

    /**
     * 保存 骑手
     *
     * @param deliverymanQueryVo
     * @return
     * @throws Exception
     */
    Object addDeliveryman(DeliverymanQueryVo deliverymanQueryVo) throws Exception;

    /**
     * 修改 骑手
     *
     * @param deliverymanQueryVo
     * @return
     * @throws Exception
     */
    boolean updateDeliveryman(DeliverymanQueryVo deliverymanQueryVo) throws Exception;

    /**
     * 删除 骑手
     *
     * @param ids
     * @return
     * @throws Exception
     */
    boolean deleteDeliveryman(String ids) throws Exception;
	
    /**
     * 根据条件删除 骑手
     *
     * @param deliverymanQueryParam
     * @return
     * @throws Exception
     */
    boolean deleteDeliveryman(DeliverymanQueryParam deliverymanQueryParam) throws Exception;

    /**
     * 根据ID获取 骑手 对象
     *
     * @param id
     * @return
     * @throws Exception
     */
     DeliverymanQueryVo getDeliverymanById(Serializable id) throws Exception;


    /**
     * 获取 骑手 对象列表
     *
     * @param deliverymanQueryParam
     * @return
     * @throws Exception
     */
     List<DeliverymanQueryVo> getDeliverymanList(DeliverymanQueryParam deliverymanQueryParam) throws Exception;

}