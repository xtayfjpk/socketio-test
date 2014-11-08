package com.xtayfjpk.socketio.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.xtayfjpk.socketio.test.bean.Point;
import com.xtayfjpk.socketio.test.listener.PointListener;

public class Server {
	private static List<SocketIOClient> clients = new ArrayList<SocketIOClient>();

	public static void main(String[] args) throws Exception {
		Configuration configuration = new Configuration();
		configuration.setHostname("127.0.0.1");
		configuration.setPort(8081);
		SocketIOServer server = new SocketIOServer(configuration);

		server.addConnectListener(new ConnectListener() {
			@Override
			public void onConnect(SocketIOClient client) {
				System.out.println("connected:SessionId=" + client.getSessionId());	
				clients.add(client);
			}
		});
		
		//server.addEventListener("pointevent", Point.class, new PointListener());
		server.start();
		System.out.println("server started");
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Random random = new Random();
				for(SocketIOClient client : clients) {
					client.sendEvent("pushpoint", new Point(random.nextInt(100), random.nextInt(100)));
				}
			}
		}, 1000, 1000);
		
		Object object = new Object();
		synchronized (object) {
			object.wait();
		}
	}

}
