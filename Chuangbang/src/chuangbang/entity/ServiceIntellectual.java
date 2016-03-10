package chuangbang.entity;

import cn.bmob.v3.BmobObject;

/**
 * 知识产权 
 * @author Administrator
 *
 */
public class ServiceIntellectual extends BmobObject{
	private String contactName;
	private String contactPhone;
	private String companyName;
	private Integer handleType;
	private String remarks;
	private User applicant;
	private Integer state;
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Integer getHandleType() {
		return handleType;
	}
	public void setHandleType(Integer handleType) {
		this.handleType = handleType;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public User getApplicant() {
		return applicant;
	}
	public void setApplicant(User applicant) {
		this.applicant = applicant;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "ServiceIntellectual [contactName=" + contactName
				+ ", contactPhone=" + contactPhone + ", companyName="
				+ companyName + ", handleType=" + handleType + ", remarks="
				+ remarks + ", applicant=" + applicant + ", state=" + state
				+ "]";
	}




}
