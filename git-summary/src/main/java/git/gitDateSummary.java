package git;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.lib.Repository;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class gitDateSummary {
    public static void main(String[] args) {
        String repoPath = "C:\\projects\\ezFlow4Java"; // Git 저장소 경로
        String outputFilePath = "C:\\Users\\NC584\\Desktop\\gitTest\\commits.xlsx"; // 출력할 Excel 파일 경로
        String startDateString = "2024-10-11";
        String endDateString = "2024-10-15";

        try {
            // 날짜 범위 설정
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse(startDateString);
            Date endDate = sdf.parse(endDateString);

            // Git 저장소 열기
            try (Git git = Git.open(new File(repoPath));
                 Workbook workbook = new XSSFWorkbook()) {
                
                Sheet sheet = workbook.createSheet("Commits");
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Commit ID");
                headerRow.createCell(1).setCellValue("Author");
                headerRow.createCell(2).setCellValue("Committer");
                headerRow.createCell(3).setCellValue("Commit Message");

                int rowCount = 1;

                // 커밋 가져오기
                Repository repository = git.getRepository();
                try (RevWalk revWalk = new RevWalk(repository)) {
                    Iterable<RevCommit> commits = git.log().call();
                    for (RevCommit commit : commits) {
                        Date commitDate = new Date(commit.getCommitTime() * 1000L);
                        if (!commitDate.before(startDate) && !commitDate.after(endDate)) {
                            Row row = sheet.createRow(rowCount++);
                            row.createCell(0).setCellValue(commit.getId().getName());
                            row.createCell(1).setCellValue(commit.getAuthorIdent().getName() + " <" + commit.getAuthorIdent().getEmailAddress() + ">");
                            row.createCell(2).setCellValue(commit.getCommitterIdent().getName() + " <" + commit.getCommitterIdent().getEmailAddress() + ">");
                            row.createCell(3).setCellValue(commit.getFullMessage());
                        }
                    }
                }

                // Excel 파일 저장
                try (FileOutputStream fileOut = new FileOutputStream(outputFilePath)) {
                    workbook.write(fileOut);
                }
            }
            System.out.println("Excel 파일이 생성되었습니다: " + outputFilePath);
        } catch (IOException | GitAPIException | java.text.ParseException e) {
            e.printStackTrace();
        }
    }
}