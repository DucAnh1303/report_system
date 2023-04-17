package com.example.itspower.service;
import com.example.itspower.request.userrequest.addUserRequest;
import com.example.itspower.response.employee.EmployeeGroupResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface EmployeeGroupService {
    void saveAll(List<addUserRequest> addUser);

    void delete(List<Integer> ids);

    Page<EmployeeGroupResponse> getEmployee(String groupName, Integer groupId, String laborCode, Pageable pageable);
}
