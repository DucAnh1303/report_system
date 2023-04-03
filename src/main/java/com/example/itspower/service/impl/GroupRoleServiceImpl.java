package com.example.itspower.service.impl;

import com.example.itspower.model.entity.GroupEntity;
import com.example.itspower.model.resultset.GroupRoleDto;
import com.example.itspower.repository.GroupRoleRepository;
import com.example.itspower.response.SuccessResponse;
import com.example.itspower.response.group.GroupRoleResponse;
import com.example.itspower.response.group.ViewDetailGroupResponse;
import com.example.itspower.response.group.ViewDetailGroups;
import com.example.itspower.service.GroupRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupRoleServiceImpl implements GroupRoleService {
    @Autowired
    private GroupRoleRepository groupRoleRepository;

    private static final String OFFICE = "Office";
    private static final String VANPHONG = "Văn phòng";
    private static final String DONVILE = "Đơn vị lẻ";

    @Override
    public List<GroupRoleResponse> searchAll() {
        return getSubListChildren(groupRoleRepository.searchAll());
    }

    public List<GroupRoleResponse> getSubListChildren(List<GroupRoleDto> groups) {
        List<GroupRoleResponse> groupRoleResponses = new ArrayList<>();
        groups.forEach(i -> {
            GroupRoleResponse groupRoleResponse = new GroupRoleResponse(i);
            groupRoleResponses.add(groupRoleResponse);
        });
        Map<Integer, List<GroupRoleResponse>> parentIdToChildren = groupRoleResponses.stream().collect(Collectors.groupingBy(GroupRoleResponse::getParentId));
        groupRoleResponses.forEach(p -> p.setChildren(parentIdToChildren.get(p.getId())));
        return parentIdToChildren.get(0);
    }

    @Override
    public Object getDetailsReport(String reportDate) {
        List<ViewDetailGroupResponse> mapReportParent = groupRoleRepository.getDetailParent();
        List<ViewDetailGroupResponse> mapReport = groupRoleRepository.getDetails(reportDate);
        if (!CollectionUtils.isEmpty(mapReport)) {
            List<ViewDetailGroups> mapData = new ArrayList<>();
            List<ViewDetailGroups> root = new ArrayList<>();
            for (ViewDetailGroupResponse mapParent : mapReportParent) {
                mapData.add(new ViewDetailGroups(mapParent));
            }
            for (ViewDetailGroupResponse mapChildren : mapReport) {
                mapData.add(new ViewDetailGroups(mapChildren));
            }
            for (Integer parentId : groupRoleRepository.getParentId()) {
                root.add(mapData.stream().filter(map -> map.getKey().intValue() == parentId.intValue()).collect(Collectors.toList()).get(0));
            }
            float totalLaborReportsProductivity = 0;
            int totalRiseVipAll = 0;
            int totalRiseCusAll = 0;
            int totalRiseEmpAll = 0;
            for (ViewDetailGroups viewDetailGroups : root) {
                int restNum = 0;
                float labor = 0;
                int partTime = 0;
                int studentNum = 0;
                int totalRiseVipChild = 0;
                int totalRiseCusChild = 0;
                int totalRiseEmpChild = 0;
                List<ViewDetailGroups> children = mapData.stream().filter(z -> z.getParentId().intValue() == viewDetailGroups.getKey().intValue()).collect(Collectors.toList());
                for (ViewDetailGroups item : children) {
                    if (viewDetailGroups.getName().equals(OFFICE) || viewDetailGroups.getName().equals(VANPHONG)) {
                        item.setEnterprise(null);
                    } else {
                        item.setOffice(null);
                    }
                    if (item.getParentId().intValue() == viewDetailGroups.getKey()) {
                        restNum += item.getNumberLeave();
                        labor += item.getLaborProductivity();
                        partTime += item.getPartTimeEmp();
                        studentNum += item.getStudentNum();
                        totalRiseVipChild += item.getTotalRiceVip();
                        totalRiseCusChild += item.getTotalRiceCus();
                        totalRiseEmpChild += item.getTotalRiceEmp();
                    }
                    item.setTotalRiceEmp(null);
                    item.setTotalRiceCus(null);
                    item.setTotalRiceVip(null);
                }
                if (viewDetailGroups.getName().equals(OFFICE) || viewDetailGroups.getName().equals(VANPHONG)) {
                    viewDetailGroups.setOffice((int) labor);
                    viewDetailGroups.setEnterprise(null);
                    viewDetailGroups.viewDetailGroups(restNum, labor, partTime, studentNum, null, null, null);
                } else {
                    viewDetailGroups.setEnterprise((int) labor);
                    viewDetailGroups.setOffice(null);
                    viewDetailGroups.viewDetailGroups(restNum, labor, partTime, studentNum, null, null, null);
                }
                totalLaborReportsProductivity += labor;
                totalRiseVipAll += totalRiseVipChild;
                totalRiseCusAll += totalRiseCusChild;
                totalRiseEmpAll += totalRiseEmpChild;
            }
            root.get(0).setTotalRiceVip(totalRiseVipAll);
            root.get(0).setTotalRiceEmp(totalRiseEmpAll);
            root.get(0).setTotalRiceCus(totalRiseCusAll);
            root.get(0).setTotalLaborProductivity(totalLaborReportsProductivity);
            float totalRatioOfOFFifceAndDonvile = 0;
            for (ViewDetailGroups viewDetail : root) {
                if (viewDetail.getName().equals(OFFICE) || viewDetail.getName().equals(VANPHONG)) {
                    float ratioOffice = (float) Math.round(((viewDetail.getLaborProductivity() / totalLaborReportsProductivity) * 100) * 10) / 10;
                    viewDetail.setRatio(ratioOffice);
                    totalRatioOfOFFifceAndDonvile += ratioOffice;
                } else if (viewDetail.getName().equals(DONVILE)) {
                    float ratioDonVile = (float) Math.round(((viewDetail.getLaborProductivity() / totalLaborReportsProductivity) * 100) * 10) / 10;
                    viewDetail.setRatio(ratioDonVile);
                    totalRatioOfOFFifceAndDonvile += ratioDonVile;
                } else {
                    viewDetail.setRatio((float) Math.round(((viewDetail.getLaborProductivity() / totalLaborReportsProductivity) * 100) * 10) / 10);
                }
            }
            root.get(0).setTotalRatioOfOfficeAndDonvile(totalRatioOfOFFifceAndDonvile);
            Map<Integer, List<ViewDetailGroups>> parentIdToChildren =
                    mapData.stream().collect(Collectors.groupingBy(ViewDetailGroups::getParentId));
            mapData.forEach(p -> p.setChildren(parentIdToChildren.get(p.getKey())));
            return new SuccessResponse<>(HttpStatus.OK.value(), "successfully", parentIdToChildren.get(0));
        }
        return new SuccessResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "that day has not been reported", null);
    }

    @Override
    public List<GroupEntity> searchAllByParentId(int parentId) {
        return groupRoleRepository.findAllByParentId(parentId);
    }

    @Override
    public List<GroupEntity> searchAllByParentIdIsNull() {
        return groupRoleRepository.findAllByParentIdNotNull();
    }

    @Override
    public Object getDemarcationRes(Integer groupId) {
        return groupRoleRepository.getDemarcationRes(groupId);
    }

    @Override
    public Object updateGroupRole(String groupName, Integer demarcation) {
        Optional<GroupEntity> groupCheck = groupRoleRepository.findByGroupName(groupName);
        if (groupCheck.isEmpty()) {
            return new SuccessResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "group id is empty or null ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        GroupEntity groupEntity = groupRoleRepository.update(groupCheck.get().getId(), groupCheck.get().getGroupName(), groupCheck.get().getParentId(), demarcation);
        return new SuccessResponse<>(HttpStatus.OK.value(), "update group demarcation success", groupEntity);
    }
}
