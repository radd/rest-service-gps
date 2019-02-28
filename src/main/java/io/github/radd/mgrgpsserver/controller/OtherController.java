package io.github.radd.mgrgpsserver.controller;

import io.github.radd.mgrgpsserver.utils.SimulateGPSClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/other/")
public class OtherController {

    @Autowired
    SimulateGPSClients simulateGPSClients;

    @GetMapping("/reset")
    public ResponseEntity<?> resetClients() {
        simulateGPSClients.resetClients();
        return ResponseEntity.ok().body("");
    }

    @GetMapping("/toggle-sim")
    public ResponseEntity<?> toggleSimulation() {
        simulateGPSClients.isActive = !simulateGPSClients.isActive;
        return ResponseEntity.ok().body("");
    }
}
