package egovframework.ezEKP.ezApprovalG.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapSession;

import egovframework.ezEKP.ezApprovalG.vo.DocFileInfoVO;

public interface FileInfoDAO{
	public List<DocFileInfoVO> getDocFileInfoList(SqlMapSession sqlMapSession,String companyId,String tenantId,String v_mode,String startDate,String endDate,String v_checkAll) throws SQLException;
	public List<DocFileInfoVO> getAttFileInfoList(SqlMapSession sqlMapSession,String companyId,String tenantId,String v_mode,String startDate,String endDate,String v_checkAll) throws SQLException;
	public List<DocFileInfoVO> getDocAttFileInfoList(SqlMapSession sqlMapSession,String companyId,String tenantId,String v_mode,String startDate,String endDate,String v_checkAll) throws SQLException;
}
