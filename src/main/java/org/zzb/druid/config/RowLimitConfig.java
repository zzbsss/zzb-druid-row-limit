package org.zzb.druid.config;

public class RowLimitConfig {

    /**
     * 自定义消息
     */
    private String message = "超过最大读取数量，本次查询终止";


    /**
     * 最大限制数量
     */
    private long maxLimit = 10000l;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(long maxLimit) {
        this.maxLimit = maxLimit;
    }

}

