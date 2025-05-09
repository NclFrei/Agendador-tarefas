package com.nicollasfrei.agendadortarefas.business.mapper;


import com.nicollasfrei.agendadortarefas.business.dto.TarefasDTO;
import com.nicollasfrei.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TarefaUpdateConverter {

    void updateTarefas(TarefasDTO dto,@MappingTarget TarefasEntity entity);
}
