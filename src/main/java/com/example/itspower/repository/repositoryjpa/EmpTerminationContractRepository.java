package com.example.itspower.repository.repositoryjpa;

import com.example.itspower.model.entity.EmployeeTerminationOfContractEntity;
import com.example.itspower.response.export.EmployeeExportExcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpTerminationContractRepository extends JpaRepository<EmployeeTerminationOfContractEntity, Integer> {
    @Query(name = "find_by_employee_report",nativeQuery = true)
    List<EmployeeExportExcel> findByEmployee(@Param("reportDate")String reportDate);
}
