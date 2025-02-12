package chat.server.web.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chat.server.web.data.ChatMetaData;
import chat.server.web.data.UserInfo;
import chat.server.web.view.JSPView;

public class SignInMemberController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPView.getViewPath("signInMember"));
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String gender = request.getParameter("gender");
		
		UserInfo user = new UserInfo(userName, gender);
		
		ChatMetaData.memberInfo.put("userName", user);
		
		Cookie userCookie = new Cookie("username", userName);
        userCookie.setMaxAge(60 * 60 * 24); // 쿠키 유효 시간 1일 (초 단위)
        
        // 응답에 쿠키 추가
        response.addCookie(userCookie);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPView.getViewPath("chatList"));
		dispatcher.forward(request, response);
	}
}
