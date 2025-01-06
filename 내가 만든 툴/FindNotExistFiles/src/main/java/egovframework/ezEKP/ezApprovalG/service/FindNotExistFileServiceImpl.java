package egovframework.ezEKP.ezApprovalG.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.sqlmap.client.SqlMapSession;

import egovframework.ezEKP.ezApprovalG.dao.FileInfoDAO;
import egovframework.ezEKP.ezApprovalG.dao.FileInfoDAOImpl;
import egovframework.ezEKP.ezApprovalG.vo.DocFileInfoVO;

public class FindNotExistFileServiceImpl implements FindNotExistFileService{
	private SqlMapClient sqlMap = null;
	FileInfoDAO fileInfoDAO = new FileInfoDAOImpl();
	Connection conn = null;
	SqlMapSession session = null;
	public SqlMapSession getSqlMapSession() throws IOException, SQLException {
		Reader reader = null;
		Properties prop = new Properties();
		InputStream inputStream = null;
		String dbProperties = "./resources/properties/db.properties";
		String dbType = "";
		try {
			inputStream  = getClass().getClassLoader().getResourceAsStream(dbProperties);
			if(inputStream!=null) {
		        prop.load(inputStream);
		        dbType = prop.getProperty("Globals.DbType").trim();
			}else {
				throw new FileNotFoundException("db.properties 읽기에 실패하였습니다. 실행파일과 동일한 위치에 resources 폴더가 존재하고, db.properties 파일은 resources/properties/db.properties"
						+ "에 위치해야합니다.");
			}
			
			String resource = "./resources/config/sqlConfig_"+dbType+".xml";
			reader = Resources.getResourceAsReader(resource);
			sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
			conn = sqlMap.getDataSource().getConnection();
			conn.setAutoCommit(false);
			session = sqlMap.openSession(conn);
		}
		finally {
			if(reader!=null) {
				reader.close();
			}
			if(inputStream!=null) {
				inputStream.close();
			}
		}
		return session;
	}
	

	
	public void downloadNotExistFileList(String v_mode, String startYear, String endYear, boolean checkAllFlag) throws SQLException,IOException{
		
		SqlMapSession sqlMapSession = getSqlMapSession();
		String startDate = "";
		String endDate = "";
		String v_checkAll="";
		String companyId = null;
		String tenantId = null;
		String realPath = null;
		String downPath =null;
		
		Properties prop = new Properties();
		InputStream inputStream = null;
		String propFileName = "./resources/properties/config.properties";
		
		try {
			inputStream  = getClass().getClassLoader().getResourceAsStream(propFileName);
			if(inputStream!=null) {
		        prop.load(inputStream);
				companyId = prop.getProperty("config.companyId").trim();
				tenantId = prop.getProperty("config.tenantId").trim();
				realPath = prop.getProperty("config.realPath").trim();
				downPath = prop.getProperty("config.downPath").trim();
			}else {
				throw new FileNotFoundException("config.properties 읽기에 실패하였습니다. 실행파일과 동일한 위치에 resources 폴더가 존재하고, config.properties 파일은 resources/config.properties"
						+ "에 위치해야합니다. 사용되는 키 값 : config.companyId, config.tenantId, config.realPath(db의 저장되어있는 href경로(파일 저장 위치) 앞 경로를 모두 적어주세요.), config.downPath");
			}
	      
		} finally {
			if(inputStream!=null) {
				inputStream.close();
			}
		}
	
		if (checkAllFlag) {
			v_checkAll="TRUE";
		}else {
			v_checkAll = "FALSE";
			startDate = Integer.valueOf(startYear)-1+"-12-31 15:00:00";
			endDate = endYear+"-12-30 15:00:00";
		}
		
		String lineSeparator = System.lineSeparator();
		StringBuilder sb = new StringBuilder();
		String spaceSeparator = " || ";
        sb.append("검색 조건 : ");
        sb.append(v_mode).append(", ").append(startYear).append("~").append(endYear).append(", companyId : ").append(companyId).append(", tenantId : ").append(tenantId);
        sb.append(lineSeparator);
        sb.append(lineSeparator);
        
		try {
			List<DocFileInfoVO> docFileInfoList =null;
			docFileInfoList = fileInfoDAO.getDocFileInfoList(sqlMapSession,companyId,tenantId,v_mode,startDate,endDate,v_checkAll);
			int notExistDocCnt = 0;
			sb.append("### 문서파일  ###").append(lineSeparator);
			
			if (v_mode.equals("END")) {
				sb.append("DOCID").append(spaceSeparator).append("DOCNO").append(spaceSeparator).append("WRITERNAME").append(spaceSeparator)
				.append("WRITERDEPTNAME").append(spaceSeparator).append("DOCTITLE").append(spaceSeparator).append("HREF").append(lineSeparator);
			}else if (v_mode.equals("APR")) {
				sb.append("DOCID").append(spaceSeparator).append("WRITERNAME").append(spaceSeparator)
				.append("WRITERDEPTNAME").append(spaceSeparator).append("DOCTITLE").append(spaceSeparator).append("HREF").append(lineSeparator);
			}
			
			for (DocFileInfoVO docFile : docFileInfoList) {
				String filePath = docFile.getFilepath();
				String fileURL = realPath + filePath;
				File file = new File(fileURL);
				
				if (!file.exists()) {
					if (v_mode.contentEquals("APR")){
						String docType = docFile.getDoctype();
						String functionType = docFile.getFunctiontype();
						String orgDocId = docFile.getOrgdocid();
						if (docType.equals("003") && functionType.equals("011") && orgDocId != null) {
							continue;
						}else{
							notExistDocCnt ++;
							sb.append(docFile.getDocid()).append(spaceSeparator).append(docFile.getWritername()).append(spaceSeparator)
							.append(docFile.getWriterdeptname()).append(spaceSeparator).append(docFile.getFilename()).append(spaceSeparator)
							.append(docFile.getFilepath()).append(lineSeparator);
						}
					}
					else if(v_mode.contentEquals("END")) {
						notExistDocCnt ++;
						sb.append(docFile.getDocid()).append(spaceSeparator).append(docFile.getDocno()).append(spaceSeparator)
						.append(docFile.getWritername()).append(spaceSeparator).append(docFile.getWriterdeptname()).append(spaceSeparator)
						.append(docFile.getFilename()).append(spaceSeparator).append(docFile.getFilepath()).append(lineSeparator);
					}
				}
			}
			
			sb.append(lineSeparator);
			sb.append("존재하지 않는 문서파일 총 : ").append(notExistDocCnt).append("개").append(lineSeparator);
	        sb.append("###------------------------------------").append(lineSeparator);
	        sb.append(lineSeparator);
			
			int notExistAttCnt = 0;
			List<DocFileInfoVO> attFileInfoList = fileInfoDAO.getAttFileInfoList(sqlMapSession,companyId,tenantId,v_mode,startDate,endDate,v_checkAll);
			
			sb.append("DOCID").append(spaceSeparator).append("ATTACHFILESN").append(spaceSeparator)
			.append("ATTACHFILENAME").append(spaceSeparator).append("ATTACHFILEHREF").append(lineSeparator);
			
			for (DocFileInfoVO attFile: attFileInfoList) {
				String filePath = attFile.getFilepath();
				String fileURL = realPath + filePath;
				File file = new File(fileURL);
				
				if (!file.exists()) {
					notExistAttCnt ++;
					sb.append(attFile.getDocid()).append(spaceSeparator).append(attFile.getAttachfilesn()).append(spaceSeparator)
					.append(attFile.getFilename()).append(spaceSeparator).append(attFile.getFilepath()).append(lineSeparator);
				}
				
			}
			
			sb.append(lineSeparator);
			sb.append("존재하지 않는 첨부파일 총 : ").append(notExistAttCnt).append("개").append(lineSeparator);
	        sb.append("###------------------------------------").append(lineSeparator);
			
	        
	        int notExistDocAttCnt = 0;
			List<DocFileInfoVO> docAttFileInfoList = fileInfoDAO.getDocAttFileInfoList(sqlMapSession,companyId,tenantId,v_mode,startDate,endDate,v_checkAll);
			
			sb.append("DOCID").append(spaceSeparator).append("ATTACHSN").append(spaceSeparator)
			.append("ATTACHDOCNAME").append(spaceSeparator).append("ATTACHDOCURL").append(lineSeparator);
			
			
			for (DocFileInfoVO docAttFile : docAttFileInfoList) {
				String filePath = docAttFile.getFilepath();
				String fileURL = realPath + filePath;
				File file = new File(fileURL);
				
				if (!file.exists()) {
					notExistDocAttCnt ++;
					sb.append(docAttFile.getDocid()).append(spaceSeparator).append(docAttFile.getAttachsn()).append(spaceSeparator)
					.append(docAttFile.getFilename()).append(spaceSeparator).append(docAttFile.getFilepath()).append(lineSeparator);
				}
				
			}
			
			sb.append(lineSeparator);
			sb.append("존재하지 않는 문서 첨부 파일 총 : ").append(notExistDocAttCnt).append("개").append(lineSeparator);
	        sb.append("###------------------------------------").append(lineSeparator);
	
			FileWriter fw = null;
			PrintWriter writer = null;
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String nowTime = now.format(formatter);
            String fileName = "notExistFile"+nowTime+".txt";
            File downFile = new File(downPath+File.separator+fileName);
			try {
				if (!downFile.exists()) {
					downFile.createNewFile();
				}
				
				fw = new FileWriter(downFile);
		        writer = new PrintWriter(fw);
		        writer.write(sb.toString());
		        System.out.println("downPath : "+downPath+File.separator+fileName);
			} finally{
				if (writer!=null) {
					writer.close();
				}
				if (fw!=null) {
					fw.close();
				}
			}
		}finally {
			if (sqlMapSession!=null) {
				session.close();
			}
			if (conn!=null) {
				conn.close();
			}
		}
		
	}
}
