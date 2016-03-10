package chuangbang.entity;

import cn.bmob.v3.BmobObject;

public class PartnerDemand extends BmobObject{
	private User author;
	private String text;
	private String title;
	private String contactName;
	private String contactPhone;
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	@Override
	public String toString() {
		return "PartnerDemand [author=" + author + ", text=" + text
				+ ", title=" + title + ", contactName=" + contactName
				+ ", contactPhone=" + contactPhone + "]";
	}
	
	
	
}
