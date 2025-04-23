package br.com.papaspizzaria.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.papaspizzaria.dto.EnderecoDTO;
import br.com.papaspizzaria.entities.Endereco;
import br.com.papaspizzaria.entities.Usuario;
import br.com.papaspizzaria.repositories.EnderecoRepository;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private UsuarioService usuarioService;

    public List<EnderecoDTO> listarEnderecosDoUsuario(Integer idCliente) {
        Usuario usuario = getUsuarioAutenticado();
        
        // Se for funcionário ou se for o próprio cliente
        if (isFuncionario() || usuario.getId().equals(idCliente.longValue())) {
            return enderecoRepository.findByIdCliente(idCliente)
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        throw new RuntimeException("Acesso não autorizado");
    }

    public EnderecoDTO buscarEndereco(Integer id) {
        Endereco endereco = enderecoRepository.findById(id).orElseThrow();
        validarAcessoEndereco(endereco);
        return convertToDTO(endereco);
    }

    public EnderecoDTO criarEndereco(EnderecoDTO enderecoDTO) {
        Usuario usuario = getUsuarioAutenticado();
        
        // Cliente só pode criar endereço para si mesmo
        if (!isFuncionario() && !usuario.getId().equals(enderecoDTO.getIdCliente().longValue())) {
            throw new RuntimeException("Acesso não autorizado");
        }
        
        Endereco endereco = new Endereco(enderecoDTO);
        endereco = enderecoRepository.save(endereco);
        return convertToDTO(endereco);
    }

    public EnderecoDTO atualizarEndereco(Integer id, EnderecoDTO enderecoDTO) {
        Endereco enderecoExistente = enderecoRepository.findById(id).orElseThrow();
        validarAcessoEndereco(enderecoExistente);
        
        // Atualiza os campos
        enderecoExistente.setEndereco(enderecoDTO.getEndereco());
        enderecoExistente.setBairro(enderecoDTO.getBairro());
        enderecoExistente.setNumero(enderecoDTO.getNumero());
        enderecoExistente.setComplemento(enderecoDTO.getComplemento());
        enderecoExistente.setObservac(enderecoDTO.getObservac());
        
        enderecoRepository.save(enderecoExistente);
        return convertToDTO(enderecoExistente);
    }

    public void deletarEndereco(Integer id) {
        Endereco endereco = enderecoRepository.findById(id).orElseThrow();
        validarAcessoEndereco(endereco);
        enderecoRepository.delete(endereco);
    }

    private void validarAcessoEndereco(Endereco endereco) {
        Usuario usuario = getUsuarioAutenticado();
        
        // Se não for funcionário e não for o dono do endereço
        if (!isFuncionario() && !usuario.getId().equals(endereco.getIdCliente().longValue())) {
            throw new RuntimeException("Acesso não autorizado");
        }
    }

    private Usuario getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return usuarioService.buscarUsuarioPorLogin(authentication.getName());
    }

    private boolean isFuncionario() {
        return getUsuarioAutenticado().getTipo() == 2; // 2 = FUNCIONARIO
    }

    private EnderecoDTO convertToDTO(Endereco endereco) {
        EnderecoDTO dto = new EnderecoDTO();
        dto.setId(endereco.getId());
        dto.setIdCliente(endereco.getIdCliente());
        dto.setEndereco(endereco.getEndereco());
        dto.setBairro(endereco.getBairro());
        dto.setNumero(endereco.getNumero());
        dto.setComplemento(endereco.getComplemento());
        dto.setObservac(endereco.getObservac());
        return dto;
    }
    
    public List<EnderecoDTO> listarTodosEnderecos() {
        if (!isFuncionario()) {
            throw new RuntimeException("Apenas funcionários podem listar todos os endereços");
        }
        return enderecoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
}