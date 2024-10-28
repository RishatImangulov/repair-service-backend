package org.richard.backend.clientstatus;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/client-statuses")
@RequiredArgsConstructor
public class ClientStatusController {

    private final ClientStatusService clientStatusService;

    @GetMapping
    public ResponseEntity<List<ClientStatusDTO>> getAllClientStatuses() {
        List<ClientStatusDTO> clientStatuses = clientStatusService.getClientStatuses();
        return ResponseEntity.ok(clientStatuses);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ClientStatusDTO>> searchClientStatuses(@RequestParam String titleFragment) {
        List<ClientStatusDTO> results = clientStatusService.searchClientStatusesByTitleFragment(titleFragment);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientStatusDTO> getClientStatusById(@PathVariable UUID id) {
        ClientStatusDTO clientStatusDTO = clientStatusService.getClientStatusById(id);
        return ResponseEntity.ok(clientStatusDTO);
    }

    @PostMapping
    public ResponseEntity<String> createClientStatus(@Valid @RequestBody ClientStatusDTO clientStatusDTO) {
        clientStatusService.createClientStatus(clientStatusDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Client status created successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateClientStatus(@PathVariable UUID id,
                                                     @RequestBody ClientStatusDTO clientStatusDTO) {
        clientStatusService.updateClientStatus(id, clientStatusDTO);
        return ResponseEntity.ok("Client status updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientStatus(@PathVariable UUID id) {
        clientStatusService.deleteClientStatus(id);
        return ResponseEntity.ok().build();
    }
}
