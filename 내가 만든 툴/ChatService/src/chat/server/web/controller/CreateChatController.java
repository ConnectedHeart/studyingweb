package chat.server.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chat.server.web.data.ChatMetaData;
import chat.server.web.data.ChatRoom;
import chat.server.web.view.JSPView;

@WebServlet("/createChat")
public class CreateChatController extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPView.getViewPath("settingRoomInfo"));
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String roomName = request.getParameter("roomName");
		int roomNumber = ChatMetaData.roomInfo.size() + 1;
		int maxPersonCount = Integer.parseInt(request.getParameter("maxPersonCount"));
		Date nowDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createDate = sdf.format(nowDate);
		ChatRoom chatRoom = new ChatRoom(roomNumber, roomName, createDate, maxPersonCount);
		ChatMetaData.roomInfo.put(roomNumber, chatRoom);
		System.out.println("ChatMemberData roomInfo : " + ChatMetaData.roomInfo.size());
		System.out.println("ChatMemberData roomNumber : " + roomNumber);
		System.out.println("ChatRoom roomInfo : " + chatRoom.getChatMember());
		
		response.sendRedirect("/joinChat?roomNumber=" + roomNumber);
	}
	
}
