package chat.server.web.data;

import java.util.concurrent.ConcurrentHashMap;

public class ChatMetaData {
	public static ConcurrentHashMap<Integer, ChatRoom> roomInfo;
	public static ConcurrentHashMap<String, UserInfo> memberInfo;
}
