package git;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class GitProperties {
	public static String repoPath = "";
	public static String lastCommitId = "";
	public static String earlyCommitId = "";
	public static String startDate = "";
	public static String endDate = "";
	public static String branchName = "";
	public static Properties commitIdProperties = null;
	public static void initProperties() {
		Properties gitProperties = new Properties();
		Properties memberProperties = new Properties();
		
		try (FileInputStream input = new FileInputStream("gitconfig.properties")) {
			gitProperties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		try (InputStreamReader input = new InputStreamReader(new FileInputStream("member.properties"), StandardCharsets.UTF_8)) {
			memberProperties.load(input);
			commitIdProperties = memberProperties;
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		repoPath = gitProperties.getProperty("git.repoPath").trim();
		lastCommitId = gitProperties.getProperty("git.lastCommitId").trim();
		earlyCommitId = gitProperties.getProperty("git.earlyCommitId").trim();
		startDate = gitProperties.getProperty("git.startDate").trim();
		endDate = gitProperties.getProperty("git.endDate").trim();
		branchName = gitProperties.getProperty("git.branchName").trim();
	}
}
