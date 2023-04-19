package com.example.itspower.repository;

import com.example.itspower.exception.ResourceNotFoundException;
import com.example.itspower.model.entity.RiceEntity;
import com.example.itspower.repository.repositoryjpa.RiceJpaRepository;
import com.example.itspower.request.RiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
public class RiceRepository {
    @Autowired
    private RiceJpaRepository riceJpaRepository;

    public Optional<RiceEntity> getByRiceDetail(Integer reportId) {
        return riceJpaRepository.findByReportId(reportId);
    }


    public RiceEntity saveRice(RiceRequest riceRequest, Integer reportId) {
        try{
            RiceEntity entity = new RiceEntity();
            entity.setReportId(reportId);
            entity.setRiceEmp(riceRequest.getRiceEmp());
            entity.setRiceCus(riceRequest.getRiceCus());
            entity.setRiceVip(riceRequest.getRiceVip());
            return riceJpaRepository.save(entity);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }
    @Transactional
    public RiceEntity updateRice(RiceRequest riceRequest, Integer reportId) {
        RiceEntity entity = new RiceEntity();
        if (riceRequest.getRiceId() == 0) {
            throw new ResourceNotFoundException(HttpStatus.BAD_REQUEST.value(), "riceId not exits", HttpStatus.BAD_REQUEST.name());
        }
        entity.setRiceId(riceRequest.getRiceId());
        entity.setReportId(reportId);
        entity.setRiceEmp(riceRequest.getRiceEmp());
        entity.setRiceCus(riceRequest.getRiceCus());
        entity.setRiceVip(riceRequest.getRiceVip());
        return riceJpaRepository.save(entity);
    }

    public void deleteReportId(Integer reportId) {
        riceJpaRepository.deleteByReportId(reportId);
    }
}
