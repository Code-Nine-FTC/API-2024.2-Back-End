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
    @PutMapping("/editar/{id}")
    public ResponseEntity<Material> editarMaterial(@PathVariable Long id, @RequestBody @Valid MaterialDto materialDTO) {
        Material material = materialService.editarMaterial(id, materialDTO);
        if (material != null) {
            return ResponseEntity.ok(material); // Retorna o material atualizado
        } else {
            return ResponseEntity.notFound().build();// Retorna 404 se o material não for encontrado
        }
    }
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluirMaterial(@PathVariable Long id) {
        boolean deletado = materialService.excluirMaterial(id);
        if (deletado) {
            return ResponseEntity.noContent().build(); // Retorna 204 No Content se excluído com sucesso
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 se o material não for encontrado
        }
    }

}
