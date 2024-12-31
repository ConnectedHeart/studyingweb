package git;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GitCommitCompare {
	public void compareCommits(Scanner scanner) {
		
		String compareCommitExcel = GitProperties.compareCommitExcel; // 기준 엑셀에서 제거할 엑셀내용
		String baseCommitExcel  = GitProperties.baseCommitExcel; // 기준 엑셀
		FileInputStream fisA = null;
		FileInputStream fisB = null;
		XSSFWorkbook workbookA = null;
		XSSFWorkbook workbookB = null;
		XSSFWorkbook workbookC = null;
		FileOutputStream fos = null;
				
		try {
	        // 엑셀 파일 읽기
	        fisA = new FileInputStream(new File(compareCommitExcel));
	        fisB = new FileInputStream(new File(baseCommitExcel));
	
	        workbookA = new XSSFWorkbook(fisA);
	        workbookB = new XSSFWorkbook(fisB);
	
	        // 첫 번째 시트 선택 (A파일과 B파일 모두 첫 번째 시트)
	        Sheet sheetA = workbookA.getSheetAt(0);
	        Sheet sheetB = workbookB.getSheetAt(0);
	        
	        boolean checkIndexFlag = false;
	        int index = 0;
	        while (!checkIndexFlag) {
	        	System.out.println("비교할 컬럼의 인덱스를 입력해주세요. 0부터 시작");
	    		String indexStr = scanner.nextLine().trim();
	    		try {
	    			index = Integer.parseInt(indexStr);
	    			checkIndexFlag = true;
	    		} catch (Exception e) {
	    			System.out.println("잘못입력하셨습니다. 숫자만 입력해주세요.");
	    		}
	        }
			
	        Set<String> columnAValues = new HashSet<>();
	        for (Row row : sheetA) {
	            Cell cell = row.getCell(index);  
	            if (cell != null) {
	                columnAValues.add(cell.toString());
	            }
	        }
	
	        // B파일에서 해당 값이 있으면 해당 행 삭제
	        workbookC = new XSSFWorkbook();
	        Sheet cSheet = workbookC.createSheet("Commits");
	        GitSummaryVO summaryVO = new GitSummaryVO();
	        summaryVO.setWorkbook(workbookC);
	        summaryVO.setSheet(cSheet);
	        GitSummaryExportExcel exportExcel = new GitSummaryExportExcel();
	        exportExcel.createHeaders(summaryVO);
	        
	        int cRowIdx = 1;
	        Iterator<Row> iteratorB = sheetB.iterator();
	        while (iteratorB.hasNext()) {
	            Row rowB = iteratorB.next();
	            Cell cellB = rowB.getCell(index);  
	            if (cellB != null && columnAValues.contains(cellB.toString())) {
	                iteratorB.remove(); // B파일에서 해당 행 삭제
	            } else {
	            	if (rowB != null) {
	            		Row targetRow = cSheet.createRow(cRowIdx);
	            		int cellCnt = GitSummaryExportExcel.headers.length;
	            		
	            		for (int i = 0; i < cellCnt; i++) {
	            			Cell cell = rowB.getCell(i);
	            			Cell targetCell = targetRow.createCell(i);
	            			if (cell != null) {
	            				targetCell.setCellValue(cell.toString());
	            			}
	            		}
	            		
	            		cRowIdx++;
	            	}
	            }
	        }
	
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	        Date now = new Date();
	        String fileName = "commitCompare" + "_" + formatter.format(now) + ".xlsx";
	        // 수정된 B파일을 저장
	        fos = new FileOutputStream(new File(fileName));
	        workbookC.write(fos);
		} catch (Exception e) {
			System.out.println("오류가 발생했습니다.");
			e.printStackTrace();
		} finally {
			// 리소스 닫기
			try {
				if (fisA != null) {
					fisA.close();
				}
				if (fisB != null) {
					fisB.close();
				}
				if (fos != null) {
					fos.close();
				}
				if (workbookA != null) {
					workbookA.close();
				}
				if (workbookB != null) {
					workbookB.close();
				}
				
				if (workbookC != null) {
					workbookC.close();
				}
			} catch (Exception e) {
				System.out.println("오류가 발생했습니다.");
				e.printStackTrace();
			}
		}

        System.out.println("baseCommitExcel에만 존재하는 커밋 다운 완료");
	}
	
}
