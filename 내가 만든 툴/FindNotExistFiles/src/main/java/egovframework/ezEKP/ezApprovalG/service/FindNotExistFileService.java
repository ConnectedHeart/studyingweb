package egovframework.ezEKP.ezApprovalG.service;

import java.io.IOException;
import java.sql.SQLException;

import com.ibatis.sqlmap.client.SqlMapSession;

public interface FindNotExistFileService {
	public void downloadNotExistFileList(String v_mode, String startYear, String endYear, boolean checkAllFlag) throws SQLException,IOException;
	public SqlMapSession getSqlMapSession() throws IOException, SQLException;
}
