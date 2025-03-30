package com.zrd.rewardservice.reward.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrd.rewardservice.reward.entity.RewardEntity;
import com.zrd.rewardservice.reward.mapper.RewardMapper;
import com.zrd.rewardservice.reward.param.RewardQueryParam;
import com.zrd.rewardservice.reward.service.RewardService;
import com.zrd.rewardservice.reward.vo.RewardQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
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
public class RewardServiceImpl extends ServiceImpl<RewardMapper, RewardEntity> implements RewardService {

    @Resource
    private RewardMapper rewardMapper;


    @Override
    public Long addReward(RewardQueryVo rewardQueryVo) throws Exception {
        RewardEntity rewardEntity = new RewardEntity();
        BeanUtils.copyProperties(rewardQueryVo, rewardEntity);
        super.save(rewardEntity);
        return rewardEntity.getId();
    }


    @Override
    public boolean updateReward(RewardQueryVo rewardQueryVo) throws Exception {
        RewardEntity rewardEntity = new RewardEntity();
        BeanUtils.copyProperties(rewardQueryVo, rewardEntity);
        return super.updateById(rewardEntity);
    }

    @Override
    public boolean deleteReward(String ids) throws Exception {
        String[] idArray = ids.split(",");
        return super.removeByIds(Arrays.asList(idArray));
    }

    @Override
    public boolean deleteReward(RewardQueryParam rewardQueryParam) throws Exception {
        LambdaQueryWrapper<RewardEntity> lambdaQueryWrapper = new LambdaQueryWrapper();

        return super.remove(lambdaQueryWrapper);
    }

    @Override
    public RewardQueryVo getRewardById(Serializable id) throws Exception {
		//RewardQueryVo rewardQueryVo = rewardMapper.getRewardById(id);

        RewardEntity rewardEntity = super.getById(id);
        RewardQueryVo rewardQueryVo = new RewardQueryVo();
        BeanUtils.copyProperties(rewardEntity, rewardQueryVo);
        return rewardQueryVo;
    }


    @Override
    public List<RewardQueryVo> getRewardList(RewardQueryParam rewardQueryParam) throws Exception {
        List<RewardQueryVo> rewardQueryVoList = rewardMapper.getRewardList(rewardQueryParam);
        return rewardQueryVoList;
    }


}