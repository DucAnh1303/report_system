package com.example.itspower.repository;

import com.example.itspower.model.entity.ReportEntity;
import com.example.itspower.repository.repositoryjpa.ReportJpaRepository;
import com.example.itspower.response.request.ReportRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReportRepository {
    private final ReportJpaRepository reportJpaRepository;

    public Optional<ReportEntity> findByOrderDate(String orderDate) {
        return reportJpaRepository.findByOrderDate(orderDate);
    }

    public ReportEntity save(ReportRequest request) {
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setOrderDate(new Date());
        reportEntity.setUserGroupId(request.getUserGroupId());
        reportEntity.setCreateBy(request.getCreateBy());
        return reportJpaRepository.save(reportEntity);
    }
}