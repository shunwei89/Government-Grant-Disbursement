package com.assignment.GovernmentGrantDisbursement.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.assignment.GovernmentGrantDisbursement.model.Household;
import com.assignment.GovernmentGrantDisbursement.model.Member;

@Repository
public class GovernmentGrantDisbursementDAO {
	@Autowired
	HouseholdRepository householdRepository;

	@Autowired
	EntityManager entityManager;

	@Autowired
	MemberRepository memberRepository;

	public Household createHousehold(Household household) {
		householdRepository.save(household);
		return household;
	}

	public Member addMember(Member member) {
		return memberRepository.save(member);
	}

	public List<Member> findByMemberName(String memberName) {
		return memberRepository.findByMemberName(memberName);
	}

	public List<Household> findAllHousehold() {
		List<Household> households = new ArrayList<Household>();
		householdRepository.findAll().forEach(households::add);
		return households;
	}

	public Optional<Household> findHouseholdByID(int householdID) {
		return householdRepository.findById(householdID);
	}
	
	public void deleteHouseholdByHouseholdId(int householdID) {
		householdRepository.deleteByHouseholdId(householdID);
	}
	
	public void deleteMemberByMemberId(int memberId) {
		memberRepository.deleteMemberByMemberId(memberId);
	}

	public List<Household> findByHouseholdId(List<Integer> householdId) {
		return householdRepository.findByHouseholdIdIn(householdId);
	}

	public List<Member> findByDobAfter(Date date) {
		return memberRepository.findByDobAfter(date);
	}
	
	public List<Member> findByDobBefore(Date date) {
		return memberRepository.findByDobBefore(date);
	}
	
	public List<Member> findBySpouseNotNull() {
		return memberRepository.findBySpouseNotNull();
	}
	
	public List<Member> findByDobAfterAndSpouseNull(Date date) {
		return memberRepository.findByDobAfterAndSpouseNull(date);
	}
}
