package chat.server.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import chat.server.web.data.ChatMetaData;
@WebServlet("/checkParticipation")
public class CheckParticipation extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jsonResponse = new JSONObject();
		PrintWriter out = response.getWriter();
		int roomNumber = Integer.parseInt(request.getParameter("roomNumber"));
		if (ChatMetaData.roomInfo.get(roomNumber) == null) {
			jsonResponse.put("status", "delete");
			out.print(jsonResponse.toString());
			out.flush();
			return;
		}
		
		int maxPersonCount = ChatMetaData.roomInfo.get(roomNumber).getMaxPersonCount();
		int currentChatPersonCnt = ChatMetaData.roomInfo.get(roomNumber).getChatMemberMap().size();
		if (currentChatPersonCnt < maxPersonCount) {
			jsonResponse.put("status", "ok");
		} else {
			jsonResponse.put("status", "full");
		}
		
		out.print(jsonResponse.toString());
		out.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
