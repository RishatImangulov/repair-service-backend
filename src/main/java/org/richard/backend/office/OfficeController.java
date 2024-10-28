package org.richard.backend.office;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/offices")
@RequiredArgsConstructor
public class OfficeController {
    private final OfficeService officeService;

    @GetMapping
    public ResponseEntity<List<OfficeDTO>> getAll() {
        List<OfficeDTO> ads = officeService.getAll();
        return ResponseEntity.ok(ads);
    }

    @GetMapping("/search")
    public ResponseEntity<List<OfficeDTO>> search(@RequestParam String titleFragment) {
        List<OfficeDTO> results = officeService.findAllByTitleFragment(titleFragment);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfficeDTO> getById(@PathVariable UUID id) {
        OfficeDTO officeDTO = officeService.getById(id);
        return ResponseEntity.ok(officeDTO);
    }

    @PostMapping
    public ResponseEntity<OfficeDTO> create(@Valid @RequestBody OfficeDTO officeDTO) {
        var newAd = officeService.create(officeDTO);
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(officeDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(newAd);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfficeDTO> update(@PathVariable UUID id,
                                                   @RequestBody OfficeDTO officeDTO) {
        var updatedAd = officeService.update(id, officeDTO);
        return ResponseEntity.ok(updatedAd);
    }

    @DeleteMapping("/{id}")
    public void deleteOffice(@PathVariable UUID id) {
        officeService.delete(id);
    }
}
