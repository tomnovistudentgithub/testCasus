package nl.maastrichtuniversity.myusc.service;

import nl.maastrichtuniversity.myusc.entities.User;
import nl.maastrichtuniversity.myusc.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1L);
        user.setUserName("testuser");
    }

    @Test
    public void testGetUserById() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserById(1L);

        // Assert
        assertEquals("testuser", result.getUserName());
    }

    @Test
    public void testUpdateUser() {
        // Arrange
        user.setUserName("updateduser");
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User result = userService.updateUser(user);

        // Assert
        assertEquals("updateduser", result.getUserName());
    }

    @Test
    public void testGetUserByUserName() {
        // Arrange
        when(userRepository.findByUserName("testuser")).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserByUserName("testuser");

        // Assert
        assertEquals("testuser", result.getUserName());
    }

    @Test
    public void testSaveUserPicture() throws IOException {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(file.getBytes()).thenReturn(new byte[]{1, 2, 3});
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        userService.saveUserPicture(1L, file);

        // Assert
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDeleteUserPicture() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        userService.deleteUserPicture(1L);

        // Assert
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetUserPicture() {
        // Arrange
        user.setPicture(new byte[]{1, 2, 3});
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        byte[] picture = userService.getUserPicture(1L);

        // Assert
        assertArrayEquals(new byte[]{1, 2, 3}, picture);
    }

    @Test
    public void testGetUserByIdNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userService.getUserById(1L));
    }

    @Test
    public void testGetUser() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUser(1L);

        // Assert
        assertEquals("testuser", result.getUserName());
    }
}


