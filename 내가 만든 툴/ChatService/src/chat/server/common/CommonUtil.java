package chat.server.common;

import java.net.URLDecoder;
import java.util.Base64;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import chat.server.web.data.ChatMetaData;
import chat.server.web.data.ChatRoom;
import chat.server.web.data.UserInfo;

public class CommonUtil {
	public String getCookieValue(HttpServletRequest request, String cookieName) {
        // 쿠키 배열을 가져옵니다.
        Cookie[] cookies = request.getCookies();

        // 쿠키가 존재하는지 확인
        if (cookies != null) {
            // 쿠키 배열을 순회하면서 원하는 쿠키를 찾습니다.
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    // 쿠키 값을 반환합니다.
                	String cookieValue = "";
                	try {
                		cookieValue = URLDecoder.decode(cookie.getValue(), "UTF-8");
                	} catch (Exception e) {
                		e.printStackTrace();
                	}
                	
                    return cookieValue;
                }
            }
        }

        // 쿠키가 없으면 null을 반환합니다.
        return null;
    }
	
	public JSONObject getChatRoomInfo() {
		JSONObject jsonObject = new JSONObject();
		for (Map.Entry<Integer, ChatRoom> entry : ChatMetaData.roomInfo.entrySet()) {
            ChatRoom chatRoom = entry.getValue();
            JSONObject chatRoomObj = new JSONObject();
            chatRoomObj.put("roomNumber", chatRoom.getRoomNumber());
            chatRoomObj.put("roomName", chatRoom.getRoomName());
            chatRoomObj.put("createDate", chatRoom.getCreateDate());
            chatRoomObj.put("maxPersonCount", chatRoom.getMaxPersonCount());
            chatRoomObj.put("createUserName", chatRoom.getCreateUserName());
            
            JSONObject chatMemberMap = new JSONObject();
            for (Map.Entry<String, UserInfo> chatMember : chatRoom.getChatMemberMap().entrySet()) {
            	chatMemberMap.put(chatMember.getKey(), chatMember.getValue().toJson());
            }
            chatRoomObj.put("chatMemberMap", chatMemberMap);
            
            jsonObject.put(entry.getKey() + "", chatRoomObj);
        }
		
		return jsonObject;
	}
}
