package com.zwz.rpc;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.zwz.rpc.zk.IRegisterCenter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @date : 2020/5/28 11:04
 * @author: zwz
 */
public class RpcServer {

    private String serviceAddress;

    private IRegisterCenter registerCenter;

    private Map<String, Object> serviceMap = new HashMap<>();

    private final ExecutorService pool = Executors.newCachedThreadPool();

    public RpcServer(String serviceAddress, IRegisterCenter registerCenter) {
        this.serviceAddress = serviceAddress;
        this.registerCenter = registerCenter;
    }

    /**
     * 绑定服务名称和服务对象
     *
     * @param services
     */
    public void bind(Object... services) {
        for (Object service : services) {
            RpcAnnotation annotation = service.getClass().getAnnotation(RpcAnnotation.class);
            String serviceName = annotation.value().getName();

            Version versionAn = service.getClass().getAnnotation(Version.class);
            String version = versionAn.value();
            //绑定接口名称及其对应服务
            serviceMap.put(serviceName + "-" + version, service);
        }
    }

    public void publish() {
        ServerSocket serverSocket = null;
        try {
            String[] address = serviceAddress.split(":");
            serverSocket = new ServerSocket(Integer.parseInt(address[1]));

            for (String interfaceName : serviceMap.keySet()) {
                registerCenter.register(interfaceName, serviceAddress);
                System.out.println("注册服务成功：" + interfaceName + "->" + serviceAddress);
            }

            while (true) {
                Socket socket = serverSocket.accept();
                pool.execute(new ProcessorHandler(socket, serviceMap));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}