package com.crossover.techtrial.service;

import com.crossover.techtrial.model.HourlyElectricity;
import com.crossover.techtrial.repository.HourlyElectricityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * HourlyElectricityServiceImpl will handle electricity generated by a Panel.
 *
 * @author Crossover
 *
 */

@Service
public class HourlyElectricityServiceImpl implements HourlyElectricityService {
  @Autowired
  HourlyElectricityRepository hourlyElectricityRepository;
  
  public HourlyElectricity save(HourlyElectricity hourlyElectricity) {
    return hourlyElectricityRepository.save(hourlyElectricity);
  }
  
  public Page<HourlyElectricity> getAllHourlyElectricityByPanelId(Long panelId, Pageable pageable) {
    return hourlyElectricityRepository.findAllByPanelIdOrderByReadingAtDesc(panelId, pageable);
  }
}