package org.richard.backend.advertisement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.richard.backend.exception.NotFoundEntityByUuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AdvertisementController.class)
@ExtendWith(MockitoExtension.class)
public class AdvertisementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdvertisementService advertisementService;

    private AdvertisementDTO sampleDTO;
    private UUID sampleId;

    @BeforeEach
    public void setUp() {
        sampleId = UUID.randomUUID();
        sampleDTO = new AdvertisementDTO(sampleId, "Sample Title", "Sample Description");
    }

    // Helper method to convert objects to JSON strings
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetAll() throws Exception {
        List<AdvertisementDTO> ads = List.of(sampleDTO);

        when(advertisementService.getAll()).thenReturn(ads);

        mockMvc.perform(get("/api/advertisements"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Sample Title"))
                .andExpect(jsonPath("$[0].description").value("Sample Description"));

        verify(advertisementService, times(1)).getAll();
    }
    @Test
    public void testSearch() throws Exception {
        List<AdvertisementDTO> results = List.of(sampleDTO);

        when(advertisementService.findAllByTitleFragment("Sample")).thenReturn(results);

        mockMvc.perform(get("/api/advertisements/search")
                        .param("titleFragment", "Sample"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Sample Title"))
                .andExpect(jsonPath("$[0].description").value("Sample Description"));

        verify(advertisementService, times(1)).findAllByTitleFragment("Sample");
    }
    @Test
    public void testGetById_Success() throws Exception {
        when(advertisementService.getById(sampleId)).thenReturn(sampleDTO);

        mockMvc.perform(get("/api/advertisements/{id}", sampleId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Sample Title"))
                .andExpect(jsonPath("$.description").value("Sample Description"));

        verify(advertisementService, times(1)).getById(sampleId);
    }

    @Test
    public void testGetById_NotFound() throws Exception {
        when(advertisementService.getById(sampleId)).thenThrow(new NotFoundEntityByUuid("Advertisement", sampleId.toString()));

        mockMvc.perform(get("/api/advertisements/{id}", sampleId))
                .andExpect(status().isNotFound());

        verify(advertisementService, times(1)).getById(sampleId);
    }
    @Test
    public void testCreate_Success() throws Exception {
        doNothing().when(advertisementService).create(any(AdvertisementDTO.class));

        mockMvc.perform(post("/api/advertisements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(sampleDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Advertisement created successfully."));

        verify(advertisementService, times(1)).create(any(AdvertisementDTO.class));
    }

    @Test
    public void testCreate_InvalidInput() throws Exception {
        AdvertisementDTO invalidDTO = new AdvertisementDTO(sampleId, "", "Sample Description");

        mockMvc.perform(post("/api/advertisements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidDTO)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testUpdate_Success() throws Exception {
        doNothing().when(advertisementService).update(eq(sampleId), any(AdvertisementDTO.class));

        mockMvc.perform(put("/api/advertisements/{id}", sampleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(sampleDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Advertisement updated successfully."));

        verify(advertisementService, times(1)).update(eq(sampleId), any(AdvertisementDTO.class));
    }

    @Test
    public void testUpdate_NotFound() throws Exception {
        doThrow(new NotFoundEntityByUuid("Advertisement", sampleId.toString()))
                .when(advertisementService).update(eq(sampleId), any(AdvertisementDTO.class));

        mockMvc.perform(put("/api/advertisements/{id}", sampleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(sampleDTO)))
                .andExpect(status().isNotFound());
    }

}
