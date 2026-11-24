package org.springtech.lab24november.service;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springtech.lab24november.Exceptions.DuplicateResourceException;
import org.springtech.lab24november.Exceptions.ResourceNotFoundException;
import org.springtech.lab24november.dto.UserRequest;
import org.springtech.lab24november.dto.UserResponse;
import org.springtech.lab24november.entity.Users;
import org.springtech.lab24november.repository.UsersRepository;

import java.util.List;


@Service
public class UsersService {
    private final UsersRepository repository;

    public UsersService(UsersRepository repository){
        this.repository = repository;
    }

    private UserResponse MapToResponse(Users entity){
        return new UserResponse(
                entity.getId(),
                entity.getName(),
                entity.getEmail()
        );
    }

    private Users MapToEntity(UserRequest request){
        Users user = new Users();
        user.setName(request.name());
        user.setEmail(request.email());

        return user;
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers(){
        return repository.findAll().stream().map(this::MapToResponse).toList();
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(long id) throws ResourceNotFoundException {
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("User with Id "+id+" does not exist.");
        }
        Users user = repository.findById(id).get();
        return MapToResponse(user);
    }

    @Transactional
    public void createUser(UserRequest request) throws DuplicateResourceException {
        if (repository.existsByEmail(request.email())){
            throw new DuplicateResourceException("A user with email "+request.email()+" already exists.");
        }
        Users user = MapToEntity(request);
        repository.save(user);
    }

    @Transactional
    public void updateUser(long id, UserRequest updatedUser) throws ResourceNotFoundException {
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("User with Id "+id+" does not exist.");
        }
        Users user = MapToEntity(updatedUser);
        user.setId(id);
        repository.save(user);
    }

    @Transactional
    public void deleteUserById(long id) throws ResourceNotFoundException{
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("User with Id "+id+" does not exist.");
        }
        repository.deleteById(id);
    }
}
