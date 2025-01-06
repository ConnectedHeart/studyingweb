package egovframework.ezEKP.ezApprovalG.dao;

import java.io.Reader;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class SqlMapConfig {
	private static SqlMapClient sqlMap;
	static {
		try {
			String resource = "config/sqlConfig.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static SqlMapClient getSqlMapInstance() {
		return sqlMap;
	}
}
