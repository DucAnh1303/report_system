package com.example.itspower.repository.repositoryjpa;

import com.example.itspower.model.entity.EmployeeGroupEntity;
import com.example.itspower.response.employee.EmployeeGroupResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeGroupRepository extends JpaRepository<EmployeeGroupEntity,Integer> {
    @Query(name = "view_all_employee", nativeQuery = true)
    Page<EmployeeGroupResponse> getEmployee(@Param("groupId")Integer groupId
            , @Param("groupName")String groupName, @Param("laborCode")String laborCode, Pageable pageable);
}
