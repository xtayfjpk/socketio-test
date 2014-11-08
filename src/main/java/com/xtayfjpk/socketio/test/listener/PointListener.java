package com.xtayfjpk.socketio.test.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import com.xtayfjpk.socketio.test.bean.Point;

public class PointListener implements DataListener<Point> {

	@Override
	public void onData(SocketIOClient client, Point data, AckRequest ackSender) throws Exception {
		System.out.println(data);		
	}

}
