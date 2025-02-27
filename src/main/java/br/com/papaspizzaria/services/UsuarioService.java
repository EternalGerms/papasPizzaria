package br.com.papaspizzaria.services;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.papaspizzaria.dto.UsuarioDTO;
import br.com.papaspizzaria.entities.TipoSituacaoUsuario;
import br.com.papaspizzaria.entities.Usuario;
import br.com.papaspizzaria.entities.UsuarioVerificacao;
import br.com.papaspizzaria.repositories.UsuarioRepository;
import br.com.papaspizzaria.repositories.UsuarioVerificacaoRepository;

@Service
public class UsuarioService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private UsuarioVerificacaoRepository usuarioVerificacaoRepository;

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

	public void registrarNovoUsuario(UsuarioDTO usuario) {
		Usuario usuarioEntity = new Usuario(usuario);
		usuarioEntity.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuarioEntity.setSituacao(TipoSituacaoUsuario.PENDENTE);
		usuarioEntity.setId(null);
		usuarioRepository.save(usuarioEntity);

		UsuarioVerificacao verificador = new UsuarioVerificacao();
		verificador.setUsuario(usuarioEntity);
		verificador.setUuid(UUID.randomUUID());
		verificador.setDataExpiracao(Instant.now().plusMillis(900000));
		usuarioVerificacaoRepository.save(verificador);

		emailService.enviarEmailTexto(usuario.getEmail(), "Novo usuário cadastrado",
				"Seu código para ativação do seu cadastro é: " + verificador.getUuid());

	}

	public String verificarCadastro(String uuid) {

		UsuarioVerificacao usuarioVerificacao = usuarioVerificacaoRepository.findByUuid(UUID.fromString(uuid)).get();
		if (usuarioVerificacao != null) {
			if (usuarioVerificacao.getDataExpiracao().compareTo(Instant.now()) >= 0) {
				Usuario u = usuarioVerificacao.getUsuario();
				u.setSituacao(TipoSituacaoUsuario.ATIVO);
				usuarioRepository.save(u);
				return "Usuário Verificado com sucesso";
			} else {
				usuarioVerificacaoRepository.delete(usuarioVerificacao);
				return "Tempo de verificação expirado";
			}
		} else {
			return "Usuário não verificado";
		}
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
