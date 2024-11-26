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
            Gasto gasto = gastoService.visualizarGasto(materialDTO.getGastoId()); // Método corrigido
            material.setGasto(gasto);
        }

        // Salvar no banco de dados
        return materiaisRepository.save(material);
    }
    public Material editarMaterial(Long id, MaterialDto materialDTO) {
        Optional<Material> materialOpt = materiaisRepository.findById(id);
        if (materialOpt.isPresent()) {
            Material material = materialOpt.get();

            // Atualizando os dados do material com as informações do DTO
            material.setNome(materialDTO.getNome());
            material.setValor(materialDTO.getValor());
            material.setFornecedor(materialDTO.getFornecedor());
            material.setFornecedorEmail(materialDTO.getFornecedorEmail());
            material.setFornecedorTelefone(materialDTO.getFornecedorTelefone());
            material.setStatusUtilizacao(materialDTO.getStatusUtilizacao());

            // Atualizando o Gasto, se fornecido
            if (materialDTO.getGastoId() != null) {
                Gasto gasto = gastoService.visualizarGasto(materialDTO.getGastoId());
                material.setGasto(gasto);
            }

            return materiaisRepository.save(material); // Salva as alterações
        }
        return null; // Retorna null se o material não for encontrado
    }

    public boolean excluirMaterial(Long id) {
        Optional<Material> materialOpt = materiaisRepository.findById(id);
        if (materialOpt.isPresent()) {
            materiaisRepository.delete(materialOpt.get()); // Exclui o material
            return true; // Retorna verdadeiro se excluído com sucesso
        }
        return false; // Retorna falso se o material não for encontrado
    }


    public Optional<Material> buscarMaterialPorId(Long id) {
        return materiaisRepository.findById(id);
    }

    public List<Material> listarMaterial() {
        return materiaisRepository.findAll();
    }
}