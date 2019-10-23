package org.service.cache.data;

public class CacheData {
    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "CacheData{" +
                "result=" + result +
                '}';
    }
}
