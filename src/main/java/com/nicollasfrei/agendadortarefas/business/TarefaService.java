package com.nicollasfrei.agendadortarefas.business;


import com.nicollasfrei.agendadortarefas.business.dto.TarefasDTO;
import com.nicollasfrei.agendadortarefas.business.mapper.TarefaConverter;
import com.nicollasfrei.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.nicollasfrei.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.nicollasfrei.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.nicollasfrei.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefasRepository tarefasRepository;
    private final TarefaConverter tarefaConverter;
    private final JwtUtil jwtUtil;

    public TarefasDTO gravarTarefa(String token, TarefasDTO dto){
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatus(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity entity = tarefaConverter.paraTarefaEntity(dto);

        return tarefaConverter.paraTarefaDTO(
                tarefasRepository.save(entity));
    }

}
