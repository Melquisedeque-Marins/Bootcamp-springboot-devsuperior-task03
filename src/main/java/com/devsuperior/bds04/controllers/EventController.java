package com.devsuperior.bds04.controllers;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/Events")
public class EventController {

    @Autowired
    private EventService service;

    @GetMapping
    public ResponseEntity<List<EventDTO>> findAll(){
        List<EventDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> findById(@PathVariable Long id){
        EventDTO Event = service.findById(id);
        return ResponseEntity.ok().body(Event);
    }

    @PostMapping
    public ResponseEntity<EventDTO> insert(@RequestBody EventDTO dto){
        EventDTO Event = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(Event.getId()).toUri();
        return ResponseEntity.created(uri).body(Event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EventDTO> delete(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
