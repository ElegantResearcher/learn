package com.zwz.rpc;

import com.zwz.rpc.zk.IServiceDiscovery;
import com.zwz.rpc.zk.ServiceDiscoveryImpl;

/**
 * @description:
 * @date : 2020/5/28 11:37
 * @author: zwz
 */
public class ClientDemo {
    public static void main(String[] args) {

        IServiceDiscovery serviceDiscovery = new ServiceDiscoveryImpl();
        RpcClientProxy rpcClientProxy = new RpcClientProxy(serviceDiscovery);

        //这里返回的是  类型为 RemoteObjectInvocationHandler 的 代理对象
        IApplication application = rpcClientProxy.clientProxy(IApplication.class,"2.0");
        // 调用方式实际上是执行 RemoteObjectInvocationHandler 的 invoke 方法
        System.out.println(application.sayHello("mic"));
    }
}