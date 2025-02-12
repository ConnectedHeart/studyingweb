package chat.server.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chat.server.web.data.ChatMetaData;
import chat.server.web.data.ChatRoom;
import chat.server.web.view.JSPView;

public class CreateChatController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPView.getViewPath("settingRoomInfo"));
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String roomName = request.getParameter("roomName");
		int roomNumber = ChatMetaData.roomInfo.size() + 1;
		int maxPersonCount = Integer.parseInt(request.getParameter("maxPersonCount"));
		Date nowDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createDate = sdf.format(nowDate);
		ChatRoom chatRoom = new ChatRoom(roomNumber, roomName, createDate, maxPersonCount);
		ChatMetaData.roomInfo.put(roomNumber, chatRoom);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPView.getViewPath("chatRoom"));
		request.setAttribute("roomNumber", roomNumber);
		dispatcher.forward(request, response);
	}
	
}
