package fr.medhead.emergency_bed_service.controller;


import fr.medhead.emergency_bed_service.model.SpecialityGroup;
import fr.medhead.emergency_bed_service.service.SpecialityGroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SpecialityGroupController.class)
public class SpecialityGroupControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private SpecialityGroupService service;


    @Test
    public void shouldReturnAllSpecialityGroups() throws Exception {
        SpecialityGroup groupe1 = new SpecialityGroup();
        groupe1.setId(1L);
        groupe1.setName("Chirurgie");

        SpecialityGroup groupe2 = new SpecialityGroup();
        groupe2.setId(2L);
        groupe2.setName("Anesthésie");

        List<SpecialityGroup> mockGroups = Arrays.asList(groupe1, groupe2);

        when(service.getAllGroups()).thenReturn(mockGroups);

        mockMvc.perform(get("/api/speciality-groups")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Chirurgie"))
                .andExpect(jsonPath("$[1].name").value("Anesthésie"));
    }
}
