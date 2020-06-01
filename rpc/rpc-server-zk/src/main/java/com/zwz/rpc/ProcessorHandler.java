package com.zwz.rpc;

import com.zwz.rpc.protocol.RpcRequest;

import javax.xml.ws.spi.Invoker;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * @description:
 * @date : 2020/5/28 11:06
 * @author: zwz
 */
public class ProcessorHandler implements Runnable {
    private Socket socket;

    private Map<String,Object> handleMap;

    public ProcessorHandler(Socket socket, Map<String, Object> handleMap) {
        this.socket = socket;
        this.handleMap = handleMap;
    }

    @Override
    public void run() {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            RpcRequest request = (RpcRequest) in.readObject();
            Object result = invoke(request);

            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(result);
            out.flush();
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private Object invoke(RpcRequest request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object[] paramObject = request.getParamObject();
        Class[] types = new Class[paramObject.length];
        for (int i = 0; i < paramObject.length; i++) {
            types[i] = paramObject[i].getClass();
        }

        String serviceName = request.getClassName();
        String version = request.getVersion();
        if (version != null) {
            serviceName = serviceName + "-" + version;
        }

        Object service = handleMap.get(serviceName);
        Method method = service.getClass().getMethod(request.getMethodName(), types);
        return method.invoke(service, paramObject);
    }
}