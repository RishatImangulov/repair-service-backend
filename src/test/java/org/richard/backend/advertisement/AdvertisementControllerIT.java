//package org.richard.backend.advertisement;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class AdvertisementControllerIT {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private AdvertisementService advertisementService;
//
//    @Autowired
//    private AdvertisementRepository advertisementRepository;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    public void testCreate() throws Exception {
////        AdvertisementDTO dto = new AdvertisementDTO();
//        // Set DTO fields
//
//        mockMvc.perform(post("/advertisements")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").isNotEmpty());
//    }
//
//    @Test
//    public void testGetById() throws Exception {
//        AdvertisementDTO dto = new AdvertisementDTO();
//        // Set DTO fields, save it via service
//
//        AdvertisementDTO savedDto = advertisementService.create(dto);
//
//        mockMvc.perform(get("/advertisements/" + savedDto.getId()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(savedDto.getId()));
//    }
//
//    @Test
//    public void testUpdate() throws Exception {
//        AdvertisementDTO dto = new AdvertisementDTO();
//        // Set DTO fields, save it via service
//
//        AdvertisementDTO savedDto = advertisementService.create(dto);
//        savedDto.setTitle("Updated Title");
//
//        mockMvc.perform(put("/advertisements/" + savedDto.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(savedDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.title").value("Updated Title"));
//    }
//
//    @Test
//    public void testDeleteAdvertisement() throws Exception {
//        AdvertisementDTO dto = new AdvertisementDTO();
//        // Set DTO fields, save it via service
//
//        AdvertisementDTO savedDto = advertisementService.create(dto);
//
//        mockMvc.perform(delete("/advertisements/" + savedDto.getId()))
//                .andExpect(status().isNoContent());
//
//        assertFalse(advertisementRepository.existsById(savedDto.getId()));
//    }
//}
