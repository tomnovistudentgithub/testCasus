package nl.maastrichtuniversity.myusc.controller;

import nl.maastrichtuniversity.myusc.entities.User;
import nl.maastrichtuniversity.myusc.service.UserService;
import nl.maastrichtuniversity.myusc.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    private List<User> addedUsers = new ArrayList<>();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        userRepository.deleteAll();

        // Arrange
        User user = new User();
        user.setId(1L);
        user.setUserName("testuser");
        user.setPassword("securepassword");
        userRepository.save(user);
        addedUsers.add(user);

        when(userService.getUser(1L)).thenReturn(user);
    }

    @AfterEach
    public void cleanup() {
        for (User user : addedUsers) {
            userRepository.delete(user);
        }
        addedUsers.clear();
    }


    @Test
    public void testGetUser() throws Exception {

        //act & assert
        mockMvc.perform(get("/api/users/details/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("testuser")); }

    }

