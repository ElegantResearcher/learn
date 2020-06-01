package com.zwz.rpc.protocol;

import java.io.Serializable;

/**
 * @description:
 * @date : 2020/5/28 10:48
 * @author: zwz
 */
public class RpcRequest implements Serializable {

    private static final long serialVersionUID = 3024428958281801052L;

    private String className;

    private String methodName;

    private Object[] paramObject;

    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParamObject() {
        return paramObject;
    }

    public void setParamObject(Object[] paramObject) {
        this.paramObject = paramObject;
    }
}