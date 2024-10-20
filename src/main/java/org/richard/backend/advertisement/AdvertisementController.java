
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

    /**
     * Retrieves all advertisements.
     *
     * @return ResponseEntity containing a list of all advertisements
     */
    @GetMapping
    public ResponseEntity<List<Advertisement>> getAllAdvertisements() {
        List<Advertisement> ads = advertisementService.getAdvertisements();
        return ResponseEntity.ok(ads);
    }

    /**
     * Searches for advertisements by a fragment of the title.
     *
     * @param titleFragment the fragment of the title to search for (can be blank - need check in frontend)
     * @return ResponseEntity containing a list of advertisements that match the search criteria
     */
    @GetMapping("/search")
    public ResponseEntity<List<Advertisement>> searchAdvertisements(@RequestParam String titleFragment) {
        List<Advertisement> results = advertisementService.searchAdvertisementsByTitleFragment(titleFragment);
        return ResponseEntity.ok(results);
    }

    /**
     * Retrieves a specific advertisement by its UUID.
     *
     * @param id the UUID of the advertisement to retrieve
     * @return ResponseEntity containing the advertisement if found, or a NotFoundEntityByUuid exception if not
     */
    @GetMapping("/{id}")
    public ResponseEntity<Advertisement> getAdvertisementById(@PathVariable UUID id) {
        Optional<Advertisement> advertisement = advertisementService.getAdvertisementById(id);
        return advertisement.map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundEntityByUuid("Advertisement", id.toString()));
    }

    /**
     * Creates a new advertisement.
     *
     * @param advertisement the Advertisement object to create
     * @return ResponseEntity with a message indicating successful creation
     */
    @PostMapping
    public ResponseEntity<String> createAdvertisement(@Valid @RequestBody Advertisement advertisement) {
        advertisementService.createAdvertisement(advertisement);
        return ResponseEntity.status(HttpStatus.CREATED).body("Advertisement created successfully.");
    }

    /**
     * Updates an existing advertisement by its UUID.
     *
     * @param id            the UUID of the advertisement to update
     * @param advertisement the updated Advertisement object
     * @return ResponseEntity with a message indicating successful update
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateAdvertisement(@PathVariable UUID id, @RequestBody Advertisement advertisement) {
        advertisementService.updateAdvertisement(id, advertisement);
        return ResponseEntity.ok("Advertisement updated successfully.");
    }
}
