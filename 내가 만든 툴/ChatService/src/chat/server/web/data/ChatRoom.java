package chat.server.web.data;

import java.util.concurrent.ConcurrentHashMap;

public class ChatRoom {
	private int roomNumber;
	private String roomName;
	private String createDate;
	private int maxPersonCount;
	private ConcurrentHashMap<String, UserInfo> chatMember;
	
	public ChatRoom(int roomNumber, String roomName, String createDate, int maxPersonCount) {
		this.roomNumber = roomNumber;
		this.roomName = roomName;
		this.createDate = createDate;
		this.maxPersonCount = maxPersonCount;
		this.chatMember = new ConcurrentHashMap<>();
	}
	
	
	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public int getMaxPersonCount() {
		return maxPersonCount;
	}
	public void setMaxPersonCount(int maxPersonCount) {
		this.maxPersonCount = maxPersonCount;
	}
	public ConcurrentHashMap<String, UserInfo> getChatMember() {
		return chatMember;
	}
	public void setChatMember(ConcurrentHashMap<String, UserInfo> chatMember) {
		this.chatMember = chatMember;
	}
	
}
