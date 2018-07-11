package com.crossover.techtrial.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.crossover.techtrial.model.Panel;
import com.crossover.techtrial.repository.PanelRepository;

/**
 * PanelServiceTest class will test all APIs in PanelService.java.
 * @author Crossover
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PanelServiceTest {
	@Autowired
	PanelService panelService;
	@MockBean
	PanelRepository panelRepository;
	
	@Test
	public void registerTest() {
		Panel panel = getPanel();
		Mockito.when(panelRepository.save(panel)).thenReturn(panel);
		Assert.assertTrue(panelService.register(panel).equals(panel));
	}
	
	@Test
	public void findBySerialTest() {
		Panel panel = getPanel();
		Mockito.when(panelRepository.findBySerial("1234567890123456")).thenReturn(panel);
		Assert.assertTrue(panelService.findBySerial("1234567890123456").equals(panel));
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
}
