package com.csi.sbs.investment.business.exception;

import com.csi.sbs.common.business.exception.GlobalException;

@SuppressWarnings("serial")
public class NotFoundException extends GlobalException
{
    public NotFoundException(String message, int code)
    {
        super(message, code);
    }
}
