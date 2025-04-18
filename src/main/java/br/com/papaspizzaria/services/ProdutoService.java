package br.com.papaspizzaria.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.papaspizzaria.dto.ProdutoDTO;
import br.com.papaspizzaria.entities.Produto;
import br.com.papaspizzaria.repositories.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<ProdutoDTO> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProdutoDTO buscarPorId(Integer id) {
        return produtoRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public ProdutoDTO salvar(ProdutoDTO produtoDTO) {
        Produto produto = new Produto(produtoDTO);
        produto = produtoRepository.save(produto);
        return convertToDTO(produto);
    }

    public ProdutoDTO atualizar(Integer id, ProdutoDTO produtoDTO) {
        return produtoRepository.findById(id)
                .map(existingProduto -> {
                    existingProduto.setDescricao(produtoDTO.getDescricao());
                    existingProduto.setPrecovenda(produtoDTO.getPrecovenda());
                    existingProduto.setUnidade(produtoDTO.getUnidade());
                    existingProduto.setEstoque(produtoDTO.getEstoque());
                    existingProduto.setGrupos(produtoDTO.getGrupos());
                    existingProduto.setImage(produtoDTO.getImage());
                    produtoRepository.save(existingProduto);
                    return convertToDTO(existingProduto);
                })
                .orElse(null);
    }

    public void deletar(Integer id) {
        produtoRepository.deleteById(id);
    }

    private ProdutoDTO convertToDTO(Produto produto) {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setId(produto.getId());
        dto.setDescricao(produto.getDescricao());
        dto.setPrecovenda(produto.getPrecovenda());
        dto.setUnidade(produto.getUnidade());
        dto.setEstoque(produto.getEstoque());
        dto.setGrupos(produto.getGrupos());
        dto.setImage(produto.getImage());
        return dto;
    }
}