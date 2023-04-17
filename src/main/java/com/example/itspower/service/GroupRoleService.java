package com.example.itspower.service;

import com.example.itspower.model.entity.GroupEntity;
import com.example.itspower.request.GroupRoleRequest;
import com.example.itspower.response.group.GroupRoleDemarcationRes;
import com.example.itspower.response.group.GroupRoleResponse;
import com.example.itspower.response.group.ResponseCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GroupRoleService {
    List<GroupRoleResponse> searchAll();

    Page<GroupEntity> getAllDamercation(String groupName,Pageable pageable);

    List<Integer> searchAllDeleteTM();

    List<ResponseCount> count(String reportDate);

    List<GroupEntity> searchAllByParentId(int parentId);

    List<GroupEntity> searchAllByParentIdIsNull();

    List<String> getName();

    Object getDemarcationRes(Integer groupId);
    List<GroupRoleDemarcationRes> getAllDemarcationRes();

    Object updateGroupRole(Integer id, Integer demarcation);

    void delete(Integer groupId);

    Object save(GroupRoleRequest groupRoleRequest);

    Object getViewRoot();
}
