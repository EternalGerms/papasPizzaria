package br.com.papaspizzaria.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
import br.com.papaspizzaria.repositories.PedidoRepository;
import br.com.papaspizzaria.repositories.ProdutoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProdutoRepository produtoRepository;

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
        pedido.setValorTotal(pedidoDTO.getValorTotal());
        pedido.setObservacoes(pedidoDTO.getObservacoes());
        
        // Save the order first to get the ID
        pedido = pedidoRepository.save(pedido);
        
        // Handle items if they exist
        if (pedidoDTO.getItens() != null && !pedidoDTO.getItens().isEmpty()) {
            for (ItemPedidoDTO itemDTO : pedidoDTO.getItens()) {
                ItemPedido item = convertToItemPedido(itemDTO);
                item.setPedido(pedido);
                pedido.getItens().add(item);
            }
            // Save the order again with items
            pedido = pedidoRepository.save(pedido);
        }
        
        return convertToDTO(pedido);
    }

    public PedidoDTO atualizarStatus(Long id, String novoStatus) {
        if (!isFuncionario()) {
            throw new RuntimeException("Apenas funcionários podem atualizar o status do pedido");
        }
        
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        pedido.setStatus(novoStatus);
        return convertToDTO(pedidoRepository.save(pedido));
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
        item.setProduto(produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado")));
        item.setQuantidade(dto.getQuantidade());
        item.setValorUnitario(dto.getValorUnitario());
        item.setValorTotal(dto.getValorTotal());
        item.setObservacoes(dto.getObservacoes());
        return item;
    }

    private ItemPedidoDTO convertToItemPedidoDTO(ItemPedido item) {
        ItemPedidoDTO dto = new ItemPedidoDTO();
        dto.setId(item.getId());
        dto.setPedidoId(item.getPedido().getId());
        dto.setProdutoId(item.getProduto().getId());
        dto.setQuantidade(item.getQuantidade());
        dto.setValorUnitario(item.getValorUnitario());
        dto.setValorTotal(item.getValorTotal());
        dto.setObservacoes(item.getObservacoes());
        return dto;
    }
} 