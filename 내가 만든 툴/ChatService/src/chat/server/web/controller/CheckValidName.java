package chat.server.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chat.server.web.data.ChatMetaData;
import chat.server.web.data.UserInfo;

@WebServlet("/checkValidName")
public class CheckValidName extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String userName = request.getParameter("inputName");
		UserInfo userInfo = ChatMetaData.memberInfo.get(userName);
		
		if (userInfo != null) {
			response.getOutputStream().print("exist");
		} else {
			response.getOutputStream().print("notExist");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
