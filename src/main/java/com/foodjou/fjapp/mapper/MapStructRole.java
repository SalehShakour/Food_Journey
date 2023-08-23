package com.foodjou.fjapp.mapper;

import com.foodjou.fjapp.domain.Role;
import com.foodjou.fjapp.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MapStructRole {

    @Mapping(target = "id", ignore = true) // Ignore id when mapping from RoleDTO to Role
    Role roleDtoToRole(RoleDTO roleDTO);

    @Mapping(target = "roleName", source = "roleName") // Map roleName from Role to RoleDTO
    RoleDTO roleToRoleDTO(Role role);
    @Mapping(target = "roleName", source = "role.roleName")
    @Mapping(target = "description", source = "role.description")
    Role updateRoleDtoToRole(RoleDTO roleDTO, Role role);
}
