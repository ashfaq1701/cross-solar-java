package com.crossover.techtrial.controller;

import com.crossover.techtrial.dto.DailyElectricity;
import com.crossover.techtrial.model.HourlyElectricity;
import com.crossover.techtrial.model.Panel;
import com.crossover.techtrial.service.HourlyElectricityService;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;


/**
 * PanelControllerTest class will test all APIs in PanelController.java.
 * @author Crossover
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PanelControllerTest {
  
  MockMvc mockMvc;
  
  @Mock
  private PanelController panelController;
  
  @Autowired
  private TestRestTemplate template;
  
  @Mock
  private HourlyElectricityService hourlyElectricityService;

  @Before
  public void setup() throws Exception {
    mockMvc = MockMvcBuilders.standaloneSetup(panelController).build();
    
  }

  @Test
  public void testPanelShouldBeRegistered() throws Exception {
    HttpEntity<Object> panel = getHttpEntity(
        "{\"serial\": \"2323232323232323\", \"longitude\": \"54.123232\"," 
            + " \"latitude\": \"54.123232\",\"brand\":\"tesla\" }");
    ResponseEntity<Panel> response = template.postForEntity(
        "/api/register", panel, Panel.class);
    Assert.assertEquals(202,response.getStatusCode().value());
  }
  
  @Test
  public void testPanelShouldNotBeRegistered() throws Exception {
	  HttpEntity<Object> panel = getHttpEntity(
		        "{\"serial\": \"232323\", \"longitude\": \"54.1232325\"," 
		            + " \"latitude\": \"54.1232321\",\"brand\":\"tesla\" }");
	  ResponseEntity<Panel> response = template.postForEntity(
		        "/api/register", panel, Panel.class);
	  Assert.assertEquals(400,response.getStatusCode().value());
  }
  
  @Test
  public void saveHourlyElectricityTest() throws Exception {
	  HttpEntity<Object> hourlyElectricity = getHttpEntity(
		        "{\"generatedElectricity\": \"189\", \"readingAt\": \"2018-07-11T01:44:00\" }");
	  ResponseEntity<HourlyElectricity> response = template.postForEntity(
		        "/api/panels/1234567890123456/hourly", hourlyElectricity, HourlyElectricity.class);
	  Assert.assertEquals(202, response.getStatusCode().value());
  }
  
  @Test
  public void hourlyElectricityTest() throws Exception {
	  ResponseEntity<Object> response = template.getForEntity(
			  "/api/panels/1234567890123456/hourly",
			  Object.class);
	  Assert.assertEquals(200, response.getStatusCode().value());
  }
  
  @Test
  public void DailyElectricityTest() throws Exception {
	  ResponseEntity<List<DailyElectricity>> response = template.getForEntity("/api/panels/1234567890123456/daily", 
			  (Class<List<DailyElectricity>>)(Class<?>)List.class);
	  List<DailyElectricity>list = response.getBody();
	  Assert.assertTrue(list.size() > 0);
  }

  private HttpEntity<Object> getHttpEntity(Object body) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<Object>(body, headers);
  }
}
