package com.zrd.settlementservice.settlement.controller;

import com.zrd.settlementservice.settlement.param.SettlementQueryParam;
import com.zrd.settlementservice.settlement.service.SettlementService;
import com.zrd.settlementservice.settlement.vo.SettlementQueryVo;
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
@RequestMapping("/settlement")
public class SettlementController{

    @Autowired
    private SettlementService settlementService;


    /**
     * 添加  对象
     */
    @PostMapping("/add")
    public Object addSettlement( @RequestBody SettlementQueryVo settlementQueryVo) throws Exception {
        Object id = settlementService.addSettlement(settlementQueryVo);
        return id;
    }

    /**
     * 修改  对象
     */
    @PutMapping("/update")
    public Boolean updateSettlement( @RequestBody SettlementQueryVo settlementQueryVo) throws Exception {
        boolean flag = settlementService.updateSettlement(settlementQueryVo);
        return flag;
    }

    /**
     * 删除  对象
     */
    @DeleteMapping("/delete")
    public Boolean deleteSettlement( @RequestParam(value = "ids") String ids) throws Exception {
        boolean flag = settlementService.deleteSettlement(ids);
        return flag;
    }

    /**
     * 获取  对象详情
     */
    @GetMapping("/info/{id}")
    public SettlementQueryVo getSettlement(@PathVariable("id") String id) throws Exception {
        SettlementQueryVo settlementQueryVo = settlementService.getSettlementById(id);
        return settlementQueryVo;
    }

    
    /**
     * 获取  列表
     */
    @PostMapping("/list")
    public List<SettlementQueryVo> getSettlementList( @RequestBody SettlementQueryParam settlementQueryParam) throws Exception {
        List<SettlementQueryVo> paging = settlementService.getSettlementList(settlementQueryParam);
        return paging;
    }


}