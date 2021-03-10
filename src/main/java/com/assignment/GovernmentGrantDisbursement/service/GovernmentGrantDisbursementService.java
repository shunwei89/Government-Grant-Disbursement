
package com.assignment.GovernmentGrantDisbursement.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.GovernmentGrantDisbursement.dao.GovernmentGrantDisbursementDAO;
import com.assignment.GovernmentGrantDisbursement.model.Household;
import com.assignment.GovernmentGrantDisbursement.model.Member;
import com.assignment.GovernmentGrantDisbursement.model.Response;

enum HouseholdType {LANDED, CONDOMINIUM, HDB}
enum OccupationType {UNEMPLOYED, STUDENT, EMPLOYED}

@Service
@Transactional
public class GovernmentGrantDisbursementService {
	@Autowired
	GovernmentGrantDisbursementDAO governmentGrantDisbursementDAO;
	
	public Response createHousehold(Household household) {
		if(household.getHouseholdType() != null && 
				(household.getHouseholdType().equalsIgnoreCase(HouseholdType.LANDED.toString()) || 
				 household.getHouseholdType().equalsIgnoreCase(HouseholdType.CONDOMINIUM.toString()) ||
				 household.getHouseholdType().equalsIgnoreCase(HouseholdType.HDB.toString()))) {
			return new Response(governmentGrantDisbursementDAO.createHousehold(household));
		} else {
			return new Response("Household type must be either landed, condominium or HDB");
		}
	}
	
	public Object addMember(Member member) {
		if(member.getOccupationType() != null && 
				(member.getOccupationType().equalsIgnoreCase(OccupationType.UNEMPLOYED.toString()) || 
				 member.getOccupationType().equalsIgnoreCase(OccupationType.STUDENT.toString()) ||
				 member.getOccupationType().equalsIgnoreCase(OccupationType.EMPLOYED.toString()))) {
			Member household = governmentGrantDisbursementDAO.addMember(member);
			
			return household;
		} else {
			return new Response("Occupation type must be either unemployed, student or employed");
		}
	}
	
	public Optional<Household> findHouseholdByID(int householdID) {
		return governmentGrantDisbursementDAO.findHouseholdByID(householdID);
	}
	
	public void deleteHouseholdByHouseholdId(int householdID) {
		governmentGrantDisbursementDAO.deleteHouseholdByHouseholdId(householdID);
	}
	
	public void deleteMemberByMemberId(int memberID) {
		governmentGrantDisbursementDAO.deleteMemberByMemberId(memberID);
	}
	
	public List<Household> findAllHousehold() {
		return governmentGrantDisbursementDAO.findAllHousehold();
	}
	
	public List<Household> findStudentEncouragementBonus(int age, int annualIncomeThreshold) {
		List<Integer> householdID = getHouseholdWithMemberBelowDOB(age);
		List<Household> households = governmentGrantDisbursementDAO.findByHouseholdId(householdID);
		
		return getHouseholdWithAnnualIncomeBelow(annualIncomeThreshold, households);
	}
	
	public List<Household> findFamilyTogethernessScheme(int age) {
		Date date = getDate(age);
		
		List<Member> children = governmentGrantDisbursementDAO.findByDobAfterAndSpouseNull(date);
		List<Member> parents = governmentGrantDisbursementDAO.findBySpouseNotNull();
		Set<Integer> childrenHouseholdID = getHouseholdIDSet(children);
		
		List<Integer> householdID = new ArrayList<Integer>();
		for(Member member : parents) {
			if(childrenHouseholdID.contains(member.getHousehold().getHouseholdId())) {
				householdID.add(member.getHousehold().getHouseholdId());
			}
		}
		
		return governmentGrantDisbursementDAO.findByHouseholdId(householdID);
	}
	
	public List<Household> findElderBonus(int age) {
		Date date = getDate(age);
		
		List<Member> elder = governmentGrantDisbursementDAO.findByDobBefore(date);
		List<Integer> householdID = getHouseholdIDList(elder);
		
		return governmentGrantDisbursementDAO.findByHouseholdId(householdID);
	}
	
	public List<Household> findBabySunshineGrant(int age) {
		Date date = getDate(age);
		
		List<Member> babies = governmentGrantDisbursementDAO.findByDobAfter(date);
		List<Integer> householdID = getHouseholdIDList(babies);

		return governmentGrantDisbursementDAO.findByHouseholdId(householdID);
	}
	
	public List<Household> findYOLOGSTGrant(int annualIncomeThreshold) {
		List<Household> households = governmentGrantDisbursementDAO.findAllHousehold();
		List<Household> output = new ArrayList<Household>();
		
		for(Household household : households) {
			List<Member> members = household.getMembers();
			int totalHouseholdIncome = 0;
			
			for(Member member : members) {
				totalHouseholdIncome += member.getAnnualIncome();
			}
			
			if(totalHouseholdIncome < annualIncomeThreshold) {
				output.add(household);
			}
		}
		
		return null;
	}
	
	private List<Integer> getHouseholdWithMemberBelowDOB(int age) {
		Date date = getDate(age);
		
		List<Member> members = governmentGrantDisbursementDAO.findByDobAfter(date);
		List<Integer> householdID = new ArrayList<Integer>();
		
		for(Member member : members) {
			householdID.add(member.getHousehold().getHouseholdId());
		}
		
		return householdID;
	}
	
	private List<Household> getHouseholdWithAnnualIncomeBelow(int annualIncomeThreshold, List<Household> households) {
		List<Household> output = new ArrayList<Household>();
		
		for(Household household : households) {
			List<Member> members = household.getMembers();
			int totalAnnualIncome = 0;
			
			for(Member member : members) {
				totalAnnualIncome += member.getAnnualIncome();
			}
			
			if(totalAnnualIncome < annualIncomeThreshold) output.add(household);
		}
		
		return output;
	}
	
	private Set<Integer> getHouseholdIDSet(List<Member> members) {
		Set<Integer> householdID = new HashSet<Integer>();
		
		for(Member member : members) {
			householdID.add(member.getHousehold().getHouseholdId());
		}
		
		return householdID;
	}
	
	private List<Integer> getHouseholdIDList(List<Member> members) {
		List<Integer> householdID = new ArrayList<Integer>();
		
		for(Member member : members) {
			householdID.add(member.getHousehold().getHouseholdId());
		}
		
		return householdID;
	}
	
	private Date getDate(int year) {
		Date currentDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		c.add(Calendar.YEAR, (year * -1));
		Date date = c.getTime();
		
		return date;
	}
}
