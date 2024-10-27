package org.richard.backend.advertisement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.richard.backend.advertisement.Advertisement;
import org.richard.backend.advertisement.AdvertisementDTO;
import org.richard.backend.advertisement.AdvertisementMapper;
import org.richard.backend.exception.NullDataMappingException;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AdvertisementMapperTest {

    private AdvertisementMapper advertisementMapper;
    private AdvertisementDTO sampleDTO;
    private Advertisement sampleEntity;
    private UUID sampleId;

    @BeforeEach
    public void setUp() {
        advertisementMapper = new AdvertisementMapper();
        sampleId = UUID.randomUUID();

        sampleDTO = AdvertisementDTO.builder()
                .id(sampleId)
                .title("Sample Title")
                .description("Sample Description")
                .build();

        sampleEntity = Advertisement.builder()
                .id(sampleId)
                .title("Sample Title")
                .description("Sample Description")
                .build();
    }

    // 1. Test for `toEntity` Method
    @Test
    public void testToEntity_Success() {
        Advertisement result = advertisementMapper.toEntity(sampleDTO);
        assertNotNull(result);
        assertEquals(sampleId, result.getId());
        assertEquals("Sample Title", result.getTitle());
        assertEquals("Sample Description", result.getDescription());
    }

    @Test
    public void testToEntity_NullDTO_ThrowsException() {
        assertThrows(NullDataMappingException.class, () -> advertisementMapper.toEntity(null));
    }

    // 2. Test for `toDTO` Method
    @Test
    public void testToDTO_Success() {
        AdvertisementDTO result = advertisementMapper.toDTO(sampleEntity);
        assertNotNull(result);
        assertEquals(sampleId, result.getId());
        assertEquals("Sample Title", result.getTitle());
        assertEquals("Sample Description", result.getDescription());
    }

    @Test
    public void testToDTO_NullEntity_ThrowsException() {
        assertThrows(NullDataMappingException.class, () -> advertisementMapper.toDTO(null));
    }

    // 3. Test for `toDTOList` Method
    @Test
    public void testToDTOList_Success() {
        List<Advertisement> entities = List.of(sampleEntity);
        List<AdvertisementDTO> result = advertisementMapper.toDTOList(entities);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Sample Title", result.get(0).getTitle());
        assertEquals("Sample Description", result.get(0).getDescription());
    }

    @Test
    public void testToDTOList_EmptyList() {
        List<Advertisement> entities = List.of();
        List<AdvertisementDTO> result = advertisementMapper.toDTOList(entities);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // 4. Test for `toEntityList` Method
    @Test
    public void testToEntityList_Success() {
        List<AdvertisementDTO> dtos = List.of(sampleDTO);
        List<Advertisement> result = advertisementMapper.toEntityList(dtos);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Sample Title", result.get(0).getTitle());
        assertEquals("Sample Description", result.get(0).getDescription());
    }

    @Test
    public void testToEntityList_EmptyList() {
        List<AdvertisementDTO> dtos = List.of();
        List<Advertisement> result = advertisementMapper.toEntityList(dtos);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // 5. Test for `updateEntityFromDTO` Method
    @Test
    public void testUpdateEntityFromDTO_Success() {
        AdvertisementDTO updatedDTO = AdvertisementDTO.builder()
                .title("Updated Title")
                .description("Updated Description")
                .build();

        Advertisement updatedEntity = advertisementMapper.updateEntityFromDTO(sampleEntity, updatedDTO);

        assertNotNull(updatedEntity);
        assertEquals("Updated Title", updatedEntity.getTitle());
        assertEquals("Updated Description", updatedEntity.getDescription());
    }

    @Test
    public void testUpdateEntityFromDTO_OnlyTitleUpdated() {
        AdvertisementDTO updatedDTO = AdvertisementDTO.builder()
                .title("Updated Title")
                .build();

        Advertisement updatedEntity = advertisementMapper.updateEntityFromDTO(sampleEntity, updatedDTO);

        assertNotNull(updatedEntity);
        assertEquals("Updated Title", updatedEntity.getTitle());
        assertEquals("Sample Description", updatedEntity.getDescription());
    }

    @Test
    public void testUpdateEntityFromDTO_OnlyDescriptionUpdated() {
        AdvertisementDTO updatedDTO = AdvertisementDTO.builder()
                .description("Updated Description")
                .build();

        Advertisement updatedEntity = advertisementMapper.updateEntityFromDTO(sampleEntity, updatedDTO);

        assertNotNull(updatedEntity);
        assertEquals("Sample Title", updatedEntity.getTitle());
        assertEquals("Updated Description", updatedEntity.getDescription());
    }
}
