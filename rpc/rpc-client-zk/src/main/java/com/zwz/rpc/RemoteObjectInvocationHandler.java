package com.zwz.rpc;

import com.zwz.rpc.protocol.RpcRequest;
import com.zwz.rpc.zk.IServiceDiscovery;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @description:
 * @date : 2020/5/28 11:40
 * @author: zwz
 */
public class RemoteObjectInvocationHandler implements InvocationHandler {

    private IServiceDiscovery serviceDiscovery;
    private String version;

    public RemoteObjectInvocationHandler(IServiceDiscovery serviceDiscovery, String version) {
        this.serviceDiscovery = serviceDiscovery;
        this.version = version;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParamObject(args);

        request.setVersion(version);

        String serviceAddress = serviceDiscovery.discover(request.getClassName() + "-" + version);
        String[] address = serviceAddress.split(":");
        TcpTransport tcpTransport = new TcpTransport(address[0], Integer.parseInt(address[1]));
        return tcpTransport.send(request);
    }
}