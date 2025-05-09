package com.nicollasfrei.agendadortarefas.business.mapper;


import com.nicollasfrei.agendadortarefas.business.dto.TarefasDTO;
import com.nicollasfrei.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefaConverter {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "dataEvento", target = "dataEvento")
    @Mapping(source = "dataCriacao", target = "dataCriacao")
    TarefasEntity paraTarefaEntity(TarefasDTO dto);

    TarefasDTO paraTarefaDTO(TarefasEntity entity);

    List<TarefasEntity> paraListaTarefaEntity(List<TarefasDTO> dtos);

    List<TarefasDTO> paraListaTarefasDTOS(List<TarefasEntity> entities);
}
