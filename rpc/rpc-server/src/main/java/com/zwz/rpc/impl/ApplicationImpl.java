package com.zwz.rpc.impl;

import com.zwz.rpc.IApplication;

/**
 * @description:
 * @date : 2020/5/28 10:46
 * @author: zwz
 */
public class ApplicationImpl implements IApplication {

    public String sayHello(String msg) {
        return "hello" + msg;
    }
}