package chuangbang.entity;

import java.util.Date;

import cn.bmob.v3.BmobObject;

public class Comment extends BmobObject{
	private User author;
	private Date creatAt;
	private String text;
	private Comment reply;
	
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public Date getCreatAt() {
		return creatAt;
	}
	public void setCreatAt(Date creatAt) {
		this.creatAt = creatAt;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public Comment getReply() {
		return reply;
	}
	public void setReply(Comment reply) {
		this.reply = reply;
	}
	@Override
	public String toString() {
		return "Comment [author=" + author + ", creatAt=" + creatAt + ", text="
				+ text + ", reply=" + reply + "]";
	}
	
	
	
}
