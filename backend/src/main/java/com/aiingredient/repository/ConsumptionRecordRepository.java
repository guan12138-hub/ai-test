package com.aiingredient.repository;

import com.aiingredient.model.ConsumptionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface ConsumptionRecordRepository extends JpaRepository<ConsumptionRecord, Long> {
    List<ConsumptionRecord> findByRecordDateBetween(LocalDate start, LocalDate end);
    List<ConsumptionRecord> findByIngredientId(Long ingredientId);
    @Query("SELECT FUNCTION('DATE_FORMAT', c.recordDate, '%Y-%m') as month, SUM(c.quantity) FROM ConsumptionRecord c " +
           "WHERE c.recordDate BETWEEN :start AND :end GROUP BY FUNCTION('DATE_FORMAT', c.recordDate, '%Y-%m')")
    List<Object[]> getMonthlyConsumption(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
