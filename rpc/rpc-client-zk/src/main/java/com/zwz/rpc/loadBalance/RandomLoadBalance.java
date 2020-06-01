package com.zwz.rpc.loadBalance;

import java.util.List;
import java.util.Random;

/**
 * @description:
 * @date : 2020/5/28 15:58
 * @author: zwz
 */
public class RandomLoadBalance extends AbstractLoadBalance{
    @Override
    protected String doSelect(List<String> repos) {
        int len = repos.size();
        Random random = new Random();
        return repos.get(random.nextInt(len));
    }

}