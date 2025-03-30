package com.zrd.rewardservice.reward.controller;

import com.zrd.rewardservice.reward.param.RewardQueryParam;
import com.zrd.rewardservice.reward.service.RewardService;
import com.zrd.rewardservice.reward.vo.RewardQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Description  前端控制器
 * @Author ZRD
 * @Date 2025/03/16
 **/
@Slf4j
@RestController
@RequestMapping("/reward")
public class RewardController{

    @Autowired
    private RewardService rewardService;


    /**
     * 添加  对象
     */
    @PostMapping("/add")
    public Object addReward( @RequestBody RewardQueryVo rewardQueryVo) throws Exception {
        Object id = rewardService.addReward(rewardQueryVo);
        return id;
    }

    /**
     * 修改  对象
     */
    @PutMapping("/update")
    public Boolean updateReward( @RequestBody RewardQueryVo rewardQueryVo) throws Exception {
        boolean flag = rewardService.updateReward(rewardQueryVo);
        return flag;
    }

    /**
     * 删除  对象
     */
    @DeleteMapping("/delete")
    public Boolean deleteReward( @RequestParam(value = "ids") String ids) throws Exception {
        boolean flag = rewardService.deleteReward(ids);
        return flag;
    }

    /**
     * 获取  对象详情
     */
    @GetMapping("/info/{id}")
    public RewardQueryVo getReward(@PathVariable("id") String id) throws Exception {
        RewardQueryVo rewardQueryVo = rewardService.getRewardById(id);
        return rewardQueryVo;
    }

    
    /**
     * 获取  列表
     */
    @PostMapping("/list")
    public List<RewardQueryVo> getRewardList( @RequestBody RewardQueryParam rewardQueryParam) throws Exception {
        List<RewardQueryVo> paging = rewardService.getRewardList(rewardQueryParam);
        return paging;
    }


}