package com.epam.backend.core.rest.controller;

import com.epam.backend.core.rest.model.User;
import com.epam.backend.core.rest.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser() {
        User user = new User("username", "password", "role");
        when(userService.saveUser(any(User.class))).thenReturn(user);

        ResponseEntity<User> response = userController.createUser(user);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
        verify(userService).saveUser(user);
    }

    @Test
    void getAllUsers() {
        List<User> users = Arrays.asList(new User("username1", "password1", "role1"),
                new User("username2", "password2", "role2"));
        when(userService.findAllUsers()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(users, response.getBody());
        verify(userService).findAllUsers();
    }

    @Test
    void getUserById_found() {
        User user = new User("username", "password", "role");
        when(userService.findUserById(1L)).thenReturn(user);

        ResponseEntity<User> response = userController.getUserById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
        verify(userService).findUserById(1L);
    }

    @Test
    void getUserById_notFound() {
        when(userService.findUserById(1L)).thenReturn(null);

        ResponseEntity<User> response = userController.getUserById(1L);

        assertEquals(404, response.getStatusCodeValue());
        verify(userService).findUserById(1L);
    }

    @Test
    void updateUser_found() {
        User existingUser = new User("username", "password", "role");
        User newUserDetails = new User( "newUsername", "newPassword", "newRole");
        User updatedUser = new User("newUsername", "newPassword", "newRole");

        when(userService.findUserById(1L)).thenReturn(existingUser);
        when(userService.saveUser(any(User.class))).thenReturn(updatedUser);

        ResponseEntity<User> response = userController.updateUser(1L, newUserDetails);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedUser, response.getBody());
        verify(userService).saveUser(existingUser);
    }

    @Test
    void updateUser_notFound() {
        when(userService.findUserById(1L)).thenReturn(null);

        ResponseEntity<User> response = userController.updateUser(1L, new User());

        assertEquals(404, response.getStatusCodeValue());
        verify(userService, never()).saveUser(any(User.class));
    }

    @Test
    void deleteUser_found() {
        User user = new User("username", "password", "role");
        when(userService.findUserById(1L)).thenReturn(user);

        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertEquals(200, response.getStatusCodeValue());
        verify(userService).deleteUser(1L);
    }

    @Test
    void deleteUser_notFound() {
        when(userService.findUserById(1L)).thenReturn(null);

        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertEquals(404, response.getStatusCodeValue());
        verify(userService, never()).deleteUser(1L);
    }
}