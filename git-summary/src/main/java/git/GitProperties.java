package git;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GitProperties {
	public static String repoPath = "";
	public static String startCommitId = "";
	public static String endCommitId = "";
	public static String excelFilePath = "";
	public static String startDateString = "";
	public static String endDateString = "";
	public static String branchName = "";
	
	static {
		Properties properties = new Properties();
		try (FileInputStream input = new FileInputStream("gitconfig.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		repoPath = properties.getProperty("git.repoPath");
		startCommitId = properties.getProperty("git.startCommitId");
		endCommitId = properties.getProperty("git.endCommitId");
		excelFilePath = properties.getProperty("git.excelFilePath");
		startDateString = properties.getProperty("git.startDateString");
		endDateString = properties.getProperty("git.endDateString");
		branchName = properties.getProperty("git.branchName");
	}
}
