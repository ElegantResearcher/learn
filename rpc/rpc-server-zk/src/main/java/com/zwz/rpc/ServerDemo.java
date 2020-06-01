package com.zwz.rpc;

import com.zwz.rpc.impl.ApplicationImpl;
import com.zwz.rpc.zk.IRegisterCenter;
import com.zwz.rpc.zk.RegisterCenterImpl;

import java.io.IOException;

/**
 * @description:
 * @date : 2020/5/28 11:03
 * @author: zwz
 */
public class ServerDemo {
    public static void main(String[] args) throws IOException {
        IApplication application = new ApplicationImpl();
        IRegisterCenter registerCenter = new RegisterCenterImpl();

        RpcServer server = new RpcServer("127.0.0.1:8005", registerCenter);
        server.bind(application);
        server.publish();
        System.in.read();
    }
}