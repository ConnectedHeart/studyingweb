package git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.lib.Repository;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.text.SimpleDateFormat;

public class GitDateSummary extends GitSummaryExportExcel {
	private String repoPath = ""; // Git 저장소 경로
    private String startDateString = "";
    private String endDateString = "";
    private String branchName = "";
    private Properties commitIdProperties = null;
    Date startDate = null;
    Date endDate = null;
	@Override
	public void createBodys(GitSummaryVO summaryVO) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		RevWalk revWalk = summaryVO.getRevWalk();
		Sheet sheet = summaryVO.getSheet();
		String searchMode = summaryVO.getSearchMode();
		String keyword = summaryVO.getKeyword();
		
		int rowCount = 1;
        // 커밋 가져오기
        for (RevCommit commit : revWalk) {
        	String commitMessage = commit.getFullMessage().split("\n")[0];
        	if (commitMessage.toUpperCase().indexOf("Merge".toUpperCase()) == 0) {
        		// merge commit 제외
        		continue;
        	}
        	
            Date commitDate = commit.getCommitterIdent().getWhen();
            
            if (commitDate.before(startDate)) {
            	// revWalk는 최신 커밋부터 순서대로 반복문 돌기 때문에 시작 날짜보다 이전 날짜 커밋이 발견되면 멈춤. 
            	break;
            }
            
            if (!commitDate.before(startDate) && !commitDate.after(endDate)) {
            	List<String> commitMessageInfo = extractKeywords(commitMessage);
            	String authorId = commit.getAuthorIdent().getName();
            	String committerId = commit.getCommitterIdent().getName();
            	
            	if (searchMode.equals("AUTHOR") &&
            		authorId.toUpperCase().indexOf(keyword.toUpperCase()) < 0 &&
            		(commitIdProperties.getProperty(authorId) == null
            		|| commitIdProperties.getProperty(authorId).indexOf(keyword) < 0)) {
            		continue;
            	} else if (searchMode.equals("COMMITTER") &&
            		committerId.toUpperCase().indexOf(keyword.toUpperCase()) < 0 &&
            		(commitIdProperties.getProperty(committerId) == null
            		|| commitIdProperties.getProperty(committerId).indexOf(keyword) < 0)) {
            		continue;
            	}
            	
            	Row row = sheet.createRow(rowCount++);
            	if (commitMessageInfo.size() < COMMIT_MSG_HEADER_COUNT) {
                	row.createCell(0).setCellValue(commit.getId().getName());
                	row.createCell(1);
                	row.createCell(2);
                	row.createCell(3);
                	row.createCell(4).setCellValue(commitMessage);
                	row.createCell(5).setCellValue(commitIdProperties.getProperty(authorId) == null ? authorId : commitIdProperties.getProperty(authorId));
                	row.createCell(6).setCellValue(commitIdProperties.getProperty(committerId) == null ? committerId : commitIdProperties.getProperty(committerId));
                	row.createCell(7).setCellValue(dateFormat.format(commit.getCommitterIdent().getWhen()));
                } else {
                	row.createCell(0).setCellValue(commit.getId().getName());
                	row.createCell(1).setCellValue(commitMessageInfo.get(0).trim());
                	row.createCell(2).setCellValue(commitMessageInfo.get(1).trim());
                	row.createCell(3).setCellValue(commitMessageInfo.get(2).trim());
                	row.createCell(4).setCellValue(commitMessageInfo.get(3).trim());
                	row.createCell(5).setCellValue(commitIdProperties.getProperty(authorId) == null ? authorId : commitIdProperties.getProperty(authorId));
                	row.createCell(6).setCellValue(commitIdProperties.getProperty(committerId) == null ? committerId : commitIdProperties.getProperty(committerId));
                	row.createCell(7).setCellValue(dateFormat.format(commit.getCommitterIdent().getWhen()));
                }
            }
        }		
	}
	@Override
	public void exportExcel(String searchMode, String keyword) {
		repoPath = GitProperties.repoPath; // Git 저장소 경로
	    startDateString = GitProperties.startDate;
	    endDateString = GitProperties.endDate;
	    branchName = GitProperties.branchName;
	    commitIdProperties = GitProperties.commitIdProperties;
		try {
	        try (Repository existingRepo = new FileRepositoryBuilder()
	        	    .setGitDir(new File(repoPath))
	        	    .build();
	        		RevWalk revWalk = new RevWalk(existingRepo);
	            Workbook workbook = new XSSFWorkbook()) {
	        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            startDate = sdf.parse(startDateString);
	            endDate = sdf.parse(endDateString);
	            Calendar calendar = Calendar.getInstance();
	            calendar.setTime(endDate); // 현재 날짜 설정
	            calendar.add(Calendar.DAY_OF_MONTH, 1);
	            endDate = calendar.getTime();
	        	if (!startDate.before(endDate)) {
	        		System.out.println("startDate가 endDate보다 같거나 이른 날짜여야합니다.");
	        		return;
	        	}
	        	
        		RevCommit startCommit = revWalk.parseCommit(existingRepo.resolve(branchName));
        		
	            revWalk.markStart(revWalk.parseCommit(startCommit));
	            
	            Sheet sheet = workbook.createSheet("Commits");
	            
	            GitSummaryVO summaryVO = new GitSummaryVO();
	            summaryVO.setWorkbook(workbook);
        		summaryVO.setRevWalk(revWalk);
        		summaryVO.setSheet(sheet);
        		summaryVO.setKeyword(keyword);
        		summaryVO.setSearchMode(searchMode);
        		
	            createHeaders(summaryVO);
	            createBodys(summaryVO);
	            
	            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	            Date now = new Date();
	            String excelFilePath = ""; // 출력할 Excel 파일 경로
	            excelFilePath = "commits_date_" + searchMode.toLowerCase() + "_" + keyword + "_" + formatter.format(now) + ".xlsx";
	            // Excel 파일 저장
	            try (FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
	                workbook.write(fileOut);
	            }
	            System.out.println("Excel 파일이 생성되었습니다: " + excelFilePath);
	            System.out.println();
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
}