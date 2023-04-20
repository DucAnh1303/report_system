package com.example.itspower.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupRoleRequest {
    private Float demarcation;
    private String groupName;
    private Integer parentId;
}
