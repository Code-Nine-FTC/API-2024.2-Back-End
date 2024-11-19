package com.codenine.projetotransparencia.controllers;

import com.codenine.projetotransparencia.controllers.dto.MaterialDto;
import com.codenine.projetotransparencia.entities.Bolsista;
import com.codenine.projetotransparencia.entities.Material;
import com.codenine.projetotransparencia.services.MaterialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    // Endpoint para salvar o material
    @PostMapping("/salvar")
    public ResponseEntity<Material> salvarMaterial(@RequestBody @Valid MaterialDto materialDTO) {
        Material material = materialService.salvarMaterial(materialDTO);
        return ResponseEntity.status(201).body(material);
    }
    @GetMapping("/listar")
    public List<Material> listarMaterial() {
        return materialService.listarMaterial();
    }
    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Material> buscarMaterialPorId(@PathVariable Long id) {
        Optional<Material> material = materialService.buscarMaterialPorId(id);
        if (material.isPresent()) {
            return ResponseEntity.ok(material.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
