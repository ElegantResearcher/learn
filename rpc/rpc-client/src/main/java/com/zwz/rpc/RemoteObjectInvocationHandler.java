package com.zwz.rpc;

import com.zwz.rpc.protocol.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @description:
 * @date : 2020/5/28 11:40
 * @author: zwz
 */
public class RemoteObjectInvocationHandler implements InvocationHandler {

    private String host;

    private int port;

    public RemoteObjectInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParamObject(args);

        TcpTransport tcpTransport = new TcpTransport(host, port);
        return tcpTransport.send(request);
    }
}