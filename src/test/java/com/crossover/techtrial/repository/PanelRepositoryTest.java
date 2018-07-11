package com.crossover.techtrial.repository;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.crossover.techtrial.model.Panel;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class PanelRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private PanelRepository panelRepository;
	
	@Test
	public void findBySerialTest() {
		Panel panel = panelRepository.findBySerial("1234567890123456");
		Assert.assertTrue(panel.getSerial().equals("1234567890123456"));
	}
}
