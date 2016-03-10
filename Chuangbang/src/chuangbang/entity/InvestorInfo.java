package chuangbang.entity;

import java.io.File;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class InvestorInfo extends BmobObject{
	private User owner;//投资人所对应的用户信息
	private BmobFile businessCardUrl;
	private Integer verifiedState;//认证状态 1，申请中  2、团队受理中 3、已通过4、失败
	private String verifiedReason;//认证签名，由团队写
	private String investmentDomain;//投资领域标签
	private String investmentStage;//投资阶段
	private String introduction;//个人简介
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public BmobFile getBusinessCardUrl() {
		return businessCardUrl;
	}
	public void setBusinessCardUrl(BmobFile businessCardUrl) {
		this.businessCardUrl = businessCardUrl;
	}
	public Integer getVerifiedState() {
		return verifiedState;
	}
	public void setVerifiedState(Integer verifiedState) {
		this.verifiedState = verifiedState;
	}
	public String getVerifiedReason() {
		return verifiedReason;
	}
	public void setVerifiedReason(String verifiedReason) {
		this.verifiedReason = verifiedReason;
	}
	public String getInvestmentDomain() {
		return investmentDomain;
	}
	public void setInvestmentDomain(String investmentDomain) {
		this.investmentDomain = investmentDomain;
	}
	public String getInvestmentStage() {
		return investmentStage;
	}
	public void setInvestmentStage(String investmentStage) {
		this.investmentStage = investmentStage;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	@Override
	public String toString() {
		return "InvestorInfo [owner=" + owner + ", businessCardUrl="
				+ businessCardUrl + ", verifiedState=" + verifiedState
				+ ", verifiedReason=" + verifiedReason + ", investmentDomain="
				+ investmentDomain + ", investmentStage=" + investmentStage
				+ ", introduction=" + introduction + "]";
	}
	
	
	
	
	
}
