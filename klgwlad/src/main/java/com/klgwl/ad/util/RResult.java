package com.klgwl.ad.util;

public class RResult<T> {
    public int code;
    public Throwable e;
    public T obj;
    public String error;

    public RResult() {
        this.code = 0;
        e = null;
        obj = null;
    }

    public boolean isSuccess() {
        return code == RHttpClient.RES_CODE_SUCCESS;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append("\n\t");
        builder.append("code:");
        builder.append(code);
        builder.append("\n\t");
        builder.append("e:");
        builder.append(e);
        builder.append("\n\t");
        builder.append("data:");
        builder.append(obj);
        builder.append("\n\t");
        builder.append("error:");
        builder.append(error);
        builder.append("\n}");
        return builder.toString();
    }
}