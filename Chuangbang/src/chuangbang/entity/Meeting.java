package chuangbang.entity;

import java.util.Date;

import cn.bmob.v3.BmobObject;

public class Meeting extends BmobObject{

	private User applyUser;
	private User inviteUser;
	private String applyText;
	private Project project;
	private Integer state;
	private String reply;
	private String refuseResource;//拒绝理由
	private String contactName;
	private String contactPhone;
	private String meetingPosition;
	private Date meetingTime;


	public User getApplyUser() {
		return applyUser;
	}
	public void setApplyUser(User applyUser) {
		this.applyUser = applyUser;
	}
	public User getInviteUser() {
		return inviteUser;
	}
	public void setInviteUser(User inviteUser) {
		this.inviteUser = inviteUser;
	}
	public String getApplyText() {
		return applyText;
	}
	public void setApplyText(String applyText) {
		this.applyText = applyText;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public String getRefuseResource() {
		return refuseResource;
	}
	public void setRefuseResource(String refuseResource) {
		this.refuseResource = refuseResource;
	}
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
	public String getMeetingPosition() {
		return meetingPosition;
	}
	public void setMeetingPosition(String meetingPosition) {
		this.meetingPosition = meetingPosition;
	}
	public Date getMeetingTime() {
		return meetingTime;
	}
	public void setMeetingTime(Date meetingTime) {
		this.meetingTime = meetingTime;
	}
	@Override
	public String toString() {
		return "Meeting [applyUser=" + applyUser
				+ ", inviteUser=" + inviteUser + ", applyText=" + applyText
				+ ", project=" + project + ", state=" + state + ", reply="
				+ reply + ", refuseResource=" + refuseResource
				+ ", contactName=" + contactName + ", contactPhone="
				+ contactPhone + ", meetingPosition=" + meetingPosition
				+ ", meetingTime=" + meetingTime + "]";
	}
	
}
