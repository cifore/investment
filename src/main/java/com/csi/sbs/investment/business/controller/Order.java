package com.csi.sbs.investment.business.controller;

import com.csi.sbs.investment.business.clientmodel.QueryMutualModel;
import com.csi.sbs.investment.business.service.StockOrderService;
import com.csi.sbs.investment.business.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/investment/order")
public class Order {

    @Resource
    private StockOrderService orderService;

    @RequestMapping(value = "/getOrder", method = RequestMethod.POST)
    @ResponseBody
    public ResultUtil getOrderList() throws Exception {
        try {
            return orderService.getOrderList();
        } catch (Exception e) {
            throw e;
        }
    }
}
