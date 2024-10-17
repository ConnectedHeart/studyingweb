package git;

import java.util.Scanner;

public class GitSummaryMain {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean exitFlag = false;
		while (!exitFlag) {
			start(scanner);
			System.out.println("재시작은 r, 종료하시려면 r키를 제외한 아무키나 누른 후 엔터를 입력하세요.");
			String endCode = scanner.nextLine().trim();
			System.out.println();
			switch (endCode) {
			case "r":
			case "R":
			case "ㄱ":
				break;
			default:
				scanner.close();
				exitFlag = true;
				break;
			}
		}
		
	}
	
	public static void start(Scanner scanner) {
		GitProperties.initProperties();
		GitSummaryExportExcel gitSummary = null;
		boolean correctModeFlag = false;
		while (!correctModeFlag) {
			System.out.println("날짜 검색 기능 사용은 1, commitid 검색 기능 사용은 2를 눌러주세요.");
			String mode = scanner.nextLine().trim();
			if (mode.equals("1")) {
				gitSummary = new GitDateSummary();
				correctModeFlag = true;
			} else if (mode.equals("2")) {
				gitSummary = new GitCommitIdSummary();
				correctModeFlag = true;
			} else {
				System.out.println("잘못 입력하셨습니다.");
			}
		}
		
		correctModeFlag = false;
		String searchMode = "ALL";
		String keyword = "";
		while (!correctModeFlag) {
			System.out.println("전체 사용자 검색 기능 사용은 1, Author 검색 기능 사용은 2, Committer 검색 기능 사용은 3을 눌러주세요.");
			String mode = scanner.nextLine().trim();
			if (mode.equals("1")) {
				correctModeFlag = true;
				System.out.println("전체 사용자 검색");
			} else if (mode.equals("2")) {
				searchMode = "AUTHOR";
				correctModeFlag = true;
			} else if (mode.equals("3")) {
				searchMode = "COMMITTER";
				correctModeFlag = true;
			} else {
				System.out.println("잘못 입력하셨습니다.");
			}
			
			if (!searchMode.equals("ALL")) {
				System.out.print("keyword를 입력 하세요 : ");
				keyword = scanner.nextLine();
				keyword = keyword.trim();
				System.out.println(searchMode + "검색 : " + keyword);
			}
		}
		
		gitSummary.exportExcel(searchMode, keyword);
	}
}
