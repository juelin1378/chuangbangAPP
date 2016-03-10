package chuangbang.entity;

import java.io.File;
import java.util.Date;

import android.R.integer;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class User extends BmobUser{
	

	/**
	 * 
	 */

	private BmobFile avatar;
	private String nickName;//用户昵称
	private String workingCompany;//公司名
	private String workingPosition;//职位
	private Integer sex;
	private Integer memberType;//会员类型
	private String description;//用户描述
	private BmobRelation favoriteProjectList;
	private BmobRelation meetingList;
	private BmobRelation demandList;
	private Integer favoriteProjectCount;
	private Integer meetingPerssionCount;
	private Integer meetingPendingCount;
	private Integer demandCount;

	
	
	
	public BmobRelation getFavoriteProjectList() {
		return favoriteProjectList;
	}
	public void setFavoriteProjectList(BmobRelation favoriteProjectList) {
		this.favoriteProjectList = favoriteProjectList;
	}
	public BmobFile getAvatar() {
		return avatar;
	}
	public void setAvatar(BmobFile avatar) {
		this.avatar = avatar;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public String getWorkingPosition() {
		return workingPosition;
	}
	public void setWorkingPosition(String workingPosition) {
		this.workingPosition = workingPosition;
	}
	public String getWorkingCompany() {
		return workingCompany;
	}
	public void setWorkingCompany(String workingCompany) {
		this.workingCompany = workingCompany;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public BmobRelation getMeetingList() {
		return meetingList;
	}
	public void setMeetingList(BmobRelation meetingList) {
		this.meetingList = meetingList;
	}
	public BmobRelation getDemandList() {
		return demandList;
	}
	public void setDemandList(BmobRelation demandList) {
		this.demandList = demandList;
	}
	public Integer getFavoriteProjectCount() {
		return favoriteProjectCount;
	}
	public void setFavoriteProjectCount(Integer favoriteProjectCount) {
		this.favoriteProjectCount = favoriteProjectCount;
	}
	public Integer getMeetingPerssionCount() {
		return meetingPerssionCount;
	}
	public void setMeetingPerssionCount(Integer meetingPerssionCount) {
		this.meetingPerssionCount = meetingPerssionCount;
	}
	public Integer getMeetingPendingCount() {
		return meetingPendingCount;
	}
	public void setMeetingPendingCount(Integer meetingPendingCount) {
		this.meetingPendingCount = meetingPendingCount;
	}
	public Integer getDemandCount() {
		return demandCount;
	}
	public void setDemandCount(Integer demandCount) {
		this.demandCount = demandCount;
	}
	
	
	
	
	
	
	
}
