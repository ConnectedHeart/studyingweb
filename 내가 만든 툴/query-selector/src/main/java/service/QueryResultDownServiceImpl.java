package service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.sqlmap.client.SqlMapSession;

import dao.QueryResultInfoDAO;
import dao.QueryResultInfoDAOImpl;
import vo.TenantConfigVO;

public class QueryResultDownServiceImpl implements QueryResultDownService {
	private SqlMapClient sqlMap = null;
	private QueryResultInfoDAO fileInfoDAO = null;
	
	public QueryResultDownServiceImpl() {
		this.fileInfoDAO = new QueryResultInfoDAOImpl();
	}
	
	Connection conn = null;
	SqlMapSession session = null;
	public SqlMapSession getSqlMapSession() throws IOException, SQLException {
		FileInputStream reader = null;
		Properties prop = new Properties();
		String dbProperties = "./resources/properties/db.properties";
		String dbType = "";
		FileInputStream input = null;
		try {
			System.out.println(System.getProperty("user.dir"));
			input = new FileInputStream(dbProperties);
			prop.load(input);
			if (input != null) {
		        prop.load(input);
		        dbType = prop.getProperty("Globals.DbType").trim();
			}
			
			String resource = "./resources/config/sqlConfig_" +dbType+".xml";
			reader = new FileInputStream(resource);
		} catch (Exception e) {
			e.printStackTrace();
			if (reader != null) {
				reader.close();
			}
			
			if (input != null) {
				input.close();
			}
			
			throw new FileNotFoundException("db.properties 읽기에 실패하였습니다. 실행파일과 동일한 위치에 resources 폴더가 존재하고, db.properties 파일은 resources/properties/db.properties"
					+ "에 위치해야합니다.");
		}
		try {
			sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
			conn = sqlMap.getDataSource().getConnection();
			conn.setAutoCommit(false);
			session = sqlMap.openSession(conn);
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("db 연결에 실패하였습니다. db.properties 내용을 확인하세요");
		} finally {
			if(reader != null) {
				reader.close();
			}
			if(input != null) {
				input.close();
			}
		}
		return session;
	}
	
	public void downloadTenantConfigList(int tenantId) throws Exception {
		SqlMapSession sqlMapSession = getSqlMapSession();
		List<TenantConfigVO> tenantConfigList = fileInfoDAO.getTenantConfigList(sqlMapSession, tenantId);
		try {
			try (Workbook workbook = new XSSFWorkbook()) {
				Sheet sheet = workbook.createSheet("Commits");
				sheet = createHeaders(workbook, sheet);
				createBody(tenantConfigList, sheet);
				
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		        Date now = new Date();
		        String excelFilePath = ""; // 출력할 Excel 파일 경로
		        excelFilePath = "tenantConfig_" + formatter.format(now) + ".xlsx";
		        // Excel 파일 저장
		        try (FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
		            workbook.write(fileOut);
		        }
		        System.out.println("Excel 파일이 생성되었습니다: " + excelFilePath);
		        System.out.println();
			}
		} finally {
			if (sqlMapSession != null) {
				session.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		
		
	}
	
	public String showMenu(Scanner scanner) {
		
		String mode = "";
		
		String[] modeCase = {"1"};
		boolean goNextStepFlag = false;
		do {
			System.out.println("이용할 메뉴를 선택하세요.");
			System.out.println("1. 테넌트 컨피그 리스트 엑셀파일 만들기");
			System.out.println("종료는 e");
			System.out.print("입력 > ");
			mode = scanner.nextLine().trim();
			
			goNextStepFlag = checkGoNextStep(mode, modeCase);
			
		} while (!goNextStepFlag);
		return mode;
	}
	
	public static boolean checkGoNextStep(String inputStr,String[] caseStr) {
		boolean goNextStepFlag = false;
		if (inputStr.equalsIgnoreCase("e")) {
			System.out.println("프로그램을 종료합니다.");
			System.exit(0);
		}
		
		for (String compareStr : caseStr) {
			if (compareStr.equals(inputStr.trim())) {
				goNextStepFlag = true;
				break;
			}
		}
		if (!goNextStepFlag) {
			System.out.println("입력값을 잘못 입력했습니다. 형식에 맞게 다시 입력해주세요.");
		}
		return goNextStepFlag;
	}
	
	public Sheet createHeaders(Workbook workbook, Sheet sheet) throws Exception {
		
		CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true); // 폰트 굵게 설정
        headerStyle.setFont(font);
        headerStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN1.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        Row headerRow = sheet.createRow(0);
        
        String[] headers = {"TENANT_ID", "PROPERTY_NAME", "PROPERTY_VALUE", "테넌트 컨피그 설명", "테넌트 컨피그 이름", "등록날짜", "컨피그 타입"};
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle); // 스타일 적용
        }
        
        sheet.setColumnWidth(0, 10 * 256);
        sheet.setColumnWidth(1, 40 * 256);
        sheet.setColumnWidth(2, 30 * 256);
        sheet.setColumnWidth(3, 100 * 256);
        sheet.setColumnWidth(4, 80 * 256);
        sheet.setColumnWidth(5, 20 * 256);
        sheet.setColumnWidth(6, 20 * 256);
        
        return sheet;
	}
	
	public void createBody(List<TenantConfigVO> tenantConfigList, Sheet sheet) {
		int rowCount = 1;
		for (TenantConfigVO tenantConfig : tenantConfigList) {
			Row row = sheet.createRow(rowCount++);
        	row.createCell(0).setCellValue(tenantConfig.getTenantId());
        	row.createCell(1).setCellValue(tenantConfig.getPropertyName());
        	row.createCell(2).setCellValue(tenantConfig.getPropertyValue());
        	row.createCell(3).setCellValue(tenantConfig.getDescription());
        	row.createCell(4).setCellValue(tenantConfig.getConfigName());
        	row.createCell(5).setCellValue(tenantConfig.getRegdate());
        	row.createCell(6).setCellValue(tenantConfig.getConfigType());
		}
	}
}
