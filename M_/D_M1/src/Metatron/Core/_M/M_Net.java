package Metatron.Core._M;

import static Metatron.Core._M.M_Utils.*;


import Metatron.Core.Primitive.aNode;
import Metatron.Core.Primitive.Struct.aDictionary;
import Metatron.Core.System.NET._SocketServer;

import java.io.IOException;
import java.net.ServerSocket;

import com.sun.net.httpserver.HttpExchange;

public class M_Net {

	public static final char del = '/';
	//ServerSocket Socket;
	public static _SocketServer Root;

	// /EndPoint|
	public aDictionary<HttpExchange> URL;

	
	public M_Net()
	{
		Log("SOCKETING SERVER");
		try {
			Root = new _SocketServer(8080);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
