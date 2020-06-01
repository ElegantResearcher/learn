package com.zwz.rpc.zk;

import com.zwz.rpc.loadBalance.LoadBalance;
import com.zwz.rpc.loadBalance.RandomLoadBalance;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;

/**
 * @description:
 * @date : 2020/5/28 15:43
 * @author: zwz
 */
public class ServiceDiscoveryImpl implements IServiceDiscovery {

    private CuratorFramework curatorFramework;

    private List<String> repos;

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
    public String discover(String serviceName) {
        String path = ZkConfig.ZK_REGISTER_PATH + "/" + serviceName;
        try {
            repos = curatorFramework.getChildren().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        registerWatcher(path);

        LoadBalance loadBalance = new RandomLoadBalance();
        return loadBalance.selectHost(repos);
    }

    /**
     * 通过watch机制 动态发现服务节点的变化
     *
     * @param path
     */
    private void registerWatcher(String path) {
        PathChildrenCache childrenCache = new PathChildrenCache(curatorFramework, path, true);
        PathChildrenCacheListener cacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                repos = curatorFramework.getChildren().forPath(path);
            }
        };

        childrenCache.getListenable().addListener(cacheListener);
        try {
            childrenCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}