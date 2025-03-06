package chat.server.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chat.server.web.data.ChatMetaData;
import chat.server.web.data.ChatRoom;

@WebServlet("/checkValidRoom")
public class CheckValidRoom extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int roomNumber = Integer.parseInt(request.getParameter("roomNumber"));
		ChatRoom chatRoom = ChatMetaData.roomInfo.get(roomNumber);
		if (chatRoom != null) {
			response.getWriter().write("exist");
		} else {
			response.getWriter().write("notExist");
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
