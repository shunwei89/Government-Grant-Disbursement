package com.assignment.GovernmentGrantDisbursement.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.GovernmentGrantDisbursement.model.Household;
import com.assignment.GovernmentGrantDisbursement.model.Member;
import com.assignment.GovernmentGrantDisbursement.model.Response;
import com.assignment.GovernmentGrantDisbursement.service.GovernmentGrantDisbursementService;

@RestController
@RequestMapping(path="/api")
public class GovernmentGrantDisbursementController {
	@Autowired
	GovernmentGrantDisbursementService governmentGrantDisbursementService; 
	
	@PostMapping(path="/household")
	public @ResponseBody Response createHousehold (@RequestBody Household household) {
		return governmentGrantDisbursementService.createHousehold(household);
	}
	
	@GetMapping(path="/household")
	public @ResponseBody List<Household> findAllHousehold () {
		return governmentGrantDisbursementService.findAllHousehold();
	}
	
	@GetMapping(path="/household/{householdID}")
	public @ResponseBody Optional<Household> findHouseholdByID (@PathVariable("householdID") String householdIDStr) {
		try {
			int householdID = Integer.parseInt(householdIDStr);
		
			return governmentGrantDisbursementService.findHouseholdByID(householdID);
		} catch(NumberFormatException e) {
			return null;
		}
	}
	
	@DeleteMapping(path="/household/{householdID}")
	public void deleteHouseholdByID (@PathVariable("householdID") String householdIDStr) {
		try {
			int householdID = Integer.parseInt(householdIDStr);
		
			governmentGrantDisbursementService.deleteHouseholdByHouseholdId(householdID);
		} catch(NumberFormatException e) {
			System.out.println("Error");
		}
	}
	
	@PostMapping(path="/member")
	public @ResponseBody Object addMember (@RequestBody Member member) {
		Object response = governmentGrantDisbursementService.addMember(member);
		
		if(response instanceof Member) {
			Member responseMember = (Member) response;
			return governmentGrantDisbursementService.findHouseholdByID(responseMember.getHousehold().getHouseholdId());
		}
		
		return response;
	}
	
	@DeleteMapping(path="/member/{memberID}")
	public void deleteMemberByID (@PathVariable("memberID") String memberIDStr) {
		try {
			int memberID = Integer.parseInt(memberIDStr);
		
			governmentGrantDisbursementService.deleteMemberByMemberId(memberID);
		} catch(NumberFormatException e) {
			System.out.println("Error");
		}
	}
	
	@GetMapping(path="/grantDisbursement")
	public @ResponseBody List<Household> findGrantDisbursement (@RequestParam(required=true) String type, 
																@RequestParam(required=false) Integer ageInt, 
																@RequestParam(required=false) Integer annualIncomeThresholdInt) {
		int age;
		int annualIncomeThreshold;
		
		switch(type) {
			case "Student Encourage Bonus":
				age = (ageInt == null) ? 16 : ageInt;
				annualIncomeThreshold = (annualIncomeThresholdInt == null) ? 150000 : annualIncomeThresholdInt;
				return governmentGrantDisbursementService.findStudentEncouragementBonus(age, annualIncomeThreshold);
			case "Family Togetherness Scheme":
				age = (ageInt == null) ? 18 : ageInt;
				return governmentGrantDisbursementService.findFamilyTogethernessScheme(age);
			case "Elder Bonus":
				age = (ageInt == null) ? 50 : ageInt;
				return governmentGrantDisbursementService.findElderBonus(age);
			case "Baby Sunshine Grant":
				age = (ageInt == null) ? 5 : ageInt;
				return governmentGrantDisbursementService.findBabySunshineGrant(age);
			case "YOLO GST Grant":
				annualIncomeThreshold = (annualIncomeThresholdInt == null) ? 100000 : annualIncomeThresholdInt;
				return governmentGrantDisbursementService.findYOLOGSTGrant(annualIncomeThreshold);
		}
		
		return null;	
	}
}
