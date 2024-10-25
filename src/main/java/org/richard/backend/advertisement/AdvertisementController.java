
package org.richard.backend.advertisement;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/advertisements")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @GetMapping
    public ResponseEntity<List<AdvertisementDTO>> getAllAdvertisements() {
        List<AdvertisementDTO> ads = advertisementService.getAdvertisements();
        return ResponseEntity.ok(ads);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AdvertisementDTO>> searchAdvertisements(@RequestParam String titleFragment) {
        List<AdvertisementDTO> results = advertisementService.searchAdvertisementsByTitleFragment(titleFragment);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementDTO> getAdvertisementById(@PathVariable UUID id) {
        AdvertisementDTO advertisementDTO = advertisementService.getAdvertisementById(id);
        return ResponseEntity.ok(advertisementDTO);
    }

    @PostMapping
    public ResponseEntity<String> createAdvertisement(@Valid @RequestBody AdvertisementDTO advertisementDTO) {
        advertisementService.createAdvertisement(advertisementDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Advertisement created successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAdvertisement(@PathVariable UUID id,
                                                      @RequestBody AdvertisementDTO advertisementDTO) {
        advertisementService.updateAdvertisement(id, advertisementDTO);
        return ResponseEntity.ok("Advertisement updated successfully.");
    }
}
