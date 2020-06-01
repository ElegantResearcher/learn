package com.zwz.rpc;

import com.zwz.rpc.impl.ApplicationImpl;

/**
 * @description:
 * @date : 2020/5/28 11:03
 * @author: zwz
 */
public class ServerDemo {
    public static void main(String[] args) {
        IApplication application = new ApplicationImpl();
        RpcServer rpcServer = new RpcServer();
        rpcServer.publish(application, 8089);
    }
}