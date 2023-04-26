package com.example.itspower.response.export;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportExcelEmpRest {
    private String reportDate;
    private String restName;
    private String labor;
    private String groupName;
    private String reasonName;

}
