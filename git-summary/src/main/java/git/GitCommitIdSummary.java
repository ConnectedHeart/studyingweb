package git;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
public class GitCommitIdSummary extends GitSummaryExportExcel {
	String repoPath = ""; // Git 리포지토리 경로
    String lastCommitId = ""; // 시작 커밋 ID
    String earlyCommitId = ""; // 끝 커밋 ID
    private Properties commitIdProperties = null;
	@Override
	public void createBodys(GitSummaryVO summaryVO) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		RevWalk revWalk = summaryVO.getRevWalk();
		Sheet sheet = summaryVO.getSheet();
		String searchMode = summaryVO.getSearchMode();
		String keyword = summaryVO.getKeyword();
		
		int rowNum = 1;
	
        for (RevCommit commit : revWalk) {
        	
        	String commitMessage = commit.getFullMessage().split("\n")[0];
        	if (commitMessage.toUpperCase().indexOf("Merge") == 0) {
        		// merge commit 제외
        		continue;
        	}
        	
        	List<String> commitMessageInfo = extractKeywords(commitMessage);
        	String authorId = commit.getAuthorIdent().getName();
        	String committerId = commit.getCommitterIdent().getName();
        	
        	if (searchMode.equals("AUTHOR") &&
        	    authorId.toUpperCase().indexOf(keyword.toUpperCase()) < 0 &&
            	(commitIdProperties.getProperty(authorId) == null
            	|| commitIdProperties.getProperty(authorId).indexOf(keyword) < 0)) {
        	}  else if (searchMode.equals("COMMITTER") &&
        		committerId.toUpperCase().indexOf(keyword.toUpperCase()) < 0 &&
        		(commitIdProperties.getProperty(committerId) == null
        		|| commitIdProperties.getProperty(committerId).indexOf(keyword) < 0)) {
        	} else {
                Row row = sheet.createRow(rowNum++);
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

            if (commit.getId().getName().trim().equals(earlyCommitId.trim())) {
            	break;
            }
        }		
	}
	
	@Override
	public void exportExcel(String searchMode, String keyword) {
		repoPath = GitProperties.repoPath; // Git 리포지토리 경로
	    lastCommitId = GitProperties.lastCommitId; // 시작 커밋 ID
	    earlyCommitId = GitProperties.earlyCommitId; // 끝 커밋 ID
	    commitIdProperties = GitProperties.commitIdProperties;
	    
        try (Repository existingRepo = new FileRepositoryBuilder()
	        	    .setGitDir(new File(repoPath))
	        	    .build();
        	RevWalk revWalk = new RevWalk(existingRepo);
        	Workbook workbook = new XSSFWorkbook()) {
	        
            RevCommit lastCommit = revWalk.parseCommit(existingRepo.resolve(lastCommitId));
            RevCommit earlyCommit = revWalk.parseCommit(existingRepo.resolve(earlyCommitId));
            
            if (lastCommit.getCommitterIdent().getWhen().before(earlyCommit.getCommitterIdent().getWhen())) {
            	System.out.println("last : " + lastCommit.getCommitterIdent().getWhen());
            	System.out.println("early : " + earlyCommit.getCommitterIdent().getWhen());
            	System.out.println("lastCommitId 의 커밋 일시가 earlyCommitId의 커밋 일시보다 나중이어야합니다.");
            	return;
            }
            
            revWalk.markStart(revWalk.parseCommit(lastCommit));

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
            excelFilePath = "commits_commitid_" + searchMode.toLowerCase() + "_" + keyword + "_" + formatter.format(now) + ".xlsx";
            
            try (FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
                workbook.write(fileOut);
            }
            System.out.println("Excel 파일이 생성되었습니다: " + excelFilePath);
            System.out.println();
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
}
