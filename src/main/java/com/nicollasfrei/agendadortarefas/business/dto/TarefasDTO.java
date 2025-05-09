package com.nicollasfrei.agendadortarefas.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nicollasfrei.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TarefasDTO {

    private String id;
    private String nomeTarefa;
    private String Descricao;
    private LocalDateTime dataCriacao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd--MM-yyyy HH:mm:ss")
    private LocalDateTime dataEvento;
    private String emailUsuario;
    private LocalDateTime dataAlteracao;
    private StatusNotificacaoEnum status;

}
