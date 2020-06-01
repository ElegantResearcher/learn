package com.zwz.rpc.loadBalance;

import java.util.List;

/**
 * @description:
 * @date : 2020/5/28 15:53
 * @author: zwz
 */
public interface LoadBalance {

    String selectHost(List<String> repos);
}