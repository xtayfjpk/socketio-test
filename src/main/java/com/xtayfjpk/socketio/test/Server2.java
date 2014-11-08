package com.xtayfjpk.socketio.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import sun.misc.BASE64Encoder;

import com.yongboy.socketio.server.IOHandlerAbs;
import com.yongboy.socketio.server.SocketIOServer;
import com.yongboy.socketio.server.transport.IOClient;

public class Server2 {
	private static List<IOClient> clients = new ArrayList<IOClient>();

	public static void main(String[] args) throws Exception {
		SocketIOServer ioServer = new SocketIOServer(new IOHandlerAbs() {
			
			@Override
			public void OnShutdown() {
				System.out.println("shut down");				
			}
			
			@Override
			public void OnMessage(IOClient client, String eventName) {
				System.out.println("receive message,eventName=" + eventName);
			}
			
			@Override
			public void OnDisconnect(IOClient client) {
				System.out.println("disconnect");				
				System.out.println("disconnect");				
			}
			
			@Override
			public void OnConnect(IOClient client) {
				System.out.println("connect");
				clients.add(client);
			}
		}, 8088);
		
		ioServer.start();
		System.out.println("server started");
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Random random = new Random();
				String data = "{\"x\":" +random.nextInt(100)+ ",\"y\":" +random.nextInt(100)+ "}";
				BASE64Encoder encoder = new BASE64Encoder();
				data = encoder.encode(data.getBytes());
				for(IOClient client : clients) {
					client.send(formatMessage(data));
				}
			}
		}, 1000, 1000);
		
		Object object = new Object();
		synchronized (object) {
			object.wait();
		}
	}

	private static String formatMessage(String data) {
		return String.format(
                "5:::{\"%s\":\"%s\",\"%s\":[\"%s\"]}",
                "name",
                "push",
                "args",
                data
                );
	}
}
