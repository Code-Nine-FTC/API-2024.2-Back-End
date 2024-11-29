//package com.codenine.projetotransparencia.services;
//
//import com.codenine.projetotransparencia.entities.Gasto;
//import com.codenine.projetotransparencia.entities.Receita;
//import com.codenine.projetotransparencia.entities.PrestacaoContas;
//import com.codenine.projetotransparencia.entities.Documento;
//import com.codenine.projetotransparencia.repository.PrestacaoContasRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class PrestacaoContasService {
//
//    @Autowired
//    private PrestacaoContasRepository prestacaoContasRepository;  // Repositório para Prestação de Contas
//
//    // Função que registra a prestação de contas tanto para gasto quanto para receita
//    public void registrarPrestacaoContas(Gasto gasto, Receita receita) {
//        // Se o gasto for não nulo, registra a prestação de contas como gasto
//        if (gasto != null) {
//            registrarPrestacaoContasGasto(gasto);
//        }
//
//        // Se a receita for não nula, registra a prestação de contas como receita
//        if (receita != null) {
//            registrarPrestacaoContasReceita(receita);
//        }
//    }
//
//    private void registrarPrestacaoContasGasto(Gasto gasto) {
//        PrestacaoContas prestacaoContas = new PrestacaoContas();
//        prestacaoContas.setProjeto(gasto.getProjeto());
//        prestacaoContas.setNome(gasto.getFornecedor());
//        prestacaoContas.setDocumento(gasto.getDocumento());
//        prestacaoContas.setTipoDocumento(gasto.getTipoDocumento());
//        prestacaoContas.setData(gasto.getData());
//        prestacaoContas.setTipoPrestacao("gasto");  // Define como "gasto"
//        prestacaoContas.setMaterial(gasto.getMaterial());
//        prestacaoContas.setValor(gasto.getValor());
//
//        // Verificar se o gasto possui anexos (nota fiscal ou outros documentos)
//        if (!gasto.getNotaFiscal().isEmpty()) {
//            List<Documento> anexos = new ArrayList<>();
//            for (Documento doc : gasto.getNotaFiscal()) {
//                anexos.add(doc);  // Adicionar os anexos à lista
//            }
//            prestacaoContas.setAnexos(anexos);
//        }
//
//        // Salvar a prestação de contas para o gasto
//        prestacaoContasRepository.save(prestacaoContas);
//    }
//
//    private void registrarPrestacaoContasReceita(Receita receita) {
//        PrestacaoContas prestacaoContas = new PrestacaoContas();
//        prestacaoContas.setProjeto(receita.getProjeto());
//        prestacaoContas.setNome(receita.getParceiro());
//        prestacaoContas.setDocumento(receita.getDocumento());
//        prestacaoContas.setTipoDocumento(receita.getTipoDocumento());
//        prestacaoContas.setData(receita.getData());
//        prestacaoContas.setTipoPrestacao("receita");  // Define como "receita"
//        prestacaoContas.setValor(receita.getValor());
//
//        // Verificar se a receita possui anexos (rubricas ou outros documentos)
//        if (!receita.getRubrica().isEmpty()) {
//            List<Documento> anexos = new ArrayList<>();
//            for (Documento doc : receita.getRubrica()) {
//                anexos.add(doc);  // Adicionar os anexos à lista
//            }
//            prestacaoContas.setAnexos(anexos);
//        }
//
//
//        prestacaoContasRepository.save(prestacaoContas);
//    }
//
//
//    public List<PrestacaoContas> listarPrestacaoContas() {
//        return prestacaoContasRepository.findAll();
//    }
//
//
//    public List<PrestacaoContas> listarPrestacaoContasPorTituloProjeto(String tituloProjeto) {
//        return prestacaoContasRepository.findByProjetoTituloContainingIgnoreCase(tituloProjeto);
//    }
//
//
//    public PrestacaoContas visualizarPrestacaoContas(Long id) {
//        return prestacaoContasRepository.findById(id).orElse(null);
//    }
//}
