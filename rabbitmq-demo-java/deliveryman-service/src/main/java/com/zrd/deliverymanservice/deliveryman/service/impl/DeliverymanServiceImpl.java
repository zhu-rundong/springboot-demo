package com.zrd.deliverymanservice.deliveryman.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zrd.deliverymanservice.deliveryman.entity.DeliverymanEntity;
import com.zrd.deliverymanservice.deliveryman.mapper.DeliverymanMapper;
import com.zrd.deliverymanservice.deliveryman.service.DeliverymanService;
import com.zrd.deliverymanservice.deliveryman.param.DeliverymanQueryParam;
import com.zrd.deliverymanservice.deliveryman.vo.DeliverymanQueryVo;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @Description 骑手 服务实现类
 * @Author ZRD
 * @Date 2023/03/19
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class DeliverymanServiceImpl extends ServiceImpl<DeliverymanMapper, DeliverymanEntity> implements DeliverymanService {

    @Resource
    private DeliverymanMapper deliverymanMapper;


    @Override
    public Object addDeliveryman(DeliverymanQueryVo deliverymanQueryVo) throws Exception {
        DeliverymanEntity deliverymanEntity = new DeliverymanEntity();
        BeanUtils.copyProperties(deliverymanQueryVo, deliverymanEntity);
        super.save(deliverymanEntity);
        return deliverymanEntity.getId();
    }


    @Override
    public boolean updateDeliveryman(DeliverymanQueryVo deliverymanQueryVo) throws Exception {
        DeliverymanEntity deliverymanEntity = new DeliverymanEntity();
        BeanUtils.copyProperties(deliverymanQueryVo, deliverymanEntity);
        return super.updateById(deliverymanEntity);
    }

    @Override
    public boolean deleteDeliveryman(String ids) throws Exception {
        String[] idArray = ids.split(",");
        return super.removeByIds(Arrays.asList(idArray));
    }

    @Override
    public boolean deleteDeliveryman(DeliverymanQueryParam deliverymanQueryParam) throws Exception {
        LambdaQueryWrapper<DeliverymanEntity> lambdaQueryWrapper = new LambdaQueryWrapper();

        return super.remove(lambdaQueryWrapper);
    }

    @Override
    public DeliverymanQueryVo getDeliverymanById(Serializable id) throws Exception {
		//DeliverymanQueryVo deliverymanQueryVo = deliverymanMapper.getDeliverymanById(id);

        DeliverymanEntity deliverymanEntity = super.getById(id);
        DeliverymanQueryVo deliverymanQueryVo = new DeliverymanQueryVo();
        BeanUtils.copyProperties(deliverymanEntity, deliverymanQueryVo);
        return deliverymanQueryVo;
    }


    @Override
    public List<DeliverymanQueryVo> getDeliverymanList(DeliverymanQueryParam deliverymanQueryParam) throws Exception {
        List<DeliverymanQueryVo> deliverymanQueryVoList = deliverymanMapper.getDeliverymanList(deliverymanQueryParam);
        return deliverymanQueryVoList;
    }


}