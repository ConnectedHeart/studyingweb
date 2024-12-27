package controller;

import java.util.Scanner;

import service.QueryResultDownService;
import service.QueryResultDownServiceImpl;

public class QueryDownController {
	public static void main(String[] args) {
		QueryResultDownService queryDownService = new QueryResultDownServiceImpl();
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(System.in);
			String selectedQuery = queryDownService.showMenu(scanner);
			switch (selectedQuery) {
			case "1":
				System.out.println("이용할 테넌트 아이디를 입력하세요");
				System.out.print("입력 > ");
				String tenantId = scanner.next().trim();
				queryDownService.downloadTenantConfigList(Integer.parseInt(tenantId));
				break;

			default:
				break;
			}
		} catch (Exception e) {
			System.out.println("에러가 발생했습니다.");
			e.printStackTrace();
			scanner.next();
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		
	}
	
}
