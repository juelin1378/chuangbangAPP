package chuangbang.entity;

import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

public class Status extends BmobObject{

	private String text;
	private User author;
	private String uAvater;
	private Integer commentCount;//评论数
	private Integer attitudeCount;//表态数
	private BmobRelation commentList;//评论列表
	private BmobRelation attitudeList;//点赞用户列表
	private String createdAt;
	private List<String> statusImage;




	
	public String getuAvater() {
		return uAvater;
	}
	public void setuAvater(String uAvater) {
		this.uAvater = uAvater;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	

	public Integer getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	public Integer getAttitudeCount() {
		return attitudeCount;
	}
	public void setAttitudeCount(Integer attitudeCount) {
		this.attitudeCount = attitudeCount;
	}
	public BmobRelation getCommentList() {
		return commentList;
	}
	public void setCommentList(BmobRelation commentList) {
		this.commentList = commentList;
	}
	public BmobRelation getAttitudeList() {
		return attitudeList;
	}
	public void setAttitudeList(BmobRelation attitudeList) {
		this.attitudeList = attitudeList;
	}
	
	
	
	public List<String> getStatusImage() {
		return statusImage;
	}
	public void setStatusImage(List<String> statusImage) {
		this.statusImage = statusImage;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createAt) {
		this.createdAt = createAt;
	}
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "动态="+getText()+"日期="+getCreatedAt()+"用户头像="+getuAvater()+"图片集合="+getStatusImage();
	}



}
