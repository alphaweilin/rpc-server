package org.rpc.server.provider;

import org.rpc.server.api.IHelloService;

public class HelloService implements IHelloService {

	@Override
	public String sayHello(String txt) {
		// TODO Auto-generated method stub
		return "Hello "+txt;
	}

}
