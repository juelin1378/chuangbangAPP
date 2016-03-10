package chuangbang.entity;

import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * 申请开发
 * @author Administrator
 *
 */
public class ServiceDevelop extends BmobObject{

	private User applicant;
	private Project project;
	private Integer developPlatform;
	private String domain;
	private String remarks;//备注
	private Integer state;
	public User getApplicant() {
		return applicant;
	}
	public void setApplicant(User applicant) {
		this.applicant = applicant;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public Integer getDevelopPlatform() {
		return developPlatform;
	}
	public void setDevelopPlatform(Integer developPlatform) {
		this.developPlatform = developPlatform;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "ServiceDevelop [applicant=" + applicant + ", project=" + project
				+ ", developPlatform=" + developPlatform + ", domain=" + domain
				+ ", remarks=" + remarks + ", state=" + state + "]";
	}
	
}
