package com.example.itspower.service.impl;
import com.example.itspower.model.entity.EmployeeGroupEntity;
import com.example.itspower.repository.repositoryjpa.EmployeeGroupRepository;
import com.example.itspower.request.userrequest.addUserRequest;
import com.example.itspower.response.employee.EmployeeGroupResponse;
import com.example.itspower.service.EmployeeGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
@Component
public class EmployeeGroupServiceImpl implements EmployeeGroupService {
    @Autowired
    EmployeeGroupRepository groupRepository;
    @Override
    public void saveAll(List<addUserRequest> addUser) {
        List<EmployeeGroupEntity> save = new ArrayList<>();
        for(addUserRequest addUserRequest:addUser){
            EmployeeGroupEntity employeeGroup = new EmployeeGroupEntity();
            if(addUserRequest.getId() !=null){
             employeeGroup.setId(addUserRequest.getId());
            }
            employeeGroup.setGroupId(addUserRequest.getGroupId());
            employeeGroup.setLaborCode(addUserRequest.getLaborCode());
            employeeGroup.setName(addUserRequest.getName());
            save.add(employeeGroup);
        }
        groupRepository.saveAll(save);
    }



    @Override
    public void delete(List<Integer> ids) {
        groupRepository.deleteAllById(ids);
    }

    @Override
    public Page<EmployeeGroupResponse> getEmployee(String groupName, Integer groupId, String laborCode, Pageable pageable) {
        return groupRepository.getEmployee(groupId,groupName,laborCode,pageable);
    }
}
