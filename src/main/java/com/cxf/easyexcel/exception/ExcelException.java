package com.cxf.easyexcel.exception;

/**
 * excel自定义异常类
 *
 * @author always_on_the_way
 * @date 2019-07-23
 */
public class ExcelException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ExcelException(String message) {
        super(message);
    }
}
