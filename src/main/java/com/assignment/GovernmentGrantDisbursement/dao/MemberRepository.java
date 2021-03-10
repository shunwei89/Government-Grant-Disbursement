package com.assignment.GovernmentGrantDisbursement.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.assignment.GovernmentGrantDisbursement.model.Member;

public interface MemberRepository extends CrudRepository<Member, Integer> {
	List<Member> findByMemberName(String memberName);
	
	List<Member> findByDobAfter(Date date);
	
	List<Member> findByDobBefore(Date date);
	
	List<Member> findBySpouseNotNull();
	
	List<Member> findByDobAfterAndSpouseNull(Date date);
	
	void deleteMemberByMemberId(int memberId);
}
