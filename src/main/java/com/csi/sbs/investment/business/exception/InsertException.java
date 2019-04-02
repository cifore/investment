package com.csi.sbs.investment.business.exception;

import com.csi.sbs.common.business.exception.GlobalException;

@SuppressWarnings("serial")
public class InsertException extends GlobalException{

	public InsertException(String message, int code)
    {
        super(message, code);
    }

}
