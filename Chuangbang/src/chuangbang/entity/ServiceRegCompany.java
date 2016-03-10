package chuangbang.entity;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
/**
 * 注册公司
 * @author Administrator
 *
 */
public class ServiceRegCompany extends BmobObject{

	private String founderName;
	private BmobDate founderBirth;
	private String contactPhone;
	private String companyNamePreferred;//首选公司名称
	private String companyNameReserves;//备用公司名称
	private String companyNature;//经营性质
	private String companyBusinessScope;//经营范围
	private Integer companyShareholdersCount;//股东人数
	private User applicant;
	private Integer state;
	public String getFounderName() {
		return founderName;
	}
	public void setFounderName(String founderName) {
		this.founderName = founderName;
	}
	
	public BmobDate getFounderBirth() {
		return founderBirth;
	}
	public void setFounderBirth(BmobDate founderBirth) {
		this.founderBirth = founderBirth;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getCompanyNamePreferred() {
		return companyNamePreferred;
	}
	public void setCompanyNamePreferred(String companyNamePreferred) {
		this.companyNamePreferred = companyNamePreferred;
	}
	public String getCompanyNameReserves() {
		return companyNameReserves;
	}
	public void setCompanyNameReserves(String companyNameReserves) {
		this.companyNameReserves = companyNameReserves;
	}
	public String getCompanyNature() {
		return companyNature;
	}
	public void setCompanyNature(String companyNature) {
		this.companyNature = companyNature;
	}
	public String getCompanyBusinessScope() {
		return companyBusinessScope;
	}
	public void setCompanyBusinessScope(String companyBusinessScope) {
		this.companyBusinessScope = companyBusinessScope;
	}
	public Integer getCompanyShareholdersCount() {
		return companyShareholdersCount;
	}
	public void setCompanyShareholdersCount(Integer companyShareholdersCount) {
		this.companyShareholdersCount = companyShareholdersCount;
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
		return "ServiceRegCompany [founderName=" + founderName
				+ ", founderBirth=" + founderBirth + ", contactPhone="
				+ contactPhone + ", companyNamePreferred="
				+ companyNamePreferred + ", companyNameReserves="
				+ companyNameReserves + ", companyNature=" + companyNature
				+ ", companyBusinessScope=" + companyBusinessScope
				+ ", companyShareholdersCount=" + companyShareholdersCount
				+ ", applicant=" + applicant + ", state=" + state + "]";
	}




}
