package git;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.jgit.revwalk.RevWalk;

public class GitSummaryVO {
	String searchMode;
	String keyword;
	Workbook workbook;
	Sheet sheet;
	RevWalk revWalk;
	
	public String getSearchMode() {
		return searchMode;
	}
	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Workbook getWorkbook() {
		return workbook;
	}
	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}
	public Sheet getSheet() {
		return sheet;
	}
	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}
	public RevWalk getRevWalk() {
		return revWalk;
	}
	public void setRevWalk(RevWalk revWalk) {
		this.revWalk = revWalk;
	}
	
}
