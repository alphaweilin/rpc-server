package org.rpc.server.provider;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcProxyServer {
	private ExecutorService executorService = Executors.newFixedThreadPool(10);
	
	public void publisher(Object service, int port) {
		ServerSocket serverSocket = null; 
		try {
			serverSocket = new ServerSocket(port);
			while(true) { // 持续监听客户端连接
				Socket socket = serverSocket.accept();
				// 1如果进入代码块，说明有一个客户端连接进来
				executorService.execute(new ProcessorHandler(socket, service));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
