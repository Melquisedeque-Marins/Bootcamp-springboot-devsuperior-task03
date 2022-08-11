package com.devsuperior.bds04.services;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repository.CityRepository;
import com.devsuperior.bds04.repository.EventRepository;
import com.devsuperior.bds04.repository.RoleRepository;
import com.devsuperior.bds04.services.exception.DatabaseException;
import com.devsuperior.bds04.services.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private EventRepository repository;

    @Transactional(readOnly = true)
    public List<EventDTO> findAll(){
       return repository.findAll(Sort.by("name")).stream().map(Event -> new EventDTO(Event)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EventDTO findById(Long id){
        Optional<Event> Event = repository.findById(id);
        EventDTO dto = new EventDTO(Event.orElseThrow(() -> new ResourceNotFoundException("Resource with id " + id + "not found")));
        return dto;
    }

    @Transactional
    public EventDTO insert(EventDTO dto){
        Event event = new Event();
        copyDtoToEntity(dto, event);
        event = repository.save(event);
        return new EventDTO(event);
    }

    public void deleteById(Long id){
        try{
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(EventDTO dto, Event entity) {
        entity.setName(dto.getName());
        entity.setDate(dto.getDate());
        entity.setUrl(dto.getUrl());
        entity.setCity(new City(dto.getId(), null));
        Optional<City> city = cityRepository.findById(dto.getCityId());
        City c = city.orElseThrow(() -> new ResourceNotFoundException("city id does not exist"));
        entity.setCity(c);
    }

}
