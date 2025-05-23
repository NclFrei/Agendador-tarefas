package com.nicollasfrei.agendadortarefas.controller;


import com.nicollasfrei.agendadortarefas.business.TarefaService;
import com.nicollasfrei.agendadortarefas.business.dto.TarefasDTO;
import com.nicollasfrei.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("*/tarefas")
@RequiredArgsConstructor
public class TarefasController {

    private final TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<TarefasDTO> gravarTarefas(@RequestBody TarefasDTO dto,
                                                    @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(tarefaService.gravarTarefa(token, dto));
    }

    @GetMapping("/eventos")
    public ResponseEntity<List<TarefasDTO>> buscaListaDeTarefasPorPeriodo(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime dataInicial,
                                                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime dataFinal){

        return ResponseEntity.ok(tarefaService.buscaTarefasAgendadadasPorPeriodo(dataInicial, dataFinal));
    }

    @GetMapping
    public ResponseEntity<List<TarefasDTO>> buscaTarefasPorEmail(@RequestHeader("Authorization") String token){
        List<TarefasDTO> tarefas = tarefaService.buscaTarefasPorEmail(token);
        return ResponseEntity.ok(tarefas);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletaTarefaPorId(@RequestParam("id") String id){
        tarefaService.deletaTerefaPorId(id);

        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<TarefasDTO> alteraStatusNotificacao(@RequestParam("status")StatusNotificacaoEnum status,
                                                              @RequestParam("Id") String id){
        return ResponseEntity.ok(tarefaService.alterarStatus(status, id));
    }

    @PutMapping
    public ResponseEntity<TarefasDTO> updateTarefas(@RequestBody TarefasDTO dto,@RequestParam("id") String id){
        return ResponseEntity.ok(tarefaService.updateTarefas(dto, id));
    }

}
