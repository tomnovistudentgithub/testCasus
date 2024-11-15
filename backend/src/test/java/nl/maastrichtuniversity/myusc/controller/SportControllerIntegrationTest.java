package nl.maastrichtuniversity.myusc.controller;


import nl.maastrichtuniversity.myusc.dtos.SportDto;
import nl.maastrichtuniversity.myusc.model.Sport;
import nl.maastrichtuniversity.myusc.model.SportType;
import nl.maastrichtuniversity.myusc.repository.EventRepository;
import nl.maastrichtuniversity.myusc.repository.SportRepository;
import nl.maastrichtuniversity.myusc.service.SportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SportControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SportRepository sportRepository;

    @Autowired
    private SportService sportService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private SportDto sportDto;
    private List<Sport> addedSports = new ArrayList<>();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        sportDto = new SportDto("Basketball", "A team sport", SportType.SPORTS);
        Sport sport = sportRepository.save(new Sport(sportDto.getName(), sportDto.getDescription(), sportDto.getSportType()));
        addedSports.add(sport);
    }

    @AfterEach
    public void cleanup() {
        for (Sport sport : addedSports) {
            sportService.deleteSport(sport.getId());
        }
        addedSports.clear();
    }

    @Test
    public void testAddSport() throws Exception {
        mockMvc.perform(post("/api/sports/addSport")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Basketball\",\"description\":\"A team sport\",\"sportType\":\"SPORTS\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteSport() throws Exception {
        Long sportId = sportRepository.findAll().get(0).getId();
        boolean hasRelatedEvents = eventRepository.findBySportId(sportId).isPresent();

        if (!hasRelatedEvents) {
            mockMvc.perform(delete("/api/sports/deleteSport/" + sportId))
                    .andExpect(status().isOk());
        } else {
            mockMvc.perform(delete("/api/sports/deleteSport/" + sportId))
                    .andExpect(status().isBadRequest());
        }
    }
}