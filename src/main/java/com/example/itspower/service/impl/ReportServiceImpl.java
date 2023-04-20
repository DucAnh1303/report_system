package com.example.itspower.service.impl;

import com.example.itspower.util.DateUtils;
import com.example.itspower.model.entity.GroupEntity;
import com.example.itspower.model.entity.ReportEntity;
import com.example.itspower.model.entity.RiceEntity;
import com.example.itspower.model.entity.TransferEntity;
import com.example.itspower.model.resultset.ReportDto;
import com.example.itspower.model.resultset.RestDto;
import com.example.itspower.repository.*;
import com.example.itspower.repository.repositoryjpa.EmployeeGroupRepository;
import com.example.itspower.request.ReportRequest;
import com.example.itspower.response.SuccessResponse;
import com.example.itspower.response.report.ReportResponse;
import com.example.itspower.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private RestRepository restRepository;
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private GroupRoleRepository groupRoleRepository;
    @Autowired
    private RiceRepository riceRepository;
    @Autowired
    private EmployeeGroupRepository employeeGroupRepository;

    @Override
    public Object reportDto(String reportDate, int groupId) {
        Optional<ReportEntity> entity = reportRepository.findByReportDateAndGroupId(reportDate, groupId);
        if (entity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SuccessResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "report is not exits now date", HttpStatus.INTERNAL_SERVER_ERROR.name()));
        }
        ReportDto reportDto = reportRepository.reportDto(reportDate, groupId);
        List<RestDto> restDtos = restRepository.getRests(reportDto.getId());
        List<TransferEntity> transferEntities = transferRepository.findByReportId(reportDto.getId());
        Optional<RiceEntity> riceEntity = riceRepository.getByRiceDetail(reportDto.getId());
        return new ReportResponse(reportDto, riceEntity.get(), restDtos, transferEntities);
    }

    @Override
    public Object save(ReportRequest request, int groupId) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 7); // thêm 7 giờ vào thời gian hiện tại
        Date newDate = calendar.getTime();
        Optional<ReportEntity> entity = reportRepository.findByReportDateAndGroupId(DateUtils.formatDate(newDate), groupId);
        if (entity.isPresent()) {
            return new SuccessResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "report date is exits", HttpStatus.INTERNAL_SERVER_ERROR.name());
        }
        ReportEntity reportEntity = reportRepository.saveReport(request, groupId);
        riceRepository.saveRice(request.getRiceRequests(), reportEntity.getId());
        restRepository.saveRest(request.getRestRequests(), reportEntity.getId());
        transferRepository.saveTransfer(request.getTransferRequests(), reportEntity.getId());
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.CREATED.value(), "report success", reportDto(DateUtils.formatDate(reportEntity.getReportDate()), reportEntity.getGroupId())));
    }

    @Override
    @Transactional
    public Object update(ReportRequest request, int groupId) {
        Optional<ReportEntity> entity = reportRepository.findByIdAndGroupId(request.getId(), groupId);
        if (entity.isEmpty()) {
            return new SuccessResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "report is not Exits", HttpStatus.INTERNAL_SERVER_ERROR.name());
        }
        ReportEntity reportEntity = reportRepository.updateReport(request, groupId);
        if (request.getRiceRequests().getRiceId() != null && request.getRiceRequests().getRiceId() != 0) {
            riceRepository.updateRice(request.getRiceRequests(), reportEntity.getId());
        }
        request.getRestRequests().forEach(z -> {
            if (z.getRestId() != null && z.getRestId() != 0) {
                restRepository.updateRest(request.getRestRequests(), reportEntity.getId());
            }
        });
        request.getTransferRequests().forEach(i -> {
            if (i.getTransferId() != null && i.getTransferId() != 0) {
                transferRepository.updateTransfer(request.getTransferRequests(), reportEntity.getId());
            }
        });
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK.value(), "update report success", reportDto(DateUtils.formatDate(reportEntity.getReportDate()), reportEntity.getGroupId())));
    }

    @Override
    public void deleteRestIdsAndReportId(Integer reportId, List<Integer> restIds) {
        restRepository.deleteRestIdsAndReportId(reportId, restIds);
    }


    public void deleteRestEmployee(Integer groupId, List<String> laborEmp) {
        Optional<GroupEntity> groupEntity = groupRoleRepository.findById(groupId);
        if (groupEntity.isPresent()) {
            employeeGroupRepository.deleteByGroupIdAndLaborCodeIn(groupId, laborEmp);
            groupEntity.get().setDemarcationAvailable(groupEntity.get().getDemarcationAvailable() - laborEmp.size());
            groupRoleRepository.save(groupEntity.get());
        }
    }
}