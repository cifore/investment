package com.csi.sbs.investment.business.exception;

import com.csi.sbs.common.business.exception.GlobalException;

@SuppressWarnings("serial")
public class AcceptException extends GlobalException {
	
	public AcceptException(String message, int code) {
		super(message, code);
	}
}
