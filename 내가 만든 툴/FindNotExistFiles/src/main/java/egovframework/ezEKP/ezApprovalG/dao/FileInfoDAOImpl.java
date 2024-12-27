package egovframework.ezEKP.ezApprovalG.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapSession;

import egovframework.ezEKP.ezApprovalG.vo.DocFileInfoVO;

public class FileInfoDAOImpl implements FileInfoDAO{
	public List<DocFileInfoVO> getDocFileInfoList(SqlMapSession sqlMapSession,String companyId,String tenantId,String v_mode,String startDate,String endDate,String v_checkAll) throws SQLException{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("v_companyID",companyId);
		param.put("v_tenantID",tenantId);
		param.put("v_mode", v_mode);
		param.put("v_startDate",startDate);
		param.put("v_endDate",endDate);
		param.put("v_checkALL",v_checkAll);
		
		
		List<DocFileInfoVO> result = (List<DocFileInfoVO>) sqlMapSession.queryForList("selectDocFileInfoList",param);
		return result;
	}
	
	public List<DocFileInfoVO> getAttFileInfoList(SqlMapSession sqlMapSession,String companyId,String tenantId,String v_mode,String startDate,String endDate,String v_checkAll) throws SQLException{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("v_companyID",companyId);
		param.put("v_tenantID",tenantId);
		param.put("v_mode", v_mode);
		param.put("v_startDate",startDate);
		param.put("v_endDate",endDate);
		param.put("v_checkALL",v_checkAll);
		
		List<DocFileInfoVO> result = (List<DocFileInfoVO>) sqlMapSession.queryForList("selectAttFileInfoList",param);
		return result;
	}
	
	public List<DocFileInfoVO> getDocAttFileInfoList(SqlMapSession sqlMapSession,String companyId,String tenantId,String v_mode,String startDate,String endDate,String v_checkAll) throws SQLException{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("v_companyID",companyId);
		param.put("v_tenantID",tenantId);
		param.put("v_mode", v_mode);
		param.put("v_startDate",startDate);
		param.put("v_endDate",endDate);
		param.put("v_checkALL",v_checkAll);
		
		List<DocFileInfoVO> result = (List<DocFileInfoVO>) sqlMapSession.queryForList("selectDocAttFileInfoList",param);
		return result;
	}
}
