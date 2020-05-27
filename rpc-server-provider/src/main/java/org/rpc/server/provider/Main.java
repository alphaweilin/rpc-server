package org.rpc.server.provider;

import org.rpc.server.api.IHelloService;

public class Main {

	public static void main(String[] args) {
		IHelloService iHelloService = new HelloService();
		RpcProxyServer proxyServer = new RpcProxyServer();
		proxyServer.publisher(iHelloService, 8080);
	}

}
