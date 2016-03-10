package chuangbang.entity;

import java.io.File;

public class ProjectTeamNumber {

	private Project affiliation;
	private File avatar;
	private String name;
	private String position;
	private String detail;

	public Project getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(Project affiliation) {
		this.affiliation = affiliation;
	}

	public File getAvatar() {
		return avatar;
	}

	public void setAvatar(File avatar) {
		this.avatar = avatar;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "ProjectTeamNumber [affiliation=" + affiliation + ", avatar="
				+ avatar + ", name=" + name + ", position=" + position
				+ ", detail=" + detail + "]";
	}

}
