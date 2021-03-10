package com.assignment.GovernmentGrantDisbursement.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.assignment.GovernmentGrantDisbursement.model.Household;

public interface HouseholdRepository extends CrudRepository<Household, Integer> {
	List<Household> findByHouseholdIdIn(List<Integer> householdId);
	
	void deleteByHouseholdId(int householdID);
}
