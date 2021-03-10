package com.assignment.GovernmentGrantDisbursement.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="MEMBER")
public class Member {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="MEMBER_ID")
	private Integer memberId;

	@ManyToOne()
	@JoinColumn(name="HOUSEHOLD_ID")
	@JsonBackReference
	private Household household;
	
	@Column(name="MEMBER_NAME")
	private String memberName;
	
	@Column(name="GENDER")
	private String gender;
	
	@Column(name="MARITAL_STATUS")
	private String maritalStatus;
	
	@Column(name="SPOUSE")
	private String spouse;
	
	@Pattern(regexp="((?i)unemployed(?-i)|(?i)student(?-i)|(?i)employed(?-i))")
	@Column(name="OCCUPATION_TYPE")
	private String occupationType;
	
	@Column(name="ANNUAL_INCOME")
	private Integer annualIncome;
	
	@Column(name="DOB")
	private Date dob;
	
	public Member() {
		
	}
	
	public Member(@JsonProperty("memberName") String memberName, 
				  @JsonProperty("gender") String gender, 
				  @JsonProperty("maritalStatus") String maritalStatus,
				  @JsonProperty("spouse") String spouse,
				  @JsonProperty("occupationType") String occupationType,
				  @JsonProperty("annualIncome") int annualIncome,
				  @JsonProperty("dob") Date dob,
				  @JsonProperty("householdID") int householdID) {
		this.memberName = memberName;
		this.gender = gender;
		this.maritalStatus = maritalStatus;
		this.spouse = spouse;
		this.occupationType = occupationType;
		this.annualIncome = annualIncome;
		this.dob = dob;
		this.household = new Household(householdID);
		
	}
	
	public Household getHousehold() {
		return household;
	}

	public void setHousehold(Household household) {
		this.household = household;
	}

	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getSpouse() {
		return spouse;
	}
	public void setSpouse(String spouse) {
		this.spouse = spouse;
	}
	public String getOccupationType() {
		return occupationType;
	}
	public void setOccupationType(String occupationType) {
		this.occupationType = occupationType;
	}
	public Integer getAnnualIncome() {
		return annualIncome;
	}
	public void setAnnualIncome(Integer annualIncome) {
		this.annualIncome = annualIncome;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
}
