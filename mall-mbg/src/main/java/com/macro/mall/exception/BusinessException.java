//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.macro.mall.exception;

public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 2874510430549463213L;
    private int returnCode;

    public BusinessException() {
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(int returnCode) {
        this.returnCode = returnCode;
    }

    public BusinessException(Exception e, int returnCode) {
        super(e);
        this.returnCode = returnCode;
    }

    public BusinessException(String message, int returnCode) {
        super(message);
        this.returnCode = returnCode;
    }

    public BusinessException(String message, Exception e, int returnCode) {
        super(message, e);
        this.returnCode = returnCode;
    }

    public int getReturnCode() {
        return this.returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }
}
