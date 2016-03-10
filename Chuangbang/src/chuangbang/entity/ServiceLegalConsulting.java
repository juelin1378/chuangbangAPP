package chuangbang.entity;

import cn.bmob.v3.BmobObject;

/**
 * 法律咨询  
 * @author Administrator
 *
 */
public class ServiceLegalConsulting extends BmobObject{

	private String contactName;
	private String contactPhone;
	private String companyName;
	private String consultingType;
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
	public String getConsultingType() {
		return consultingType;
	}
	public void setConsultingType(String consultingType) {
		this.consultingType = consultingType;
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
		return "ServiceLegalConsulting [contactName=" + contactName
				+ ", contactPhone=" + contactPhone + ", companyName="
				+ companyName + ", consultingType=" + consultingType
				+ ", remarks=" + remarks + ", applicant=" + applicant
				+ ", state=" + state + "]";
	}

	



}
