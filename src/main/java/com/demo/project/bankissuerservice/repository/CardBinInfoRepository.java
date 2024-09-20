package com.demo.project.bankissuerservice.repository;

import com.demo.project.bankissuerservice.model.CardBinInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardBinInfoRepository extends JpaRepository<CardBinInfo, String> {

    Optional<CardBinInfo> findByMinRangeLessThanEqualAndMaxRangeGreaterThanEqual(String min,
        String max);
}
