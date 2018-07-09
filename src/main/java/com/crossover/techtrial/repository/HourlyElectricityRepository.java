package com.crossover.techtrial.repository;

import com.crossover.techtrial.dto.DailyElectricity;
import com.crossover.techtrial.model.HourlyElectricity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * HourlyElectricity Repository is for all operations for HourlyElectricity.
 * @author Crossover
 */
@RestResource(exported = false)
public interface HourlyElectricityRepository 
    extends PagingAndSortingRepository<HourlyElectricity,Long> {
  Page<HourlyElectricity> findAllByPanelIdOrderByReadingAtDesc(Long panelId,Pageable pageable);
  
  @Query("SELECT new com.crossover.techtrial.dto.DailyElectricity(h.readingAt, sum(h.generatedElectricity), avg(h.generatedElectricity), min(h.generatedElectricity), max(h.generatedElectricity)) FROM HourlyElectricity h join h.panel as p where (p.id = :panelId) group by year(h.readingAt), month(h.readingAt) order by year(h.readingAt) desc, month(h.readingAt) desc")
  List<DailyElectricity> getAllDailyElectricityByPanelId(@Param("panelId") Long panelId);
}
