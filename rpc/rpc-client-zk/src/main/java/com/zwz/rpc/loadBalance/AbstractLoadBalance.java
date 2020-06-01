package com.zwz.rpc.loadBalance;

import java.util.List;

/**
 * @description:
 * @date : 2020/5/28 15:54
 * @author: zwz
 */
public abstract class AbstractLoadBalance implements LoadBalance{

    @Override
    public String selectHost(List<String> repos) {
        if (repos == null || repos.size() == 0) {
            return null;
        }

        if (repos.size() == 1) {
            return repos.get(0);
        }

        return doSelect(repos);
    }

    protected abstract String doSelect(List<String> repos);
}