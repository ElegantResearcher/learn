package com.zwz.rpc;

import java.lang.reflect.Proxy;

/**
 * @description:
 * @date : 2020/5/28 11:38
 * @author: zwz
 */
public class RpcClientProxy {

    public static <T> T clientProxy(final Class<T> interfaceCls, final String host, final int port) {
        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(), new Class[]{interfaceCls},
                new RemoteObjectInvocationHandler(host, port));
    }
}