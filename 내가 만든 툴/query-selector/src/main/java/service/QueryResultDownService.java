package service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.ibatis.sqlmap.client.SqlMapSession;

public interface QueryResultDownService {
	public void downloadTenantConfigList(int tenantId) throws Exception;
	public SqlMapSession getSqlMapSession() throws IOException, SQLException;
	public String showMenu(Scanner scanner) throws Exception;
}
