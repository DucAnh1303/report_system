package com.example.itspower.model.entity;

import com.example.itspower.model.resultset.GroupRoleDto;
import com.example.itspower.model.resultset.RootNameDto;
import com.example.itspower.response.group.ViewDetailGroupResponse;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "group_role")
@Data
@SqlResultSetMapping(
        name = "RootNameDto",
        classes = @ConstructorResult(
                targetClass = RootNameDto.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "name", type = String.class),
                }
        )
)

@NamedNativeQuery(
        name = "findAllRoot",
        query = "select id,group_name as name from group_role where parent_id is null",
        resultSetMapping = "RootNameDto"
)

@SqlResultSetMapping(
        name = "GroupRoleDto",
        classes = @ConstructorResult(targetClass = GroupRoleDto.class, columns = {
                        @ColumnResult(name = "id", type = int.class),
                        @ColumnResult(name = "parentId", type = int.class),
                        @ColumnResult(name = "demarcationAvailable", type = Integer.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "label", type = String.class),
                }
        )
)
@NamedNativeQuery(name = "findAllRole", query = "select gr.id                 as id,\n" +
        "       gr.group_name                               as name,\n" +
        "       gr.group_name                               as label,\n" +
        "       (IF(gr.parent_id is null, 0, gr.parent_id)) as parentId,\n" +
        "        gr.demarcation_available        as demarcationAvailable\n" +
        "from group_role gr;",
        resultSetMapping = "GroupRoleDto"
)
@SqlResultSetMapping(
        name = "viewDetailDto",
        classes = @ConstructorResult(
                targetClass = ViewDetailGroupResponse.class,
                columns = {
                        @ColumnResult(name = "groupKey", type = Integer.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "parentId", type = Integer.class),
                        @ColumnResult(name = "demarcation", type = Integer.class),
                        @ColumnResult(name = "laborProductivity", type = Integer.class),
                        @ColumnResult(name = "restEmp", type = Integer.class),
                        @ColumnResult(name = "partTimeEmp", type = Integer.class),
                        @ColumnResult(name = "studentNum", type = Integer.class),
                        @ColumnResult(name = "riceCus", type = Integer.class),
                        @ColumnResult(name = "riceVip", type = Integer.class),
                        @ColumnResult(name = "riceEmp", type = Integer.class),
                        @ColumnResult(name = "totalRiceNum", type = Integer.class),
                }
        )
)

@NamedNativeQuery(
        name = "findByViewDetail",
        query = "SELECT gr.id as groupKey ,gr.group_name as name, gr.parent_id as parentId , " +
                "(r.demarcation + r.student_num + r.part_time_num) as demarcation ,  " +
                "((r.demarcation+r.student_num+r.part_time_num) -r.student_num -r.rest_num- " +
                "(select tr.transfer_num from transfer tr where tr.report_id = r.id and tr.`type` = 1) " +
                "- (select tr1.transfer_num from transfer tr1 where tr1.report_id = r.id and tr1.`type` = 2)) as laborProductivity , "+
                "r.rest_num as restEmp, " +
                "r.part_time_num as partTimeEmp, r.student_num as studentNum , " +
                "ri.rice_Cus as riceCus, ri.rice_vip as riceVip, ri.rice_emp as riceEmp, " +
                "(ri.rice_Cus + ri.rice_vip + ri.rice_emp) as totalRiceNum " +
                "FROM group_role gr left join report_system.report  r on r.group_id=gr.id left join rice ri on ri.report_id=r.id " +
                "Where DATE_FORMAT(r.report_date, '%Y%m%d') = DATE_FORMAT(:reportDate, '%Y%m%d') " ,
        resultSetMapping = "viewDetailDto"
)

@NamedNativeQuery(
        name = "findByViewDetailParent",
        query = "SELECT gr.id as groupKey ,gr.group_name as name, gr.parent_id as parentId , " +
                "r.demarcation as demarcation,  " +
                "r.labor_productivity as laborProductivity, r.rest_num as restEmp, " +
                "r.part_time_num as partTimeEmp, r.student_num as studentNum , " +
                "ri.rice_Cus as riceCus, ri.rice_vip as riceVip, ri.rice_emp as riceEmp, "+
                "(NULLIF(ri.rice_Cus,0) + NULLIF(ri.rice_vip,0) + NULLIF(ri.rice_emp,0)) as totalRiceNum " +
                "FROM group_role gr left join report_system.report  r on r.group_id=gr.id left join rice ri on ri.report_id=r.id " +
                "where gr.parent_id is null " ,
        resultSetMapping = "viewDetailDto"
)

public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "group_name")
    private String groupName = "";
    @Column(name = "parent_id")
    private Integer parentId;
    @Column(name = "demarcation_available")
    private Integer demarcationAvailable;


}
