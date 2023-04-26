package com.example.itspower.model.entity;

import com.example.itspower.model.resultset.GroupRoleDto;
import com.example.itspower.model.resultset.RootNameDto;
import com.example.itspower.model.resultset.ViewAllDto;
import com.example.itspower.response.group.GroupRoleDemarcationRes;
import com.example.itspower.response.group.ViewDetailGroupResponse;
import com.example.itspower.response.group.ViewGroupRoot;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "group_role")
@Data
@SqlResultSetMapping(
        name = "RootNameDto",
        classes = @ConstructorResult(
                targetClass = RootNameDto.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class),}
        )
)

@NamedNativeQuery(
        name = "findAllRoot",
        query = "select DISTINCT parent_id as id from group_role gr2 where parent_id  is not null  order by parent_id desc",
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
@NamedNativeQuery(name = "findAllRole", query = "select gr.id  as id,\n" +
        "       gr.group_name                               as name,\n" +
        "       gr.group_name                               as label,\n" +
        "       (IF(gr.parent_id is null, 0, gr.parent_id)) as parentId,\n" +
        "        gr.demarcation_available        as demarcationAvailable\n" +
        "from group_role gr;",
        resultSetMapping = "GroupRoleDto"
)
@SqlResultSetMapping(
        name = "getAllDinhBien",
        classes = @ConstructorResult(targetClass = GroupRoleDemarcationRes.class, columns = {
                @ColumnResult(name = "groupId", type = int.class),
                @ColumnResult(name = "dinhBien", type = Integer.class),
                @ColumnResult(name = "groupName", type = String.class)
        }
        )
)


@NamedNativeQuery(name = "findAllDinhBien", query = "SELECT gr.id as groupId," +
        "gr.demarcation_available as dinhBien," +
        "gr .group_name as groupName\n" +
        "from group_role gr",
        resultSetMapping = "getAllDinhBien"
)

@SqlResultSetMapping(
        name = "ViewAllDto",
        classes = @ConstructorResult(targetClass = ViewAllDto.class, columns = {
                @ColumnResult(name = "groupId", type = Integer.class),
                @ColumnResult(name = "groupParentId", type = Integer.class),
                @ColumnResult(name = "groupName", type = String.class),
                @ColumnResult(name = "reportDemarcation", type = Integer.class),
                @ColumnResult(name = "laborProductivity", type = Integer.class),
                @ColumnResult(name = "partTimeNum", type = Integer.class),
                @ColumnResult(name = "restNum", type = Integer.class),
                @ColumnResult(name = "studentNum", type = Integer.class),
                @ColumnResult(name = "riceCus", type = Integer.class),
                @ColumnResult(name = "riceEmp", type = Integer.class),
                @ColumnResult(name = "riceVip", type = Integer.class),
        }
        )
)
@NamedNativeQuery(name = "findAllRoleView", query = "SELECT groupId,groupName,groupParentId,reportDemarcation,\n" +
        "laborProductivity,partTimeNum,restNum,studentNum,riceCus,riceEmp,riceVip\n" +
        "FROM (\n" +
        "    SELECT gr.id AS groupId,\n" +
        "           gr.group_name AS groupName,\n" +
        "           IF(gr.parent_id IS NULL, 0, gr.parent_id) AS groupParentId,\n" +
        "           rp.demarcation AS reportDemarcation,\n" +
        "           rp.labor_productivity AS laborProductivity,\n" +
        "           rp.part_time_num AS partTimeNum,\n" +
        "           rp.rest_num AS restNum,\n" +
        "           rp.student_num AS studentNum,\n" +
        "           r.rice_cus AS riceCus,\n" +
        "           r.rice_emp AS riceEmp,\n" +
        "           r.rice_vip AS riceVip,\n" +
        "           rp.report_date AS reportDate\n" +
        "    FROM group_role gr\n" +
        "    LEFT JOIN report rp ON gr.id = rp.group_id \n" +
        "    LEFT JOIN rice r ON rp.id = r.report_id \n" +
        ") subq -- add an alias for the subquery\n" +
        "WHERE DATE_FORMAT(subq.reportDate, '%Y%m%d') = DATE_FORMAT(:reportDate,'%Y%m%d')\n" +
        "or subq.reportDate is null  ",
        resultSetMapping = "ViewAllDto"
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
                "r.demarcation as demarcation ,  " +
                "(r.demarcation -r.student_num -r.rest_num- " +
                "(select tr.transfer_num from transfer tr where tr.report_id = r.id and tr.`type` = 1) " +
                "- (select tr1.transfer_num from transfer tr1 where tr1.report_id = r.id and tr1.`type` = 2)) as laborProductivity , " +
                "r.rest_num as restEmp, " +
                "r.part_time_num as partTimeEmp, r.student_num as studentNum , " +
                "ri.rice_Cus as riceCus, ri.rice_vip as riceVip, ri.rice_emp as riceEmp, " +
                "(ri.rice_Cus + ri.rice_vip + ri.rice_emp) as totalRiceNum " +
                "FROM group_role gr left join report  r on r.group_id=gr.id left join rice ri on ri.report_id=r.id " +
                "Where DATE_FORMAT(r.report_date, '%Y%m%d') = DATE_FORMAT(:reportDate, '%Y%m%d') ",
        resultSetMapping = "viewDetailDto"
)

@NamedNativeQuery(
        name = "findByViewDetailParent",
        query = "SELECT gr.id as groupKey ,gr.group_name as name, gr.parent_id as parentId , " +
                "r.demarcation as demarcation,  " +
                "r.labor_productivity as laborProductivity, r.rest_num as restEmp, " +
                "r.part_time_num as partTimeEmp, r.student_num as studentNum , " +
                "ri.rice_Cus as riceCus, ri.rice_vip as riceVip, ri.rice_emp as riceEmp, " +
                "(NULLIF(ri.rice_Cus,0) + NULLIF(ri.rice_vip,0) + NULLIF(ri.rice_emp,0)) as totalRiceNum " +
                "FROM group_role gr left join report  r on r.group_id=gr.id left join rice ri on ri.report_id=r.id " +
                "where gr.id in (SELECT DISTINCT gr2.parent_id FROM group_role gr2 ) ",
        resultSetMapping = "viewDetailDto"
)

@SqlResultSetMapping(
        name = "ViewGroupRoot",
        classes = @ConstructorResult(targetClass = ViewGroupRoot.class, columns = {
                @ColumnResult(name = "id", type = Integer.class),
                @ColumnResult(name = "groupName", type = String.class),
                @ColumnResult(name = "numberChild", type = Integer.class)
        }
        )
)
@NamedNativeQuery(name = "view_group_root", query = "SELECT gr.id,gr.group_name as groupName,(SELECT COUNT(gr2.parent_id) from " +
        "group_role gr2 where  gr2.parent_id = gr.id ) as numberChild from group_role gr " +
        "where gr.id in (SELECT DISTINCT gr1.parent_id from group_role gr1 ) and group_name <> 'Tổ may' ",
        resultSetMapping = "ViewGroupRoot"
)

public class GroupEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "group_name")
    private String groupName = "";
    @Column(name = "parent_id")
    private Integer parentId;
    @Column(name = "demarcation_available")
    private Float demarcationAvailable;


}
