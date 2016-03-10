package chuangbang.entity;

import cn.bmob.v3.BmobObject;

/**
 * 专业套餐 
 * @author Administrator
 *
 */
public class ServiceProfesssional extends BmobObject{
	private User applicant;
	private Integer state;
	private Project project;
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
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	@Override
	public String toString() {
		return "ServiceProfesssional [applicant=" + applicant + ", state="
				+ state + ", project=" + project + "]";
	}


}
