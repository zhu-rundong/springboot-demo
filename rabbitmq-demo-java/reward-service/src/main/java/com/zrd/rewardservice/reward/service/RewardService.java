package com.zrd.rewardservice.reward.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrd.rewardservice.reward.entity.RewardEntity;
import com.zrd.rewardservice.reward.param.RewardQueryParam;
import com.zrd.rewardservice.reward.vo.RewardQueryVo;

import java.io.Serializable;
import java.util.List;

/**
 * @Description  服务类 接口
 * @Author ZRD
 * @Date 2025/03/16
 **/
public interface RewardService extends IService<RewardEntity> {

    /**
     * 保存 
     *
     * @param rewardQueryVo
     * @return
     * @throws Exception
     */
    Long addReward(RewardQueryVo rewardQueryVo) throws Exception;

    /**
     * 修改 
     *
     * @param rewardQueryVo
     * @return
     * @throws Exception
     */
    boolean updateReward(RewardQueryVo rewardQueryVo) throws Exception;

    /**
     * 删除 
     *
     * @param ids
     * @return
     * @throws Exception
     */
    boolean deleteReward(String ids) throws Exception;
	
    /**
     * 根据条件删除 
     *
     * @param rewardQueryParam
     * @return
     * @throws Exception
     */
    boolean deleteReward(RewardQueryParam rewardQueryParam) throws Exception;

    /**
     * 根据ID获取  对象
     *
     * @param id
     * @return
     * @throws Exception
     */
     RewardQueryVo getRewardById(Serializable id) throws Exception;


    /**
     * 获取  对象列表
     *
     * @param rewardQueryParam
     * @return
     * @throws Exception
     */
     List<RewardQueryVo> getRewardList(RewardQueryParam rewardQueryParam) throws Exception;

}