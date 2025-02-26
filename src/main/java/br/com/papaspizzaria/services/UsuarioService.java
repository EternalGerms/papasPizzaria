package br.com.papaspizzaria.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.papaspizzaria.dto.UsuarioDTO;
import br.com.papaspizzaria.entities.Usuario;
import br.com.papaspizzaria.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
    private UsuarioRepository usuarioRepository;
    
    public UsuarioService(UsuarioRepository usuarioRepository) {
    	this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioDTO> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(UsuarioDTO::new).toList();
    }

    public void salvarUsuario(UsuarioDTO usuario) {
    	Usuario usuarioEntity = new Usuario(usuario);
    	usuarioEntity.setSenha(passwordEncoder.encode(usuario.getSenha()));
    	usuarioRepository.save(usuarioEntity);
    }
    
    public UsuarioDTO alterarUsuario(UsuarioDTO usuario) {
    	Usuario usuarioEntity = new Usuario(usuario);
    	usuarioEntity.setSenha(passwordEncoder.encode(usuario.getSenha()));
    	return new UsuarioDTO(usuarioRepository.save(usuarioEntity));
    }

    public void excluirUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id).get();
        usuarioRepository.delete(usuario);
    }
    
    public UsuarioDTO buscarUsuario(Long id) {
    	return new UsuarioDTO(usuarioRepository.findById(id).get());
    }
    
}
