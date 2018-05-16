package com.xiaojinzi.ehibase.util;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

/**
 * @author : xiaojinzi 30212
 * @desc :
 * @time : 2017/12/06
 *
 */
public class ErrorUtil {

    private static String[] netWorkFlags = {
            "Failed to connect",
            "failed to connect",
            "java.net.ConnectException",
            "HTTP 405 Method Not Allowed"
    };

    private static String[] serverError = {
            "HTTP 500 Internal Server Error"
    };

    /**
     * 判断一个错误是不是网络错误,客户端错误
     *
     * @param t
     * @return
     */
    public static boolean isNetWorkError(Throwable t) {

        if (t instanceof SocketTimeoutException || t instanceof UnknownHostException ||
                t instanceof UnknownServiceException) {
            return true;
        }

        for (String flag : netWorkFlags) {

            if (t.getMessage() != null && t.getMessage().contains(flag)) {
                return true;
            }

        }
        return false;

    }

    /**
     * 判断一个错误是不是服务端错误
     *
     * @param t
     * @return
     */
    public static boolean isServerError(Throwable t) {

        for (String flag : serverError) {

            if (t.getMessage() != null && t.getMessage().contains(flag)) {
                return true;
            }

        }
        return false;

    }

}
