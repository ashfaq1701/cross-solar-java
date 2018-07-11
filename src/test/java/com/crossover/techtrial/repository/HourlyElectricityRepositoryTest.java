package com.crossover.techtrial.repository;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.crossover.techtrial.dto.DailyElectricity;
import com.crossover.techtrial.model.HourlyElectricity;

import org.junit.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class HourlyElectricityRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private HourlyElectricityRepository hourlyElectricityRepository;
	
	@Test
	public void findAllByPanelIdOrderByReadingAtDescTest() {
		Pageable pageable = PageRequest.of(0, 4);
		Page<HourlyElectricity> page = hourlyElectricityRepository.findAllByPanelIdOrderByReadingAtDesc(1L, pageable);
		Assert.assertTrue(page.getContent().size() > 0);
		Assert.assertTrue(page.getTotalElements() > 0);
		Assert.assertTrue(page.getPageable().getPageSize() == 4);
		
	}
	
	@Test
	public void getAllDailyElectricityByPanelId() {
		List<DailyElectricity> list = hourlyElectricityRepository.getAllDailyElectricityByPanelId(1L);
		Assert.assertTrue(list.size() > 0);
	}
}
