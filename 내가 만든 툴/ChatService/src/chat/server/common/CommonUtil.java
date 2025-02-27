package chat.server.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CommonUtil {
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
