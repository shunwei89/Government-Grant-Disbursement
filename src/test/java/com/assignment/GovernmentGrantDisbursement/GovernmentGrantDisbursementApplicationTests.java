package com.assignment.GovernmentGrantDisbursement;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.assignment.GovernmentGrantDisbursement.model.Household;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class GovernmentGrantDisbursementApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	void contextLoads() {
	}

	@Test
	public void createHousehold() throws JsonProcessingException, Exception {
		Household household = new Household();
		household.setHouseholdType("Landed");
		
		MvcResult mvcResult = this.mockMvc.perform(post("/api/household")
				  .contentType("application/json")
				  .content(objectMapper.writeValueAsString(household)))
				  .andReturn();

		String actualResponse = mvcResult.getResponse().getContentAsString();
		assert(actualResponse.contains("Landed"));
	}
	
	@Test
	public void addMember() throws JsonProcessingException, Exception {
		Household household = new Household();
		household.setHouseholdType("Landed");
		
		MvcResult mvcResult = this.mockMvc.perform(post("/api/household")
				  .contentType("application/json")
				  .content(objectMapper.writeValueAsString(household)))
				  .andReturn();

		String actualResponse = mvcResult.getResponse().getContentAsString();
		
		JSONObject actualJSONObject = new JSONObject(actualResponse);
		JSONObject householdJSON = actualJSONObject.getJSONObject("household"); 		
		int householdID = householdJSON.getInt("householdId");
		
		mvcResult = this.mockMvc.perform(post("/api/member")
				  				.contentType("application/json")
				  				.content("{\"memberName\" : \"Allan\"," 
				  					   + "\"gender\" : \"Male\"," 
				  					   + "\"maritalStatus\" : \"Single\","
				  					   + "\"spouse\" : \"Allan_Spouse\","
				  					   + "\"occupationType\" : \"Unemployed\","
				  					   + "\"annualIncome\" : 10000,"
				  					   + "\"dob\" : \"2020-02-02\","
				  					   + "\"householdID\" : " + householdID
				  					   + "}"))
				  				.andReturn();

		actualResponse = mvcResult.getResponse().getContentAsString();
		assert(actualResponse.contains("Allan"));
	}
	
	@Test
	public void getHouseholdByID() throws JsonProcessingException, Exception {
		Household household = new Household();
		household.setHouseholdType("Landed");
		
		MvcResult mvcResult = this.mockMvc.perform(post("/api/household")
				  .contentType("application/json")
				  .content(objectMapper.writeValueAsString(household)))
				  .andReturn();

		JSONObject actualJSONObject = new JSONObject(mvcResult.getResponse().getContentAsString());
		JSONObject householdJSON = actualJSONObject.getJSONObject("household"); 		
		int householdID = householdJSON.getInt("householdId");
		
		String url = "/api/household/" + householdID;
		mvcResult = this.mockMvc.perform(get(url)).andReturn();

		String actualResponse = mvcResult.getResponse().getContentAsString();
		String expectedResponse = "{\"householdType\":\"Landed\",\"householdId\":"+ householdID +",\"members\":[]}";
		assert(actualResponse.equals(expectedResponse));
	}
	
	@Test
	public void getAllHousehold() throws JsonProcessingException, Exception {
		Household household = new Household();
		household.setHouseholdType("Landed");
		
		MvcResult mvcResult = this.mockMvc.perform(post("/api/household")
				  .contentType("application/json")
				  .content(objectMapper.writeValueAsString(household)))
				  .andReturn();

		mvcResult = this.mockMvc.perform(get("/api/household/")).andReturn();

		String actualResponse = mvcResult.getResponse().getContentAsString();
		assert(actualResponse != null);
	}
}
