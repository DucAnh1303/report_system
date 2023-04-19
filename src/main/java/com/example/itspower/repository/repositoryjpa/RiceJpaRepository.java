package com.example.itspower.repository.repositoryjpa;

import com.example.itspower.model.entity.RiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface RiceJpaRepository extends JpaRepository<RiceEntity, Integer> {
    Optional<RiceEntity> findByReportId(Integer reportId);
    @Transactional
    @Modifying
    void deleteByReportId(Integer reportId);
}
