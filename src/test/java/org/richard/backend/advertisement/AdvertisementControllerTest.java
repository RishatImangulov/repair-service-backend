package org.richard.backend.advertisement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.richard.backend.exception.NotFoundEntityByUuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
    public void testGetAllAdvertisements() throws Exception {
        List<AdvertisementDTO> ads = List.of(sampleDTO);

        when(advertisementService.getAdvertisements()).thenReturn(ads);

        mockMvc.perform(get("/api/advertisements"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Sample Title"))
                .andExpect(jsonPath("$[0].description").value("Sample Description"));

        verify(advertisementService, times(1)).getAdvertisements();
    }
    @Test
    public void testSearchAdvertisements() throws Exception {
        List<AdvertisementDTO> results = List.of(sampleDTO);

        when(advertisementService.searchAdvertisementsByTitleFragment("Sample")).thenReturn(results);

        mockMvc.perform(get("/api/advertisements/search")
                        .param("titleFragment", "Sample"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Sample Title"))
                .andExpect(jsonPath("$[0].description").value("Sample Description"));

        verify(advertisementService, times(1)).searchAdvertisementsByTitleFragment("Sample");
    }
    @Test
    public void testGetAdvertisementById_Success() throws Exception {
        when(advertisementService.getAdvertisementById(sampleId)).thenReturn(sampleDTO);

        mockMvc.perform(get("/api/advertisements/{id}", sampleId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Sample Title"))
                .andExpect(jsonPath("$.description").value("Sample Description"));

        verify(advertisementService, times(1)).getAdvertisementById(sampleId);
    }

    @Test
    public void testGetAdvertisementById_NotFound() throws Exception {
        when(advertisementService.getAdvertisementById(sampleId)).thenThrow(new NotFoundEntityByUuid("Advertisement", sampleId.toString()));

        mockMvc.perform(get("/api/advertisements/{id}", sampleId))
                .andExpect(status().isNotFound());

        verify(advertisementService, times(1)).getAdvertisementById(sampleId);
    }
    @Test
    public void testCreateAdvertisement_Success() throws Exception {
        doNothing().when(advertisementService).createAdvertisement(any(AdvertisementDTO.class));

        mockMvc.perform(post("/api/advertisements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(sampleDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Advertisement created successfully."));

        verify(advertisementService, times(1)).createAdvertisement(any(AdvertisementDTO.class));
    }

    @Test
    public void testCreateAdvertisement_InvalidInput() throws Exception {
        AdvertisementDTO invalidDTO = new AdvertisementDTO(sampleId, "", "Sample Description");

        mockMvc.perform(post("/api/advertisements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidDTO)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testUpdateAdvertisement_Success() throws Exception {
        doNothing().when(advertisementService).updateAdvertisement(eq(sampleId), any(AdvertisementDTO.class));

        mockMvc.perform(put("/api/advertisements/{id}", sampleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(sampleDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Advertisement updated successfully."));

        verify(advertisementService, times(1)).updateAdvertisement(eq(sampleId), any(AdvertisementDTO.class));
    }

    @Test
    public void testUpdateAdvertisement_NotFound() throws Exception {
        doThrow(new NotFoundEntityByUuid("Advertisement", sampleId.toString()))
                .when(advertisementService).updateAdvertisement(eq(sampleId), any(AdvertisementDTO.class));

        mockMvc.perform(put("/api/advertisements/{id}", sampleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(sampleDTO)))
                .andExpect(status().isNotFound());
    }

}
