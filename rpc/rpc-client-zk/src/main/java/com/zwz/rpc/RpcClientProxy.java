package com.zwz.rpc;

import com.zwz.rpc.zk.IServiceDiscovery;

import java.lang.reflect.Proxy;

/**
 * @description:
 * @date : 2020/5/28 11:38
 * @author: zwz
 */
public class RpcClientProxy {

    private IServiceDiscovery serviceDiscovery;

    public RpcClientProxy(IServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    public <T> T clientProxy(final Class<T> interfaceCls, String version) {
        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(), new Class[]{interfaceCls},
                new RemoteObjectInvocationHandler(serviceDiscovery, version));
    }
}