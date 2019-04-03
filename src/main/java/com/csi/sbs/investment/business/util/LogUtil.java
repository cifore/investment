package com.csi.sbs.investment.business.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.csi.sbs.common.business.json.JsonProcess;
import com.csi.sbs.common.business.util.UUIDUtil;
import com.csi.sbs.investment.business.constant.SysConstant;
import com.csi.sbs.investment.business.entity.SysTransactionLogEntity;
import com.csi.sbs.investment.business.exception.InsertException;

public class LogUtil {
	
	public static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static boolean saveLog(
			RestTemplate restTemplate,
			String operationType,
			String sourceservices,
			String operationStatus,
			String operationDetail
			) throws ParseException,InsertException{
		
			SysTransactionLogEntity log = new SysTransactionLogEntity();
			log.setId(UUIDUtil.generateUUID());
			log.setOperationtype(operationType);
			log.setSourceservices(sourceservices);
			log.setOperationstate(operationStatus);
			log.setOperationdate(sf.parse(sf.format(new Date())));
			log.setOperationdetail(operationDetail);

			@SuppressWarnings("unused")
			ResponseEntity<String> result2 = restTemplate.postForEntity(SysConstant.WRITE_LOG_SERVICEPATH,
					PostUtil.getRequestEntity(JsonProcess.changeEntityTOJSON(log)), String.class);
		
		    return true;
	}

}
