
package org.richard.backend.advertisement;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.richard.backend.exception.NotFoundEntityByUuid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/advertisements")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @GetMapping
    public ResponseEntity<List<Advertisement>> getAllAdvertisements() {
        List<AdvertisementDTO> ads = advertisementService.getAdvertisements();
        return ResponseEntity.ok(ads);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AdvertisementDTO>> searchAdvertisements(@RequestParam String titleFragment) {
        List<AdvertisementDTO> results = advertisementService.searchAdvertisementsByTitleFragment(titleFragment);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Advertisement> getAdvertisementById(@PathVariable UUID id) {
        AdvertisementDTO advertisement = advertisementService.getAdvertisementById(id)
                .orElseThrow(() -> new NotFoundEntityByUuid("Advertisement", id.toString()));
        return ResponseEntity.ok(advertisement);
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
