package dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapSession;

import vo.TenantConfigVO;


public interface QueryResultInfoDAO {
	public List<TenantConfigVO> getTenantConfigList(SqlMapSession sqlMapSession, int tenantId) throws SQLException;
}
