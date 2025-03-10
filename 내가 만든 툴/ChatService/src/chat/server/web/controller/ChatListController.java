package chat.server.web.controller;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chat.server.web.data.ChatMetaData;
import chat.server.web.data.ChatRoom;
import chat.server.web.view.JSPView;

@WebServlet("/chatList")
public class ChatListController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ConcurrentHashMap<Integer, ChatRoom> roomInfo = ChatMetaData.roomInfo;
		request.setAttribute("roomInfo", roomInfo);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPView.getViewPath("chatList"));
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
