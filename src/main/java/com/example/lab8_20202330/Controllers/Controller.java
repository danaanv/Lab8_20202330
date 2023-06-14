package com.example.lab8_20202330.Controllers;

import com.example.lab8_20202330.Dtos.CantidadTicketsPorUsuario;
import com.example.lab8_20202330.Entitys.Evento;
import com.example.lab8_20202330.Entitys.Usuario;
import com.example.lab8_20202330.Repositorys.EventoRepository;
import com.example.lab8_20202330.Repositorys.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Controller
public class Controller {

    final EventoRepository eventoRepository;
    final UsuarioRepository usuarioRepository;

    public Controller(EventoRepository eventoRepository, UsuarioRepository usuarioRepository) {
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/evento")
    public List<Evento> listarEventos(){ return eventoRepository.findAll();}

    @GetMapping("/evento/{id}")
    public ResponseEntity<HashMap<String,Object>> obtenerEventoPorId(@PathVariable("id") String idStr) {
        HashMap<String,Object> responseJson = new HashMap<>();

        try {
            int id = Integer.parseInt(idStr);
            java.util.Optional<Evento> optionalEvento = eventoRepository.findById(id);
            if (optionalEvento.isPresent()) {
                responseJson.put("result","success");
                responseJson.put("evento",optionalEvento.get());
                return ResponseEntity.ok(responseJson);
            } else {
                responseJson.put("msg","Evento no encontrado");
            }
        } catch (NumberFormatException e) {
            responseJson.put("msg","el ID debe ser un número entero positivo");
        }
        return ResponseEntity.badRequest().body(responseJson);
    }

    @PostMapping("/evento")
    public ResponseEntity<HashMap<String, Object>> guardarEvento(
            @RequestBody Evento evento,
            @RequestParam(value = "fetchId", required = false) boolean fetchId) {

        HashMap<String, Object> responseJson = new HashMap<>();

        eventoRepository.save(evento);
        if(fetchId){
            responseJson.put("id",evento.getId());
        }
        responseJson.put("estado","creado");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,String>> gestionException(HttpServletRequest request){
        HashMap<String,String> responseMap = new HashMap<>();
        if(request.getMethod().equals("POST")){
            responseMap.put("estado","error");
            responseMap.put("msg","Debe enviar un evento");
        }
        return ResponseEntity.badRequest().body(responseMap);
    }

    @PutMapping(value = "/evento")
    public ResponseEntity<HashMap<String,Object>> actualizarEvento(@RequestBody Evento evento) {

        HashMap<String, Object> responseMap = new HashMap<>();

        if (evento.getId() != null && evento.getId() > 0) {
            Optional<Evento> opt = eventoRepository.findById(evento.getId());
            if (opt.isPresent()) {
                eventoRepository.save(evento);
                responseMap.put("estado", "actualizado");
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "El evento a actualizar no existe");
                return ResponseEntity.badRequest().body(responseMap);
            }
        } else {
            responseMap.put("estado", "error");
            responseMap.put("msg", "Debe enviar un ID");
            return ResponseEntity.badRequest().body(responseMap);
        }
    }

    @DeleteMapping(value = "/evento/{id}")
    public ResponseEntity<HashMap<String, Object>> borrarEvento(@PathVariable("id") String idStr) {

        HashMap<String, Object> responseMap = new HashMap<>();

        try {
            int id = Integer.parseInt(idStr);
            if (eventoRepository.existsById(id)) {
                eventoRepository.deleteById(id);
                responseMap.put("estado", "borrado exitoso");
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "no se encontró el evento con id: " + id);
                return ResponseEntity.badRequest().body(responseMap);
            }
        } catch (NumberFormatException ex) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "El ID debe ser un número");
            return ResponseEntity.badRequest().body(responseMap);
        }
    }

    @GetMapping(value = "/cantidadTicketsUsuario")
    public ResponseEntity<HashMap<String, Object>> obtenerTicketsPorUsuario(){

        HashMap<String, Object> responseMap = new HashMap<>();

        List<CantidadTicketsPorUsuario> cantidadTicketsPorUsuarioList = usuarioRepository.obtenerCantidadTicketsPorUsuario();
        try {
            /*
            int tickets = cantidadTicketsPorUsuarioList.getCantidadTickets();
            Usuario usuario = cantidadTicketsPorUsuarioList.getUsuario();
            responseMap.put("cantidad", tickets);
            responseMap.put("correo", usuario.getCorreo());

             */
            return ResponseEntity.ok(responseMap);
        } catch (NumberFormatException ex){
            return null;
        }

    }
}
