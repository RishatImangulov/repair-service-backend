
package org.richard.backend.advertisement;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/advertisements")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @GetMapping
    public ResponseEntity<List<AdvertisementDTO>> getAll() {
        List<AdvertisementDTO> ads = advertisementService.getAll();
        return ResponseEntity.ok(ads);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AdvertisementDTO>> search(@RequestParam String titleFragment) {
        List<AdvertisementDTO> results = advertisementService.findAllByTitleFragment(titleFragment);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementDTO> getById(@PathVariable UUID id) {
        AdvertisementDTO advertisementDTO = advertisementService.getById(id);
        return ResponseEntity.ok(advertisementDTO);
    }

    @PostMapping
    public ResponseEntity<AdvertisementDTO> create(@Valid @RequestBody AdvertisementDTO advertisementDTO) {
        var newAd = advertisementService.create(advertisementDTO);
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(advertisementDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(newAd);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdvertisementDTO> update(@PathVariable UUID id,
                                                   @RequestBody AdvertisementDTO advertisementDTO) {
        var updatedAd = advertisementService.update(id, advertisementDTO);
        return ResponseEntity.ok(updatedAd);
    }

    @DeleteMapping("/{id}")
    public void deleteAdvertisement(@PathVariable UUID id) {
        advertisementService.delete(id);
    }
}
