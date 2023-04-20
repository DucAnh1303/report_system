package com.example.itspower.response.user;

import com.example.itspower.model.entity.GroupEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupResponse {
    private String groupName = "";
    private Integer groupId;
    private Integer parentId;
    private Float demarcationAvailable;

    public GroupResponse(GroupEntity groupEntity) {
        this.groupName = groupEntity.getGroupName();
        this.groupId = groupEntity.getId();
        this.parentId = groupEntity.getParentId();
        this.demarcationAvailable = groupEntity.getDemarcationAvailable();
    }
}
