package git;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
public class GitCommitIdSummary {
	 public static void main(String[] args) {
	        String repoPath = "C:\\projects\\ezFlow4Java"; // Git 리포지토리 경로
	        String branchName = "jrel2";
	        String startCommitId = "0cd8127b490f8d2d59d5d922919accaee937f511"; // 시작 커밋 ID
	        String endCommitId = "bcc1273b50b430200e2fffe7e39ae33ccb87e6c7"; // 끝 커밋 ID
	        String excelFilePath = "C:\\Users\\NC584\\Desktop\\gitTest\\commits2.xlsx"; // 엑셀 파일 경로
	        
	        try (Git git = Git.open(new File(repoPath))) {
	        	RevWalk startRevWalk = new RevWalk(git.getRepository());
	        	RevWalk endRevWalk = new RevWalk(git.getRepository());
	            // 특정 브랜치의 HEAD 가져오기

	            // 시작 커밋과 끝 커밋의 ObjectId 가져오기
	            RevCommit startCommit = startRevWalk.parseCommit(git.getRepository().resolve(startCommitId));
	            RevCommit endCommit = endRevWalk.parseCommit(git.getRepository().resolve(endCommitId));

	            // 시작 커밋부터 끝 커밋까지 순회
	            startRevWalk.markStart(startCommit);
	            endRevWalk.markStart(endCommit);

	            try (Workbook workbook = new XSSFWorkbook()) {
	                Sheet sheet = workbook.createSheet("Commits");
	                Row headerRow = sheet.createRow(0);
	                headerRow.createCell(0).setCellValue("Commit ID");
	                headerRow.createCell(1).setCellValue("Message");
	                headerRow.createCell(2).setCellValue("Author");
	                headerRow.createCell(3).setCellValue("Committer");
	                headerRow.createCell(4).setCellValue("Date");

	                int rowNum = 1;
	                for (RevCommit commit : startRevWalk) {
	                    // 현재 커밋이 시작 커밋과 끝 커밋 사이인지 확인
	                    if (commit.getId().equals(endCommit.getId())) {
	                        break; // 종료
	                    }

	                    Row row = sheet.createRow(rowNum++);
	                    row.createCell(0).setCellValue(commit.getId().getName());
	                    row.createCell(1).setCellValue(commit.getFullMessage().split("\n")[0]); // 첫 줄만
	                    row.createCell(2).setCellValue(commit.getAuthorIdent().getName());
	                    row.createCell(3).setCellValue(commit.getCommitterIdent().getName());
	                    row.createCell(4).setCellValue(commit.getCommitterIdent().getWhen().toString());
	                }

	                try (FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
	                    workbook.write(fileOut);
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
}
