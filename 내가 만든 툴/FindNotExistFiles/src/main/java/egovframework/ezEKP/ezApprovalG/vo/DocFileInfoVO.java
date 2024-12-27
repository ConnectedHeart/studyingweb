package egovframework.ezEKP.ezApprovalG.vo;

import java.util.Date;

public class DocFileInfoVO {
	private String type;
	private String docid;
	private String filename;
	private String filepath;
	private String typesn;
	private String viewsn;
	private String attachfilesn;
	private Date enddate;
	private Date startdate;
	private String doctype;
	private String functiontype;
	private String orgdocid;
	private String docno;
	private String writername;
	private String writerdeptname;
	private String attachsn;
	
	
	public String getAttachsn() {
		return attachsn;
	}
	public void setAttachsn(String attachsn) {
		this.attachsn = attachsn;
	}
	public String getDocno() {
		return docno;
	}
	public void setDocno(String docno) {
		this.docno = docno;
	}
	public String getWritername() {
		return writername;
	}
	public void setWritername(String writername) {
		this.writername = writername;
	}
	public String getWriterdeptname() {
		return writerdeptname;
	}
	public void setWriterdeptname(String writerdeptname) {
		this.writerdeptname = writerdeptname;
	}
	public String getDoctype() {
		return doctype;
	}
	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}
	public String getFunctiontype() {
		return functiontype;
	}
	public void setFunctiontype(String functiontype) {
		this.functiontype = functiontype;
	}
	public String getOrgdocid() {
		return orgdocid;
	}
	public void setOrgdocid(String orgdocid) {
		this.orgdocid = orgdocid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDocid() {
		return docid;
	}
	public void setDocid(String docid) {
		this.docid = docid;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getTypesn() {
		return typesn;
	}
	public void setTypesn(String typesn) {
		this.typesn = typesn;
	}
	public String getViewsn() {
		return viewsn;
	}
	public void setViewsn(String viewsn) {
		this.viewsn = viewsn;
	}
	public String getAttachfilesn() {
		return attachfilesn;
	}
	public void setAttachfilesn(String attachfilesn) {
		this.attachfilesn = attachfilesn;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
}
