package com.xiaojinzi.ehibase.util;

import java.io.IOException;

/**
 * Created by xiaojinzi on 19/09/2017.
 * <p>
 * <p>
 * <p>
 * 就是针对后台返回的最外层的格式如果是错误的情况下,把错误的信息封装成为这个异常抛出去
 */
public class NetWorkErrorException extends IOException {

    private int errorCode;

    public NetWorkErrorException() {
    }

    public NetWorkErrorException(String detailMessage, int errerCode) {
        super(detailMessage);
        this.errorCode = errerCode;
    }

    public NetWorkErrorException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public NetWorkErrorException(Throwable throwable) {
        super(throwable);
    }

    public int getErrorCode() {
        return errorCode;
    }
}
