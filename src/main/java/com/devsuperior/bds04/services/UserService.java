package com.devsuperior.bds04.services;

import com.devsuperior.bds04.dto.InsertUserDTO;
import com.devsuperior.bds04.dto.RoleDTO;
import com.devsuperior.bds04.dto.ResponseUserDTO;
import com.devsuperior.bds04.entities.Role;
import com.devsuperior.bds04.entities.User;
import com.devsuperior.bds04.repository.RoleRepository;
import com.devsuperior.bds04.repository.UserRepository;
import com.devsuperior.bds04.services.exception.DatabaseException;
import com.devsuperior.bds04.services.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository repository;

    public List<ResponseUserDTO> findAll(){
       return repository.findAll(Sort.by("name")).stream().map(user -> new ResponseUserDTO(user)).collect(Collectors.toList());
    }

    public ResponseUserDTO findById(Long id){
        Optional<User> user = repository.findById(id);
        ResponseUserDTO dto = new ResponseUserDTO(user.orElseThrow(() -> new ResourceNotFoundException("Resource with id " + id + "not found")));
        return dto;
    }

    public ResponseUserDTO insert(InsertUserDTO dto){
        User user = new User();
        convertDtoToUser(dto, user);
        User newUser = repository.save(user);
        return new ResponseUserDTO(user);
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

    private void convertDtoToUser(InsertUserDTO dto, User user){
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.getRoles().clear();
        for (RoleDTO roleDTO : dto.getRolesDTO()) {
            Role role = roleRepository.getOne(roleDTO.getId());
            user.getRoles().add(role);
        }
    }
}
