package chuangbang.entity;

import cn.bmob.v3.BmobObject;
/**
 * 会计做账
 * @author Administrator
 *
 */
public class ServiceAccounting extends BmobObject{

	private String contactName;
	private String contactPhone;
	private String companyName;
	private String companyNature;
	private String businessScope;
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
	public String getCompanyNature() {
		return companyNature;
	}
	public void setCompanyNature(String companyNature) {
		this.companyNature = companyNature;
	}
	public String getBusinessScope() {
		return businessScope;
	}
	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
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
		return "ServiceAccounting [contactName=" + contactName
				+ ", contactPhone=" + contactPhone + ", companyName="
				+ companyName + ", companyNature=" + companyNature
				+ ", businessScope=" + businessScope + ", applicant="
				+ applicant + ", state=" + state + "]";
	}




}
