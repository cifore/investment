package com.csi.sbs.investment.business.exception;

import com.csi.sbs.common.business.exception.GlobalException;

@SuppressWarnings("serial")
public class CallOtherException extends GlobalException{

	public CallOtherException(String message, int code)
    {
        super(message, code);
    }

}
