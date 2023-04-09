package com.example.itspower.service;

import com.example.itspower.model.entity.GroupEntity;
import com.example.itspower.request.GroupRoleRequest;
import com.example.itspower.response.group.GroupRoleResponse;

import java.util.List;

public interface GroupRoleService {
    List<GroupRoleResponse> searchAll();
    List<GroupEntity> searchAllByParentId(int parentId);

    List<GroupEntity> searchAllByParentIdIsNull();
    List<String> getName();

    Object getDemarcationRes(Integer groupId);

    Object updateGroupRole(String groupName, Integer demarcation,String parentName);

    void delete(String groupNam,String parentName);

    Object save( GroupRoleRequest groupRoleRequest);
}
