package br.com.papaspizzaria.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.papaspizzaria.dto.PedidoDTO;
import br.com.papaspizzaria.dto.ItemPedidoDTO;
import br.com.papaspizzaria.entities.Pedido;
import br.com.papaspizzaria.entities.ItemPedido;
import br.com.papaspizzaria.entities.Usuario;
import br.com.papaspizzaria.entities.Produto;
import br.com.papaspizzaria.repositories.PedidoRepository;
import br.com.papaspizzaria.repositories.ProdutoRepository;
import br.com.papaspizzaria.repositories.EnderecoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public List<PedidoDTO> listarPedidosDoCliente(Long idCliente) {
        Usuario usuario = getUsuarioAutenticado();
        
        if (isFuncionario() || usuario.getId().equals(idCliente)) {
            return pedidoRepository.findByIdCliente(idCliente)
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        throw new RuntimeException("Acesso não autorizado");
    }

    public List<PedidoDTO> listarPedidosDoClienteAutenticado() {
        Usuario usuario = getUsuarioAutenticado();
        return pedidoRepository.findByIdCliente(usuario.getId())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PedidoDTO criarPedido(PedidoDTO pedidoDTO) {
        Usuario usuario = getUsuarioAutenticado();
        
        if (!isFuncionario() && !usuario.getId().equals(pedidoDTO.getIdCliente())) {
            throw new RuntimeException("Acesso não autorizado");
        }
        
        Pedido pedido = new Pedido();
        pedido.setIdCliente(pedidoDTO.getIdCliente());
        pedido.setIdEndereco(pedidoDTO.getIdEndereco());
        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatus("PENDENTE");
        pedido.setObservacoes(pedidoDTO.getObservacoes());
        
        // Handle items if they exist
        if (pedidoDTO.getItens() != null && !pedidoDTO.getItens().isEmpty()) {
            double valorTotal = 0.0;
            
            for (ItemPedidoDTO itemDTO : pedidoDTO.getItens()) {
                // Busca o produto e seu preço
                Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemDTO.getProdutoId()));
                
                ItemPedido item = new ItemPedido();
                item.setProduto(produto);
                item.setQuantidade(itemDTO.getQuantidade());
                item.setValorUnitario(produto.getPrecovenda());
                item.setPedido(pedido);
                pedido.getItens().add(item);
                
                // Calcula o valor total
                valorTotal += item.getQuantidade() * item.getValorUnitario();
            }
            
            pedido.setValorTotal(valorTotal);
        }
        
        // Save the order
        pedido = pedidoRepository.save(pedido);
        return convertToDTO(pedido);
    }

    @Transactional
    public PedidoDTO atualizarPedido(Long id, PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        Usuario usuario = getUsuarioAutenticado();
        
        if (!isFuncionario() && !usuario.getId().equals(pedido.getIdCliente())) {
            throw new RuntimeException("Acesso não autorizado. Cliente só pode editar seus próprios pedidos.");
        }
        
        if (!"PENDENTE".equals(pedido.getStatus())) {
            throw new RuntimeException("Não é possível editar o pedido. Apenas pedidos com status PENDENTE podem ser editados. Status atual: " + pedido.getStatus());
        }

        // Valida se o endereço existe e pertence ao cliente
        if (pedidoDTO.getIdEndereco() != null) {
            enderecoRepository.findById(pedidoDTO.getIdEndereco())
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
            
            if (!isFuncionario() && !enderecoRepository.existsByIdAndIdCliente(pedidoDTO.getIdEndereco(), usuario.getId().intValue())) {
                throw new RuntimeException("Endereço não pertence ao cliente");
            }
        }
        
        // Atualiza os campos permitidos
        if (pedidoDTO.getIdEndereco() != null) {
            pedido.setIdEndereco(pedidoDTO.getIdEndereco());
        }
        if (pedidoDTO.getObservacoes() != null) {
            pedido.setObservacoes(pedidoDTO.getObservacoes());
        }
        
        // Atualiza os itens do pedido
        if (pedidoDTO.getItens() != null && !pedidoDTO.getItens().isEmpty()) {
            // Limpa os itens existentes
            pedido.getItens().clear();
            
            double valorTotal = 0.0;
            
            // Adiciona os novos itens
            for (ItemPedidoDTO itemDTO : pedidoDTO.getItens()) {
                // Busca o produto e seu preço
                Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemDTO.getProdutoId()));
                
                ItemPedido item = new ItemPedido();
                item.setProduto(produto);
                item.setQuantidade(itemDTO.getQuantidade());
                item.setValorUnitario(produto.getPrecovenda());
                item.setPedido(pedido);
                pedido.getItens().add(item);
                
                // Calcula o valor total
                valorTotal += item.getQuantidade() * item.getValorUnitario();
            }
            
            pedido.setValorTotal(valorTotal);
        }
        
        // Salva o pedido e retorna o DTO
        pedido = pedidoRepository.save(pedido);
        return convertToDTO(pedido);
    }

    @Transactional
    public void deletarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        Usuario usuario = getUsuarioAutenticado();
        
        if (!isFuncionario() && !usuario.getId().equals(pedido.getIdCliente())) {
            throw new RuntimeException("Acesso não autorizado. Cliente só pode remover seus próprios pedidos.");
        }
        
        if (!"PENDENTE".equals(pedido.getStatus())) {
            throw new RuntimeException("Não é possível remover o pedido. Apenas pedidos com status PENDENTE podem ser removidos. Status atual: " + pedido.getStatus());
        }
        
        pedidoRepository.delete(pedido);
    }

    public PedidoDTO atualizarStatus(Long id, String novoStatus) {
        if (!isFuncionario()) {
            throw new RuntimeException("Apenas funcionários podem atualizar o status do pedido");
        }
        
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        // Valida o novo status
        if (!isStatusValido(novoStatus)) {
            throw new RuntimeException("Status inválido. Os status permitidos são: PENDENTE, A_CAMINHO, ENTREGUE");
        }
        
        pedido.setStatus(novoStatus);
        return convertToDTO(pedidoRepository.save(pedido));
    }

    private boolean isStatusValido(String status) {
        return "PENDENTE".equals(status) || 
               "A_CAMINHO".equals(status) || 
               "ENTREGUE".equals(status);
    }

    public PedidoDTO buscarPedidoPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        Usuario usuario = getUsuarioAutenticado();
        if (!isFuncionario() && !usuario.getId().equals(pedido.getIdCliente())) {
            throw new RuntimeException("Acesso não autorizado");
        }
        
        return convertToDTO(pedido);
    }

    private boolean isFuncionario() {
        return getUsuarioAutenticado().getTipo() == 2;
    }

    private Usuario getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return usuarioService.buscarUsuarioPorLogin(authentication.getName());
    }

    private PedidoDTO convertToDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setIdCliente(pedido.getIdCliente());
        dto.setIdEndereco(pedido.getIdEndereco());
        dto.setDataHora(pedido.getDataHora());
        dto.setStatus(pedido.getStatus());
        dto.setValorTotal(pedido.getValorTotal());
        dto.setObservacoes(pedido.getObservacoes());
        
        // Convert items to DTOs
        if (pedido.getItens() != null) {
            dto.setItens(pedido.getItens().stream()
                    .map(this::convertToItemPedidoDTO)
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }

    private ItemPedido convertToItemPedido(ItemPedidoDTO dto) {
        ItemPedido item = new ItemPedido();
        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        item.setProduto(produto);
        item.setQuantidade(dto.getQuantidade());
        item.setValorUnitario(produto.getPrecovenda());
        return item;
    }

    private ItemPedidoDTO convertToItemPedidoDTO(ItemPedido item) {
        ItemPedidoDTO dto = new ItemPedidoDTO();
        dto.setPedidoId(item.getPedido().getId());
        dto.setProdutoId(item.getProduto().getId());
        dto.setQuantidade(item.getQuantidade());
        return dto;
    }
} 