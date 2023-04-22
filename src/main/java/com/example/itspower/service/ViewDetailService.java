package com.example.itspower.service;

import com.example.itspower.response.group.ViewDetailGroups;
import org.springframework.core.io.InputStreamResource;

import java.io.IOException;
import java.util.List;

public interface ViewDetailService {
    List<ViewDetailGroups> searchAllView(String reportDate);

    InputStreamResource exportExcel(String reportDate) throws IOException, NoSuchFieldException, IllegalAccessException;
}
