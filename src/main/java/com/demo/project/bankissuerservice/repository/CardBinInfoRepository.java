package com.demo.project.bankissuerservice.repository;

import com.demo.project.bankissuerservice.entity.CardBinInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CardBinInfoRepository extends JpaRepository<CardBinInfo, Long> {

    List<CardBinInfo> findAllByBin(String bin);

    @Modifying
    @Query(value = "CREATE TABLE card_bin_info_new (LIKE card_bin_info INCLUDING ALL)", nativeQuery = true)
    void createNewTableForBackup();

    @Modifying
    @Query(value = "ALTER TABLE card_bin_info RENAME TO card_bin_info_old", nativeQuery = true)
    void renameOldTable();

    @Modifying
    @Query(value = "ALTER TABLE card_bin_info_new RENAME TO card_bin_info", nativeQuery = true)
    void renameNewTable();


    @Modifying
    @Query(value = "DROP TABLE IF EXISTS card_bin_info_old", nativeQuery = true)
    void dropOldTable();
}
