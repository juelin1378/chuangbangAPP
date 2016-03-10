package chuangbang.entity;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobRole;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

import android.os.Parcel;
import android.os.Parcelable;

public class Project extends BmobObject{
	private User owner;//项目发布者
	private String name;//项目名称
	private String domain;//项目领域标签
	private String description;//项目描述
	private String painPointer;//行业痛点
	private String solution;//解决方案
	private String competitors;//竞争产品
	private String advantage;//竞争优势
	private String businessModel;//商业模式
	private Integer financingState;//融资阶段
	private Integer financingAmount;//融资金额
	private Integer transferShare;//出让股份
	private String state;//项目状态
	private BmobFile logo;
	private BmobRelation teamList;//项目成员列表
	private BmobRelation commentList;//评论列表
	private BmobRelation favoriteUserList;//收藏列表
	private Integer favoriteUserCount;//收藏人数
	private Integer commentCount;//评论人数
	private Integer proTermNumCount;//团队人数
	
	
	

	

	
	
	
	
	
	
	
	public Integer getProTermNumCount() {
		return proTermNumCount;
	}
	public void setProTermNumCount(Integer proTermNumCount) {
		this.proTermNumCount = proTermNumCount;
	}
	public Integer getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	public Integer getFavoriteUserCount() {
		return favoriteUserCount;
	}
	public void setFavoriteUserCount(Integer favoriteUserCount) {
		this.favoriteUserCount = favoriteUserCount;
	}
	public BmobFile getLogo() {
		return logo;
	}
	public void setLogo(BmobFile logo) {
		this.logo = logo;
	}
	
	
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPainPointer() {
		return painPointer;
	}
	public void setPainPointer(String painPointer) {
		this.painPointer = painPointer;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public String getCompetitors() {
		return competitors;
	}
	public void setCompetitors(String competitors) {
		this.competitors = competitors;
	}
	public String getAdvantage() {
		return advantage;
	}
	public void setAdvantage(String advantage) {
		this.advantage = advantage;
	}
	public String getBusinessModel() {
		return businessModel;
	}
	public void setBusinessModel(String businessModel) {
		this.businessModel = businessModel;
	}
	public Integer getFinancingState() {
		return financingState;
	}
	public void setFinancingState(Integer financingState) {
		this.financingState = financingState;
	}
	
	public Integer getFinancingAmount() {
		return financingAmount;
	}
	public void setFinancingAmount(Integer financingAmount) {
		this.financingAmount = financingAmount;
	}
	
	public Integer getTransferShare() {
		return transferShare;
	}
	public void setTransferShare(Integer transferShare) {
		this.transferShare = transferShare;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	
	
	
	public BmobRelation getTeamList() {
		return teamList;
	}
	public void setTeamList(BmobRelation teamList) {
		this.teamList = teamList;
	}
	public BmobRelation getCommentList() {
		return commentList;
	}
	public void setCommentList(BmobRelation commentList) {
		this.commentList = commentList;
	}
	public BmobRelation getFavoriteUserList() {
		return favoriteUserList;
	}
	public void setFavoriteUserList(BmobRelation favoriteUserList) {
		this.favoriteUserList = favoriteUserList;
	}
//	@Override
//	public void writeToParcel(Parcel parcel, int flags) {
//	  
//        parcel.writeString(description); 
//        parcel.writeString(name);  
//
//	}
//	@Override
//	public int describeContents() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//	
//	public static final Parcelable.Creator<Project> CREATOR = new Creator<Project>() {  
//        public Project createFromParcel(Parcel source) {  
//        	Project pro = new Project();  
//            
//            pro.description = source.readString();  
//            pro.name = source.readString(); 
//            
//            return pro;  
//        }
//
//		@Override
//		public Project[] newArray(int arg0) {
//			// TODO Auto-generated method stub
//			return new Project[arg0];
//		}  
//      
//    };

	@Override
	public String toString() {
		return "Project [owner=" + getOwner() + ", name=" + getName() + ", state="
				+ state + ", logo=" + logo + ", domain=" + domain
				+ ", description=" + description + ", painPointer="
				+ painPointer + ", solution=" + solution + ", competitors="
				+ competitors + ", advantage=" + advantage + ", businessModel="
				+ businessModel + ", teamList=" + teamList
				+ ", financingState=" + financingState + ", financingAmount="
				+ financingAmount + ", transferShare=" + transferShare
				+ ", commentList=" + commentList + ", favoriteUserList="
				+ favoriteUserList + "评论数"+getCommentCount()+"收藏数"+getFavoriteUserCount()+"]";
	}


	
}
