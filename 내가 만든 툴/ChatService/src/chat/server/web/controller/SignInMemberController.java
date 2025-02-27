package chat.server.web.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chat.server.web.data.ChatMetaData;
import chat.server.web.data.UserInfo;
import chat.server.web.view.JSPView;

@WebServlet("/signIn")
public class SignInMemberController extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPView.getViewPath("signInMember"));
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String userName = request.getParameter("userName");
		String gender = request.getParameter("gender");
		
		UserInfo user = new UserInfo(userName, gender);
		
		ChatMetaData.memberInfo.put(userName, user);
		System.out.println("ChatMemberData memberInfo : " + ChatMetaData.memberInfo.size());
		Cookie userCookie = new Cookie("userName", userName);
        userCookie.setMaxAge(60 * 60 * 24); // 쿠키 유효 시간 1일 (초 단위)
        
        // 응답에 쿠키 추가
        response.addCookie(userCookie);
		
        String redirectUrl = "/chatList";
        response.sendRedirect(redirectUrl);
	}
}
