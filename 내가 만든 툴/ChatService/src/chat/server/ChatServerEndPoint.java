package chat.server;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import chat.server.web.data.UserInfo;


public class ChatServerEndPoint {
	
	@ServerEndpoint(value = "/chat")
	public class RealTimeChat {
		@OnOpen
		public void onOpen(Session session) {
			System.out.println("세션이 연결되었습니다. " + session.getId());
		}
		
		@OnMessage
		public void onMessage(String message, Session session) throws IOException {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				UserInfo map = objectMapper.readValue(message, UserInfo.class);
			} catch (Exception e) {
				
			}
		}
		
		@OnClose
		public void onClose(Session session) {
			
		}
	}
	
}

