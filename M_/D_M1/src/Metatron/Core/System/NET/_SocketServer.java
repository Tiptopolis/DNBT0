package Metatron.Core.System.NET;

import static Metatron.Core._M.M_Utils.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.websocket.server.ServerEndpoint;

import com.sun.net.httpserver.HttpExchange;

import Metatron.Core.Primitive.A_I.iCycle;
import Metatron.Core.Primitive.Struct.aList;
import Metatron.Core.System.Event.iHandler;
import Metatron.Core._M.M_Utils;

@ServerEndpoint(value = "/")
public class _SocketServer extends ServerSocket implements iHandler<HttpExchange>, iCycle {

	ServerSocket Socket;// technically works lol
	protected Thread ServerThread;
	protected boolean running = false;

	public _SocketServer() throws IOException {
		this(8081);
	}

	public _SocketServer(int port) throws IOException {
		this.Socket = new ServerSocket(port);
		System.out.println("Listening for connection on port " + port + " ....");
		/*
		 * while (true) { try (Socket socket = this.Socket.accept()) { Date today = new
		 * Date(); String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
		 * socket.getOutputStream().write(httpResponse.getBytes("UTF-8")); } }
		 */
		this.running = true;

		ServerThread = new Thread("ServerThread") {
			@Override
			public void run() {

				if (_SocketServer.this.isActive()) {
					try {
						_SocketServer.this.next();
					} catch (Throwable t) {
						throw new RuntimeException(t);
					}
				}
			}
		};
		ServerThread.start();
		Log("Console Thread Start @" + this.ServerThread + "  ::  " + this.ServerThread.isAlive());
		System.out.println("Terminated connection on port 8080 ....");

	}

	@Override
	public void next() {
		while (_SocketServer.this.isActive()) {
			synchronized (this) {
				if (this.running)
					try (Socket socket = this.Socket.accept()) {
						DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

						Date today = new Date();
						String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
						aList L = new aList(socket.getInputStream().read());
						Log(L);
						Log((char) in.read());

						socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
						Log("> " + httpResponse);
					} catch (IOException e) {
						// e.printStackTrace(); //
					}
			}
		}
	}

	@Override
	public boolean handle(HttpExchange o) {

		Log(o);
		Log("<>  " + o.getRequestMethod());
		Log("___________________________________>");
		return false;
	}

	public void close() throws IOException {
		this.running = false;
		if (!this.Socket.isClosed())
			this.Socket.close();

	}

	public boolean isActive() {
		return this.running;
		// return !this.Socket.isClosed() && this.Socket.isBound() && this.running;
	}

	@Override
	public String toString() {
		String s = M_Utils.toIdString(this) + " : " + this.Socket;
		return s;
	}
}
