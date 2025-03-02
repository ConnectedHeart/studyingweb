package chat.server;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import chat.server.web.data.ChatMetaData;
import chat.server.web.data.UserInfo;

@ServerEndpoint(value = "/chat")
public class ChatServerEndPoint {
	@OnOpen
	public void onOpen(Session session) throws Exception {
		String queryString = session.getRequestURI().getQuery();
        String userName = null;
        Integer roomNumber = null;

        if (queryString != null) {
            String[] params = queryString.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    if (keyValue[0].equals("roomNumber")) {
                        roomNumber = Integer.parseInt(keyValue[1]);
                    } else if (keyValue[0].equals("userName")) {
                    	userName = keyValue[1];
                    }
                }
            }
        }
        
		session.getUserProperties().put("userName", userName.trim());
		session.getUserProperties().put("roomNumber", roomNumber);
		
		UserInfo member = ChatMetaData.memberInfo.get(userName);
		member.setSession(session);
		
		Map <String, UserInfo> chatMember = ChatMetaData.roomInfo.get(roomNumber).getChatMemberMap();
		for (String key : chatMember.keySet()) {
			UserInfo user = chatMember.get(key);
			Session userSession = user.getSession();
			JSONObject responseMessage = new JSONObject();
			responseMessage.put("type", "noti");
			responseMessage.put("content", userName + "님이 입장하셨습니다.");
			userSession.getBasicRemote().sendText(responseMessage.toString());
		}
		
		ChatMetaData.roomInfo.get(roomNumber).getChatMemberMap().put(userName, member);
		
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
			case "send" :
				ConcurrentHashMap<String, UserInfo> chatMember = ChatMetaData.roomInfo.get(roomNumber).getChatMemberMap();				
				JSONObject responseMessage = new JSONObject();
				responseMessage.put("type", "send");
				responseMessage.put("userName", userName);
				responseMessage.put("content", message);
				
				for (String key : chatMember.keySet()) {
					UserInfo user = chatMember.get(key);
					if (user.getUserName().equals(userName)) {
						continue;
					}
					
					Session userSession = user.getSession();
					userSession.getBasicRemote().sendText(responseMessage.toString());
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
	public void onClose(Session session) throws Exception {
		int roomNumber = (int) session.getUserProperties().get("roomNumber");
		String userName = (String) session.getUserProperties().get("userName");
		ChatMetaData.roomInfo.get(roomNumber).getChatMemberMap().remove(userName);
		
		if (userName.equals(ChatMetaData.roomInfo.get(roomNumber).getCreateUserName())) {
			Map <String, UserInfo> chatMember = ChatMetaData.roomInfo.get(roomNumber).getChatMemberMap();
			
			for (UserInfo user : chatMember.values()) {
				Session userSession = user.getSession();
				if (userSession != null && userSession.isOpen()) {
		            try {
		            	JSONObject responseMessage = new JSONObject();
		    			responseMessage.put("type", "noti");
		    			responseMessage.put("content", "방장님이 채팅을 종료하였습니다.");
		    			userSession.getBasicRemote().sendText(responseMessage.toString());
		                userSession.close(); // 세션 종료
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
			}
			
			ChatMetaData.roomInfo.remove(roomNumber);
			return;
		}
		
		
		Map <String, UserInfo> chatMember = ChatMetaData.roomInfo.get(roomNumber).getChatMemberMap();
		
		if (chatMember.size() == 0) {
			ChatMetaData.roomInfo.remove(roomNumber);
			System.out.println("채팅방 삭제 : " + roomNumber);
			return;
		}
		
		for (String key : chatMember.keySet()) {
			UserInfo user = chatMember.get(key);
			Session userSession = user.getSession();
			userSession.getBasicRemote().sendText(userName + "님이 퇴장하셨습니다.");
		}
		System.out.println("세션 종료 : " + userName);
	}
}

