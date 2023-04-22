package com.example.itspower.response.export;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeExportExcel {
    private String groupName;
    private String startDate;
    private String employeeName;
    private String laborCode;
}
