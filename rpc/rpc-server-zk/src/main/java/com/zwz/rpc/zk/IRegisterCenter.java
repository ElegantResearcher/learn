package com.zwz.rpc.zk;

public interface IRegisterCenter {

    /**
     * 注册服务名称和服务地址
     * @param serviceName
     * @param serviceAddress
     * @throws Exception
     */
    void register(String serviceName, String serviceAddress) throws Exception;
}
