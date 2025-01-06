package git;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class GitSummaryExportExcel {
	public static final int COMMIT_MSG_HEADER_COUNT = 4; // [분류][유형][voc 번호][커밋메시지]
	public static final String[] headers = {"Commit ID", "분류", "유형", "voc번호", "커밋 메시지", "Author", "Committer", "커밋일시"};
	public void createHeaders(GitSummaryVO summaryVO) throws Exception {
		Workbook workbook = summaryVO.getWorkbook();
		Sheet sheet = summaryVO.getSheet();
		
		CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true); // 폰트 굵게 설정
        headerStyle.setFont(font);
        headerStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN1.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        Row headerRow = sheet.createRow(0);
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle); // 스타일 적용
        }
        
        sheet.setColumnWidth(0, 45 * 256);
        sheet.setColumnWidth(1, 13 * 256);
        sheet.setColumnWidth(2, 13 * 256);
        sheet.setColumnWidth(3, 13 * 256);
        sheet.setColumnWidth(4, 100 * 256);
        sheet.setColumnWidth(5, 20 * 256);
        sheet.setColumnWidth(6, 20 * 256);
        sheet.setColumnWidth(7, 20 * 256);
	}
	
	public List<String> extractKeywords(String text) {
		List<String> keywords = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            keywords.add(matcher.group(1));
        }

        return keywords;
	}
	
	public void createBodys(GitSummaryVO summaryVO) throws Exception {}
	public void exportExcel(String searchMode, String keyword) {}
}
