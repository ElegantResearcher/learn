package com.zwz.rpc.impl;

import com.zwz.rpc.IApplication;
import com.zwz.rpc.RpcAnnotation;
import com.zwz.rpc.Version;

/**
 * @description:
 * @date : 2020/5/28 10:46
 * @author: zwz
 */
@RpcAnnotation(IApplication.class)
@Version("2.0")
public class ApplicationImpl implements IApplication {

    public String sayHello(String msg) {
        return "hello " + msg;
    }
}