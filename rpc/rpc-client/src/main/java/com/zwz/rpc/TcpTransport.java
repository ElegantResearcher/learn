package com.zwz.rpc;

import com.zwz.rpc.protocol.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @description:
 * @date : 2020/5/28 11:51
 * @author: zwz
 */
public class TcpTransport {

    private String host;

    private int port;

    public TcpTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Object send(RpcRequest request) {
        Socket socket = null;
        try {
            System.out.println("创建一个新的连接");
            socket = new Socket(host, port);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(request);
            out.flush();

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Object result = in.readObject();

            in.close();
            out.close();

            return result;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("发送/接收数据异常");
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}