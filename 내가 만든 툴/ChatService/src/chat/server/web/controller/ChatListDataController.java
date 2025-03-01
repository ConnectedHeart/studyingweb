package chat.server.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import chat.server.common.CommonUtil;
import chat.server.web.data.ChatMetaData;
import chat.server.web.data.ChatRoom;
import chat.server.web.data.UserInfo;

@WebServlet("/chatRoomList")
public class ChatListDataController extends HttpServlet {
	CommonUtil commonUtil = new CommonUtil();
	
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("status", "ok");
        jsonResponse.put("data", commonUtil.getChatRoomInfo());

        response.setContentType("application/json");
        
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
}
