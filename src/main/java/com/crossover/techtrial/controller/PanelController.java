package com.crossover.techtrial.controller;

import com.crossover.techtrial.dto.DailyElectricity;
import com.crossover.techtrial.model.HourlyElectricity;
import com.crossover.techtrial.model.Panel;
import com.crossover.techtrial.service.HourlyElectricityService;
import com.crossover.techtrial.service.PanelService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Panel Controller for all Rest APIs related to Panel.
 * @author Crossover
 *
 */

@RestController
public class PanelController {

  @Autowired
  PanelService panelService;
  
  @Autowired
  HourlyElectricityService hourlyElectricityService;
  
  /**
   * Register a Panel to System and start receiving the electricity statistics.
   * @param panel to register.
   * @return
   */
  @PostMapping(path = "/api/register")
  public ResponseEntity<?> registerPanel(@RequestBody Panel panel) {
    return  ResponseEntity.accepted().body(panelService.register(panel));
  }
  
  /**
   * Controller Method to save hourly Electricity to Database. 
   * @param panelSerial Serial number of Panel.
   * @param hourlyElectricity  generated electricity for this panel.
   * @return
   */
  
  @PostMapping(path = "/api/panels/{panel-serial}/hourly")
  public ResponseEntity<?> saveHourlyElectricity(
      @PathVariable(value = "panel-serial") String panelSerial, 
      @RequestBody HourlyElectricity hourlyElectricity) {
	  Panel panel = panelService.findBySerial(panelSerial);
	  if (panel == null) {
		  return ResponseEntity.notFound().build(); 
	  }
	  hourlyElectricity.setPanel(panel);
	  return ResponseEntity.accepted().body(hourlyElectricityService.save(hourlyElectricity));
  }
   
  /**
   * Get Hourly Electricity from Previous dates.
   */
  
  @GetMapping(path = "/api/panels/{panel-serial}/hourly")
  public ResponseEntity<?> hourlyElectricity(
      @PathVariable(value = "panel-serial") String panelSerial,
      @PageableDefault(size = 5,value = 0) Pageable pageable) {
    Panel panel = panelService.findBySerial(panelSerial);
    if (panel == null) {
      return ResponseEntity.notFound().build(); 
    }
    Page<HourlyElectricity> page = hourlyElectricityService.getAllHourlyElectricityByPanelId(
        panel.getId(), pageable);
    return ResponseEntity.ok(page);
  }
  
  /**
   * This end point is used by Front end charts component to plot the daily statistics of 
   * electricity generated by this Panel from the day it registered to end of previous day.
   * @param panelSerial is unique serial for this Panel.
   * @return
   */
  
  @GetMapping(path = "/api/panels/{panel-serial}/daily")
  public ResponseEntity<List<DailyElectricity>> allDailyElectricityFromYesterday(
      @PathVariable(value = "panel-serial") String panelSerial) {
    Panel panel = panelService.findBySerial(panelSerial);
    if (panel == null) {
    	return ResponseEntity.notFound().build();
    }
    List<DailyElectricity> dailyElectricityForPanel = hourlyElectricityService.getAllDailyElectricityByPanelId(panel.getId());
    return ResponseEntity.ok(dailyElectricityForPanel);
  }
}