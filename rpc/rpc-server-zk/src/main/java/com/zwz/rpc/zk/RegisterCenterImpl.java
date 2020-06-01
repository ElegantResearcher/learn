package com.zwz.rpc.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @description:
 * @date : 2020/5/28 14:12
 * @author: zwz
 */
public class RegisterCenterImpl implements IRegisterCenter {

    private CuratorFramework curatorFramework;

    {
        curatorFramework = CuratorFrameworkFactory
                .builder()
                .connectString(ZkConfig.CONNECT_ADDRESS)
                .sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .build();

        curatorFramework.start();
    }

    @Override
    public void register(String serviceName, String serviceAddress) throws Exception {
        //注册对应的服务
        String servicePath = ZkConfig.ZK_REGISTER_PATH + "/" + serviceName;
        if (curatorFramework.checkExists().forPath(servicePath) == null) {
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                    .forPath(servicePath, "0".getBytes());
        }

        String addressPath = servicePath + "/" + serviceAddress;
        String rsNode = curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(addressPath, "0".getBytes());
        System.out.println("服务注册成功：" + rsNode);
    }


}