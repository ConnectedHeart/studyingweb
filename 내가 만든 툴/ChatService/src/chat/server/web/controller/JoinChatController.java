package chat.server.web.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chat.server.common.CommonUtil;
import chat.server.web.view.JSPView;

@WebServlet("/joinChat")
public class JoinChatController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String roomNumber = request.getParameter("roomNumber");
		
		CommonUtil commonUtil = new CommonUtil(); 
		request.setAttribute("roomNumber", roomNumber);
		request.setAttribute("userName", commonUtil.getCookieValue(request, "userName"));
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPView.getViewPath("chatRoom"));
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
