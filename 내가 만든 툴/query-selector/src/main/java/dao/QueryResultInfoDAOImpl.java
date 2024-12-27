package dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapSession;

import vo.TenantConfigVO;

public class QueryResultInfoDAOImpl implements QueryResultInfoDAO {
	public List<TenantConfigVO> getTenantConfigList(SqlMapSession sqlMapSession, int tenantId) throws SQLException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tenantId", tenantId);
		
		List<TenantConfigVO> result = (List<TenantConfigVO>) sqlMapSession.queryForList("selectTenantConfigList", param);
		return result;	
	}
}
