package chat.server.web.data;

import javax.websocket.Session;

import org.json.JSONObject;

public class UserInfo {
	private String userName;
	private String gender;
	private Session session;
	
	public UserInfo(String userName, String gender) {
		this.userName = userName;
		this.gender = gender;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	
	public JSONObject toJson() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("userName", this.userName);
		jsonObj.put("gender", this.gender);
		
		return jsonObj;
	}
	
	
}
