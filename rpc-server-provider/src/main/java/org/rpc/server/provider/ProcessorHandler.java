package org.rpc.server.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

import org.rpc.server.api.RpcRequest;

public class ProcessorHandler implements Runnable {
	
	private Socket socket;
	private Object service;

	public ProcessorHandler(Socket socket, Object service) {
		this.socket = socket;
		this.service = service;
	}



	@Override
	public void run() {
		ObjectInputStream objectInputStream=null;
		ObjectOutputStream objectOutputStream=null;
		// 1利用反序列化获得java对象
		// 2socket.getInputStream()->被序列化的报文
		// 3ObjectInputStream() 实现反序列化
		try {
			objectInputStream =  new ObjectInputStream(socket.getInputStream());
			RpcRequest rpcRequest = (RpcRequest)objectInputStream.readObject();
			Object result=invoke(rpcRequest);
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(result);
			objectOutputStream.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (objectInputStream != null) {
				try {
					objectInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (objectOutputStream != null) {
				try {
					objectOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}

	}
	
	private Object invoke(RpcRequest request) throws Exception {
		Class clazz = Class.forName(request.getClassName());
		Method method = clazz.getMethod(request.getMethodName(), request.getTypes());
		Object result = method.invoke(service, request.getParameters());
		return result;
	}

}
