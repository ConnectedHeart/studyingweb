package chat.server.web.data;

import java.util.concurrent.ConcurrentHashMap;

public class ChatMetaData {
	public static ConcurrentHashMap<Integer, ChatRoom> roomInfo = new ConcurrentHashMap<Integer, ChatRoom>();
	public static ConcurrentHashMap<String, UserInfo> memberInfo = new ConcurrentHashMap<String, UserInfo>();
}
