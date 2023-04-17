package com.example.itspower.repository.repositoryjpa;

import com.example.itspower.model.entity.GroupEntity;
import com.example.itspower.model.resultset.GroupRoleDto;
import com.example.itspower.model.resultset.RootNameDto;
import com.example.itspower.model.resultset.ViewAllDto;
import com.example.itspower.response.group.ViewDetailGroupResponse;
import com.example.itspower.response.group.ViewGroupRoot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface GroupJpaRepository extends JpaRepository<GroupEntity, Integer> {

    List<GroupEntity> findAllByParentId(int parentId);
    @Query(value = "select * from group_role where group_name like concat('%',?1,'%')",nativeQuery = true)
    Page<GroupEntity> findByGroupName(String groupName, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "Delete FROM group_role where id =:groupId ", nativeQuery = true)
    void deleteByGroupName( @Param("groupId") Integer groupId);

    List<GroupEntity> findAllByParentIdIsNull();

    @Query(name = "findAllRole", nativeQuery = true)
    List<GroupRoleDto> findAllRole();

    @Query(name = "findAllRoleView", nativeQuery = true)
    List<ViewAllDto> findAllViewRole(@Param("reportDate") String reportDate);

    @Query(name = "findAllRoot", nativeQuery = true)
    List<RootNameDto> getAllRoot();

    @Query(value = "SELECT group_name  from group_role gr ", nativeQuery = true)
    List<String> getAllByGroupName();

    @Query(value = "select distinct gr.parent_id from group_role gr where parent_id is not null ", nativeQuery = true)
    List<Integer> getAllParentId();

    @Query(name = "findByViewDetail", nativeQuery = true)
    List<ViewDetailGroupResponse> getDetail(@Param("reportDate") String reportDate);

    @Query(name = "findByViewDetailParent", nativeQuery = true)
    List<ViewDetailGroupResponse> getDetailParent();

    @Query(name = "view_group_root", nativeQuery = true)
    List<ViewGroupRoot> getViewGroup();

    Optional<GroupEntity> findByGroupName(String groupName);

    Optional<GroupEntity> findByGroupNameAndParentId(String groupName, Integer parentId);

}
