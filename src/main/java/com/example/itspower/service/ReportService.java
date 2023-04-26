package com.example.itspower.service;

import com.example.itspower.request.ReportRequest;

import java.util.List;

public interface ReportService {
    Object reportDto(String reportDate, int groupId);

    Object save(ReportRequest request, int groupId);

    Object update(ReportRequest request, int groupId);
    void deleteRestIdsAndReportId(Integer reportId,List<Integer> restIds);

    void deleteRestEmployee(Integer groupId, List<String> laborEmp);
}
