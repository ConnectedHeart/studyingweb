package git;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.jgit.revwalk.RevWalk;

public class GitSummaryExportExcel {
	public void createHeaders(Workbook workbook, Sheet sheet) throws Exception {
		CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true); // 폰트 굵게 설정
        headerStyle.setFont(font);
        headerStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN1.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        Row headerRow = sheet.createRow(0);
        
        String[] headers = {"Commit ID", "Message", "Author", "Committer", "Date"};
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle); // 스타일 적용
        }
        
        sheet.setColumnWidth(0, 45 * 256);
        sheet.setColumnWidth(1, 100 * 256);
        sheet.setColumnWidth(2, 20 * 256);
        sheet.setColumnWidth(3, 20 * 256);
        sheet.setColumnWidth(4, 20 * 256);
	}
	
	public void createBodys(Sheet sheet, RevWalk revWalk) throws Exception {}
	public void exportExcel() {}
}
