package com.threektechone.resorthub.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterMenuItemsDTO;
import com.threektechone.resorthub.models.ResortMenu;

@Mapper(componentModel = "spring")
public interface ResortMenuMapper {
    
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "category", source = "category")
    ResortMenu toResortMenu(RegisterMenuItemsDTO dto);

    List<ResortMenu> toResortMenuList(List<RegisterMenuItemsDTO> dtos);
}
