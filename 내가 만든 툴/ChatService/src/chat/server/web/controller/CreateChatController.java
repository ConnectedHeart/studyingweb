package chat.server.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPView.getViewPath("chatRoom"));
		request.setAttribute("roomNumber", roomNumber);
		request.setAttribute("userName", getCookieValue(request, "userName"));
		dispatcher.forward(request, response);
	}
	
	public String getCookieValue(HttpServletRequest request, String cookieName) {
        // 쿠키 배열을 가져옵니다.
        Cookie[] cookies = request.getCookies();

        // 쿠키가 존재하는지 확인
        if (cookies != null) {
            // 쿠키 배열을 순회하면서 원하는 쿠키를 찾습니다.
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    // 쿠키 값을 반환합니다.
                    return cookie.getValue();
                }
            }
        }

        // 쿠키가 없으면 null을 반환합니다.
        return null;
    }
	
}
