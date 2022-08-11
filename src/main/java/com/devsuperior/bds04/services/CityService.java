package com.devsuperior.bds04.services;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.dto.RoleDTO;
import com.devsuperior.bds04.entities.Role;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.repository.RoleRepository;
import com.devsuperior.bds04.repository.CityRepository;
import com.devsuperior.bds04.services.exception.DatabaseException;
import com.devsuperior.bds04.services.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CityRepository repository;

    public List<CityDTO> findAll(){
       return repository.findAll(Sort.by("name")).stream().map(City -> new CityDTO(City)).collect(Collectors.toList());
    }

    public CityDTO findById(Long id){
        Optional<City> City = repository.findById(id);
        CityDTO dto = new CityDTO(City.orElseThrow(() -> new ResourceNotFoundException("Resource with id " + id + "not found")));
        return dto;
    }

    public CityDTO insert(CityDTO dto){
        City city = new City();
        city.setName(dto.getName());
        city = repository.save(city);
        return new CityDTO(city);
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

}
