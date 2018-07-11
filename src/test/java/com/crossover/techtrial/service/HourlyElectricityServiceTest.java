package com.crossover.techtrial.service;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.crossover.techtrial.dto.DailyElectricity;
import com.crossover.techtrial.model.HourlyElectricity;
import com.crossover.techtrial.model.Panel;
import com.crossover.techtrial.repository.HourlyElectricityRepository;
import org.springframework.data.domain.Pageable;

/**
 * HourlyElectricityServiceTest class will test all APIs in HourlyElectricityService.java.
 * @author Crossover
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class HourlyElectricityServiceTest {
	@Autowired
	HourlyElectricityService hourlyElectricityService;
	
	@MockBean
	HourlyElectricityRepository hourlyElectricityRepository;
	
	@Test
	public void saveTest() {
		HourlyElectricity hourlyElectricity = getHourlyElectricity();
		
		Mockito.when(hourlyElectricityRepository.save(hourlyElectricity)).thenReturn(hourlyElectricity);
		
		Assert.assertTrue(hourlyElectricityService.save(hourlyElectricity).equals(hourlyElectricity));
	}
	
	@Test
	public void getAllHourlyElectricityByPanelIdTest() {
		Pageable pageable = PageRequest.of(0, 4);
		Page<HourlyElectricity> hourlyElectricityPage = getHourlyElectricitiesPage();
		Mockito.when(hourlyElectricityRepository.findAllByPanelIdOrderByReadingAtDesc(1L, pageable)).thenReturn(hourlyElectricityPage);
		
		Assert.assertTrue(hourlyElectricityService.getAllHourlyElectricityByPanelId(1L, pageable).equals(hourlyElectricityPage));
	}
	
	@Test
	public void getAllDailyElectricityByPanelIdTest() {
		List<DailyElectricity> dailyElectricities = getDailyElectricities();
		Mockito.when(hourlyElectricityRepository.getAllDailyElectricityByPanelId(1L)).thenReturn(dailyElectricities);
		Assert.assertTrue(hourlyElectricityService.getAllDailyElectricityByPanelId(1L).equals(dailyElectricities));
	}
	
	public Panel getPanel() {
		Panel panel = new Panel();
		panel.setId(1L);
		panel.setBrand("tesla");
		panel.setSerial("1234567890123456");
		panel.setLatitude(16.999671);
		panel.setLongitude(51.104394);
		return panel;
	}
	
	public HourlyElectricity getHourlyElectricity() {
		HourlyElectricity hourlyElectricity = new HourlyElectricity();
		hourlyElectricity.setId(1L);
		hourlyElectricity.setReadingAt(LocalDateTime.now());
		hourlyElectricity.setGeneratedElectricity(120L);
		hourlyElectricity.setPanel(getPanel());
		return hourlyElectricity;
	}
	
	public Page<HourlyElectricity> getHourlyElectricitiesPage() {
		ArrayList<HourlyElectricity> arrList = new ArrayList<HourlyElectricity>();
		HourlyElectricity a = getHourlyElectricity();
		HourlyElectricity b = getHourlyElectricity();
		b.setId(2L);
		b.setGeneratedElectricity(170L);
		arrList.add(a);
		arrList.add(b);
		PageImpl<HourlyElectricity> page = new PageImpl<HourlyElectricity>(arrList);
		return page;
	}
	
	public List<DailyElectricity> getDailyElectricities() {
		List<DailyElectricity> arrList = new ArrayList<DailyElectricity>();
		
		DailyElectricity a = new DailyElectricity();
		a.setDate(LocalDate.now());
		a.setAverage(68.0);
		a.setMax(200L);
		a.setMin(50L);
		a.setSum(800L);
		
		DailyElectricity b = new DailyElectricity();
		b.setDate(LocalDate.now());
		b.setAverage(90.0);
		b.setMax(290L);
		b.setMin(65L);
		b.setSum(1000L);
		
		arrList.add(a);
		arrList.add(b);
		
		return arrList;
	}
}
