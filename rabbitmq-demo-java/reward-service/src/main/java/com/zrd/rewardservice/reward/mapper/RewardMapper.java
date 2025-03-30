package com.zrd.rewardservice.reward.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrd.rewardservice.reward.entity.RewardEntity;
import com.zrd.rewardservice.reward.param.RewardQueryParam;
import com.zrd.rewardservice.reward.vo.RewardQueryVo;
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
public interface RewardMapper extends BaseMapper<RewardEntity> {

    /**
     * 根据ID获取  对象
     *
     * @param id
     * @return
     */
    RewardQueryVo getRewardById(Serializable id);

    /**
     * 获取  对象列表
     *
     * @param rewardQueryParam
     * @return
     */
    List<RewardQueryVo> getRewardList(@Param("param") RewardQueryParam rewardQueryParam);

}