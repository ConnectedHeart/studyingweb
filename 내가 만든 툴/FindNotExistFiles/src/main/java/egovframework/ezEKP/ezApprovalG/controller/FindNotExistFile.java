package egovframework.ezEKP.ezApprovalG.controller;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import egovframework.ezEKP.ezApprovalG.service.FindNotExistFileService;
import egovframework.ezEKP.ezApprovalG.service.FindNotExistFileServiceImpl;

public class FindNotExistFile {
	public static void main(String[] args) {
		FindNotExistFileService findNexistFileSer = new FindNotExistFileServiceImpl();
		String startYear = "";
		String endYear = "";
		String v_mode = "";
		String[] availableYear = null;
		
		LocalDate now = LocalDate.now();
		int todayYear = now.getYear();
		int startYearInt = 1900;
		availableYear = new String[todayYear-startYearInt+2];
		availableYear[0] = "ALL";
		
		for (int year = startYearInt, i = 1; year <= todayYear; year++ ,i++) {
			availableYear[i] = year+"";
		}
		
		Scanner scanner = new Scanner(System.in);
		String[] modeCase = {"APR","END"};
		boolean goNextStepFlag = false;
		
		do {
			System.out.println("어떤 테이블을 조회하시겠습니까? (진행 : APR , 완료 : END, 종료 : e)");
			System.out.println("입력 : >");
			v_mode = scanner.nextLine().trim();
			v_mode = v_mode.toUpperCase();
			
			goNextStepFlag = checkGoNextStep(v_mode,modeCase);
			
		} while (!goNextStepFlag);
		
		goNextStepFlag = false;
		boolean checkAllFlag = false;
		
		while (!goNextStepFlag) {
			System.out.println("검색 시작 년도를 입력하세요. 모든 년도 검색은 ALL 입력, ex)2023  종료 : e");
			startYear = scanner.nextLine().trim();
			goNextStepFlag = checkGoNextStep(startYear,availableYear);
			if (startYear.equalsIgnoreCase("ALL")) {
				startYear = startYear.toUpperCase();
				endYear = "ALL";
				checkAllFlag = true;
			}
		}
		
		goNextStepFlag = false;
		
		while (!goNextStepFlag&&!checkAllFlag) {
			if (!startYear.equalsIgnoreCase("ALL")) {
				System.out.println("검색 끝 년도를 입력하세요. ex)2023 종료 : e");
				endYear = scanner.nextLine().trim();
				goNextStepFlag = checkGoNextStep(endYear,availableYear);
				if (endYear.equalsIgnoreCase("ALL")) {
					startYear = "ALL";
					checkAllFlag = true;
				}
			}
		}
		
		System.out.println("선택 테이블 : " + v_mode);
		System.out.println("startYear : "+startYear);
		System.out.println("endYear : "+endYear);
		System.out.println("checkAllFlag : "+checkAllFlag);
		
		try {
			findNexistFileSer.downloadNotExistFileList(v_mode, startYear,endYear,checkAllFlag);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean checkGoNextStep(String inputStr,String[] caseStr) {
		boolean goNextStepFlag = false;
		if (inputStr.equalsIgnoreCase("e")) {
			System.out.println("프로그램을 종료합니다.");
			System.exit(0);
		}
		
		for (String compareStr : caseStr) {
			if (compareStr.equalsIgnoreCase(inputStr.trim())) {
				goNextStepFlag = true;
				break;
			}
		}
		if (!goNextStepFlag) {
			System.out.println("입력값을 잘못 입력했습니다. 형식에 맞게 다시 입력해주세요.");
		}
		return goNextStepFlag;
	}
	
	
	public static void checkExit(String inputStr) {
		if (inputStr.equalsIgnoreCase("e")) {
			System.out.println("프로그램을 종료합니다.");
			System.exit(0);
		}
	}
}
