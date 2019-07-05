package com.csi.sbs.investment.business.service;

import com.csi.sbs.investment.business.clientmodel.QueryMutualModel;
import com.csi.sbs.investment.business.entity.StockOrderEntity;
import com.csi.sbs.investment.business.util.ResultUtil;

public interface StockOrderService {

    public ResultUtil getOrderList();
}
