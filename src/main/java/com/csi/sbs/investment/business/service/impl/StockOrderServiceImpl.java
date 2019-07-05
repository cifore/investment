package com.csi.sbs.investment.business.service.impl;

import com.csi.sbs.investment.business.dao.StockOrderDao;
import com.csi.sbs.investment.business.entity.StockOrderEntity;
import com.csi.sbs.investment.business.service.StockOrderService;
import com.csi.sbs.investment.business.util.ResultUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("StockOrderService")
public class StockOrderServiceImpl implements StockOrderService{

    @Resource
    private StockOrderDao orderDao;


    @Override
    public ResultUtil getOrderList() {
        ResultUtil result = new ResultUtil();
        StockOrderEntity order = new StockOrderEntity();
        order.setStatus("0");
        List<StockOrderEntity> orderDaoMany = orderDao.findMany(order);
        result.setCode("1");
        result.setMsg("Search Success");
        result.setData(orderDaoMany);
        return result;
    }
}
