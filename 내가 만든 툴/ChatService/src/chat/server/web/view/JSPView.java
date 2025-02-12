package chat.server.web.view;

public class JSPView {
	private final static String prefix = "/WEB-INF/jsp/";
	private final static String suffix = ".jsp";
	
	public static String getViewPath(String path) {
		return prefix + path + suffix;
	}
	
}
