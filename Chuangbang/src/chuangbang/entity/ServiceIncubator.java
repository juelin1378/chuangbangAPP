package chuangbang.entity;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
/**
 * 进驻孵化器
 * @author Administrator
 *
 */
public class ServiceIncubator extends BmobObject{

	private String founderName;//创始人姓名
	private BmobDate founderBirth;//出生日期
	private String contactPhone;//手机号码
	private String currentLocation;//现所在地
	private String gradualtedSchoolName;//毕业院校
	private BmobDate gradualtedTime;//毕业时间
	private String companyName;//公司名/项目名
	private Integer termNumberCount;//团队人数
	private BmobDate settledExpectTime;//期望入驻时间
	private String remarks;//备注
	private User applicant;//申请人
	private Integer state;//

	
	
	
	
	
	
	
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
	public String getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}
	public String getGradualtedSchoolName() {
		return gradualtedSchoolName;
	}
	public void setGradualtedSchoolName(String gradualtedSchoolName) {
		this.gradualtedSchoolName = gradualtedSchoolName;
	}

	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Integer getTermNumberCount() {
		return termNumberCount;
	}
	public void setTermNumberCount(Integer termNumberCount) {
		this.termNumberCount = termNumberCount;
	}
	
	public BmobDate getGradualtedTime() {
		return gradualtedTime;
	}
	public void setGradualtedTime(BmobDate gradualtedTime) {
		this.gradualtedTime = gradualtedTime;
	}
	public BmobDate getSettledExpectTime() {
		return settledExpectTime;
	}
	public void setSettledExpectTime(BmobDate settledExpectTime) {
		this.settledExpectTime = settledExpectTime;
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
	
	

}
