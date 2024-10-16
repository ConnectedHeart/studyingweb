package git;

import java.util.Scanner;

public class GitSummaryMain {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		GitSummaryExportExcel gitSummary = null;
		boolean correctModeFlag = false;
		while (!correctModeFlag) {
			System.out.println("날짜 검색 기능 사용은 1, commitid 검색 기능 사용은 2를 눌러주세요.");
			String mode = scanner.nextLine();
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
		
		gitSummary.exportExcel();
		
		System.out.println("종료하시려면 아무키나 누를 후 엔터를 입력하세요.");
		scanner.nextLine();
		scanner.close();
	}
}
