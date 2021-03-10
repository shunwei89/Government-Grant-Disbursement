package com.assignment.GovernmentGrantDisbursement.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="Household")
public class Household {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="HOUSEHOLD_ID")
	private Integer householdId;
	
	@Pattern(regexp="((?i)landed(?-i)|(?i)Condominium(?-i)|(?i)HDB(?-i))")
	@Column(name="HOUSEHOLD_TYPE")
	private String householdType;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="household")
	@Cascade(value= {org.hibernate.annotations.CascadeType.SAVE_UPDATE,CascadeType.DELETE})
	@JsonManagedReference
	private List<Member> members = new ArrayList<Member>();

	public Household(int householdID, String householdType) {
		this.householdId = householdID;
		this.householdType = householdType;
	}
	
	public Household() {
		
	}
	
	public Household(@JsonProperty("householdType") String householdType) {
		this.householdType = householdType;
	}
	
	public Household(int householdID) {
		this.householdId = householdID;
	}
	
	public Integer getHouseholdId() {
		return householdId;
	}

	public void setHouseholdId(Integer householdId) {
		this.householdId = householdId;
	}

	public String getHouseholdType() {
		return householdType;
	}

	public void setHouseholdType(String householdType) {
		this.householdType = householdType;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}
}
