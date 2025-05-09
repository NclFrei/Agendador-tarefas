package com.nicollasfrei.agendadortarefas.business;


import com.nicollasfrei.agendadortarefas.business.dto.TarefasDTO;
import com.nicollasfrei.agendadortarefas.business.mapper.TarefaConverter;
import com.nicollasfrei.agendadortarefas.business.mapper.TarefaUpdateConverter;
import com.nicollasfrei.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.nicollasfrei.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.nicollasfrei.agendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.nicollasfrei.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.nicollasfrei.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefasRepository tarefasRepository;
    private final TarefaConverter tarefaConverter;
    private final JwtUtil jwtUtil;
    private final TarefaUpdateConverter tarefaUpdateConverter;

    public TarefasDTO gravarTarefa(String token, TarefasDTO dto){
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatus(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity entity = tarefaConverter.paraTarefaEntity(dto);

        return tarefaConverter.paraTarefaDTO(
                tarefasRepository.save(entity));
    }

    public List<TarefasDTO> buscaTarefasAgendadadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        return  tarefaConverter.paraListaTarefasDTOS(
                tarefasRepository.findByDataEventoBetween(dataInicial, dataFinal));
    }

    public List<TarefasDTO> buscaTarefasPorEmail(String token){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        List<TarefasEntity> listaTarefas = tarefasRepository.findByEmailUsuario(email);

        return tarefaConverter.paraListaTarefasDTOS(listaTarefas);
    }

    public void deletaTerefaPorId(String id){
        try{
            tarefasRepository.deleteById(id);
        } catch (ResourceNotFoundException e ){
            throw new ResourceNotFoundException("Erro ao deletar tarefa por id, id inexistente" + id, e.getCause());
        }
    }


    public TarefasDTO alterarStatus(StatusNotificacaoEnum status, String id){
        try {
            TarefasEntity entity = tarefasRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada" + id));

            entity.setStatus(status);
            return tarefaConverter.paraTarefaDTO(tarefasRepository.save(entity));
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa" + e.getCause());
        }
    }

    public TarefasDTO updateTarefas(TarefasDTO dto,String id){
        try{
            TarefasEntity entity = tarefasRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada" + id));
            tarefaUpdateConverter.updateTarefas(dto, entity);
            return  tarefaConverter.paraTarefaDTO(tarefasRepository.save(entity));
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa" + e.getCause());
        }
    }
}
