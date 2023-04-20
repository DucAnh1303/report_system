package com.example.itspower.request;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeGroupRequest {
    private Integer groupId;
    private List<String> laborEmp;
}