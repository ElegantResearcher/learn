package com.zwz.rpc;

/**
 * @description:
 * @date : 2020/5/28 11:37
 * @author: zwz
 */
public class ClientDemo {
    public static void main(String[] args) {
        //这里返回的是  类型为 RemoteObjectInvocationHandler 的 代理对象
        IApplication application = RpcClientProxy.clientProxy(IApplication.class, "127.0.0.1", 8089);
        // 调用方式实际上是执行 RemoteObjectInvocationHandler 的 invoke 方法
        System.out.println(application.sayHello("mic"));
    }
}