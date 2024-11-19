package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.entities.Bolsista;
import com.codenine.projetotransparencia.entities.ClassificacaoDemanda;
import com.codenine.projetotransparencia.controllers.dto.MaterialDto;
import com.codenine.projetotransparencia.entities.Gasto;
import com.codenine.projetotransparencia.entities.Material;
import com.codenine.projetotransparencia.repository.MateriaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialService {

    @Autowired
    private MateriaisRepository materiaisRepository;


    @Autowired
    private GastoService gastoService; // Injeção do serviço de Gasto

    public Material salvarMaterial(MaterialDto materialDTO) {
        // Converter DTO para entidade
        Material material = new Material();
        material.setNome(materialDTO.getNome());
        material.setValor(materialDTO.getValor());
        material.setFornecedor(materialDTO.getFornecedor());
        material.setFornecedorEmail(materialDTO.getFornecedorEmail());
        material.setFornecedorTelefone(materialDTO.getFornecedorTelefone());
        material.setStatusUtilizacao(materialDTO.getStatusUtilizacao());

        // Se precisar associar um Gasto
        if (materialDTO.getGastoId() != null) {
            Gasto gasto = gastoService.buscarGastoPorId(materialDTO.getGastoId()); // Método corrigido
            material.setGasto(gasto);
        }

        // Salvar no banco de dados
        return materiaisRepository.save(material);
    }

    public Optional<Material> buscarMaterialPorId(Long id) {
        return materiaisRepository.findById(id);
    }

    public List<Material> listarMaterial() {
        return materiaisRepository.findAll();
    }
}