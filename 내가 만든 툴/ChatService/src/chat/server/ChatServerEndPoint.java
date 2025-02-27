package chat.server;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import chat.server.web.data.ChatMetaData;
import chat.server.web.data.UserInfo;

@ServerEndpoint(value = "/chat")
public class ChatServerEndPoint {
	@OnOpen
	public void onOpen(Session session) {
		String queryString = session.getRequestURI().getQuery();
        String userName = null;
        String roomNumber = null;

        if (queryString != null) {
            String[] params = queryString.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    if (keyValue[0].equals("roomNumber")) {
                        roomNumber = keyValue[1];
                    } else if (keyValue[0].equals("userName")) {
                    	userName = keyValue[1];
                    }
                }
            }
        }
        
		session.getUserProperties().put("userName", userName);
		session.getUserProperties().put("roomNumber", Integer.parseInt(roomNumber));
		System.out.println("세션이 연결되었습니다. " + session.getId());
	}
	
	@OnMessage
	public void onMessage(String messageJson, Session session) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Map<String, String> map = objectMapper.readValue(messageJson, Map.class);
			String type = map.get("type");
			String message = map.get("message");
			int roomNumber = (Integer) session.getUserProperties().get("roomNumber");
			String userName = (String) session.getUserProperties().get("userName");
			switch (type) {
			case "join":
				System.out.println("join Room : " + roomNumber + "번 방 " + userName);
				UserInfo member = ChatMetaData.memberInfo.get(userName);
				System.out.println("member : " + member.getUserName());
				System.out.println("chatRoomInfo size : " + ChatMetaData.roomInfo.size());
				member.setSession(session);
				ChatMetaData.roomInfo.get(roomNumber).getChatMember().put(userName, member);
				break;
			case "send" :
				String sendMsg = message;
				ConcurrentHashMap<String, UserInfo> chatMember = ChatMetaData.roomInfo.get(roomNumber).getChatMember();
				for (String key : chatMember.keySet()) {
					UserInfo user = chatMember.get(key);
					Session userSession = user.getSession();
					userSession.getBasicRemote().sendText(user.getUserName() + " : " + sendMsg);
				}
				break;
			default:
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@OnClose
	public void onClose(Session session) {
		int roomNumber = (int) session.getUserProperties().get("roomNumber");
		String userName = (String) session.getUserProperties().get("userName");
		ChatMetaData.roomInfo.get(roomNumber).getChatMember().remove("userName");
		System.out.println("세션 종료 : " + userName);
	}
}

