package com.hospital.dto;

/**
 * The persistent class for the appointment database table.
 * 
 */
public class ReturnObject {

    public static enum RespStatus {
        ERROR("error"), INFO("info"), SUCCESS("success"), WARNING("warning"), ACCESS_DENIED("access_denied");

        private String value;

        private RespStatus(String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }
    }

    public static final String ERROR = RespStatus.ERROR.toString();

    public static final String INFO = RespStatus.INFO.toString();

    public static final String SUCCESS = RespStatus.SUCCESS.toString();

    public static final String WARNING = RespStatus.WARNING.toString();

    public static final String ACCESS_DENIED = RespStatus.ACCESS_DENIED.toString();

    private String status;

    private String message;

    private Object retObj;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRetObj() {
        return retObj;
    }

    public void setRetObj(Object retObj) {
        this.retObj = retObj;
    }

    public void setMessage(String message, String status) {
        this.status = String.valueOf(status);
        this.message = String.valueOf(message);
    }

    public void setMessageSuccess(String message) {
        this.status = SUCCESS;
        this.message = String.valueOf(message);
    }

    public void setMessageError(String message) {
        this.status = ERROR;
        this.message = String.valueOf(message);
    }

    public void setMessage(RespStatus respStatus, String message) {
        this.status = String.valueOf(respStatus.toString());
        this.message = String.valueOf(message);
    }

}
