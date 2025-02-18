package com.br.sistema_teste.service;

import com.br.sistema_teste.domain.Usuario;
import com.br.sistema_teste.dto.UsuarioCreateDto;
import com.br.sistema_teste.dto.UsuarioResponseDto;
import com.br.sistema_teste.dto.UsuarioSenhaDto;
import com.br.sistema_teste.infra.exceptions.BusinessException;
import com.br.sistema_teste.repositories.UsuarioRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private ModelMapper modelMapper = new ModelMapper();
    private Validator validator;
    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

        @Test
    @DisplayName("Criar um novo usuario")
    public void teste_criar_novo_usuario(){
       UsuarioCreateDto createDto = new UsuarioCreateDto("leandro", "leandro@email.com", 30, "123456");
       Usuario usuario = modelMapper.map(createDto, Usuario.class);
       usuario.setPassword("senhaCodificada");
       Usuario usuarioSalvar = new Usuario(1L, "leandro", "leandro@email.com",30, "senhaCodificada");
       UsuarioResponseDto responseDto = new UsuarioResponseDto(1L, "leandro", "leandro@email.com",30);

       // Garanta que o createDto não é nulo
       assertNotNull(createDto);
       System.out.println("CreateDto: " + createDto);

       when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("senhaCodificada");
       when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioSalvar);

       // Act
       UsuarioResponseDto resultado = usuarioService.salvar(createDto);

       // Assert
       assertNotNull(resultado);
       assertEquals(1L, resultado.getId());
       assertEquals("leandro", resultado.getUsername());
       assertEquals("leandro@email.com", resultado.getEmail());
       assertTrue(resultado.getAge()>17);
   }
    @Test
    @DisplayName("Tentar cadastrar usuario menor de 18 anos")
    public void testSalvarUsuarioMenorDeIdade() {
        // Arrange
        UsuarioCreateDto createDto = new UsuarioCreateDto("leandro", "leandro@email.com", 17, "123456");

        // Garanta que o createDto não é nulo
        assertNotNull(createDto);
        System.out.println("CreateDto: " + createDto);

        // Act & Assert
        BusinessException thrown = assertThrows(
                BusinessException.class,
                () -> usuarioService.salvar(createDto),
                "Idade minima para cadastro 18 anos"
        );

        assertEquals("Idade minima para cadastro 18 anos", thrown.getMessage());
    }
    @Test
    public void testSalvarUsuarioComEmailValido() {
        // Arrange
        UsuarioCreateDto createUserDTO = new UsuarioCreateDto("Nome", "email@example.com", 30, "123456");
        Set<ConstraintViolation<UsuarioCreateDto>> violations = validator.validate(createUserDTO);

        // Verifique se não há violações de validação
        assertTrue(violations.isEmpty());

        Usuario usuario = modelMapper.map(createUserDTO, Usuario.class);
        usuario.setPassword("senhaCodificada");
        Usuario savedUsuario = new Usuario(1L, "Nome", "email@example.com",30, "senhaCodificada");

        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("senhaCodificada");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(savedUsuario);

        // Act
        UsuarioResponseDto responseUserDTO = usuarioService.salvar(createUserDTO);

        // Assert
        assertNotNull(responseUserDTO);
        assertEquals(1L, responseUserDTO.getId());
        assertEquals("Nome", responseUserDTO.getUsername());
        assertEquals("email@example.com", responseUserDTO.getEmail());
    }

    @Test
    public void testSalvarUsuarioComEmailInvalido() {
        // Arrange
        UsuarioCreateDto createUserDTO = new UsuarioCreateDto("Nome", "email_invalido", 30, "123456");
        Set<ConstraintViolation<UsuarioCreateDto>> violations = validator.validate(createUserDTO);

        // Verifique se há violações de validação
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        ConstraintViolation<UsuarioCreateDto> violation = violations.iterator().next();
        assertEquals("formato do e-mail está inválido", violation.getMessage());
    }
    @Test
    @DisplayName("Buscar usuario por Id")
    public void testBuscarUsuarioPorIdComSucesso() {
        // Arrange
        Long usuarioId = 1L;
        Usuario usuario = new Usuario(usuarioId, "leandro", "leandro@email.com",30, "senhaCodificada");
        UsuarioResponseDto responseDto = new UsuarioResponseDto(usuarioId, "leandro", "leandro@email.com",30);


        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));


        // Act
        UsuarioResponseDto resultado = usuarioService.buscarPorId(usuarioId);

        // Assert
        assertNotNull(resultado);
        assertEquals(usuarioId, resultado.getId());
        assertEquals("leandro", resultado.getUsername());
        assertEquals("leandro@email.com", resultado.getEmail());
    }

    @Test
    @DisplayName("Erro ao buscar usuario por Id")
    public void testBuscarUsuarioPorIdNaoEncontrado() {
        // Arrange
        Long usuarioId = 1L;

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        // Act & Assert
        BusinessException thrown = assertThrows(
                BusinessException.class,
                () -> usuarioService.buscarPorId(usuarioId)
        );

        assertEquals("Id:1 não encontrado", thrown.getMessage());
    }
    @Test
    @DisplayName("Listar todos os usuarios")
    public void testBuscarTodosUsuariosComSucesso() {
        // Arrange
        List<Usuario> usuarios = Arrays.asList(
                new Usuario(1L, "Nome1", "email1@example.com",30, "senhaCodificada"),
                new Usuario(2L, "Nome2", "email2@example.com",30, "senhaCodificada")
        );

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        // Act
        List<UsuarioResponseDto> resultados = usuarioService.buscarTodos();

        // Assert
        assertNotNull(resultados);
        assertEquals(2, resultados.size());
        assertEquals("Nome1", resultados.get(0).getUsername());
        assertEquals("email1@example.com", resultados.get(0).getEmail());
        assertEquals("Nome2", resultados.get(1).getUsername());
        assertEquals("email2@example.com", resultados.get(1).getEmail());
    }

    @Test
    public void testEditarSenhaComSucesso() {
        // Arrange
        Long usuarioId = 1L;
        Usuario usuario = new Usuario(usuarioId, "Nome", "email@example.com",30, "senhaAntigaCodificada");
        UsuarioSenhaDto senhaDto = new UsuarioSenhaDto("senhaAtual", "novaSenha", "novaSenha");

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(senhaDto.getSenhaAtual(), usuario.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(senhaDto.getNovaSenha())).thenReturn("novaSenhaCodificada");

        // Act
        usuarioService.editarSenha(usuarioId, senhaDto);

        // Assert
        assertEquals("novaSenhaCodificada", usuario.getPassword());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    public void testEditarSenhaIdNaoEncontrado() {
        // Arrange
        Long usuarioId = 1L;
        UsuarioSenhaDto senhaDto = new UsuarioSenhaDto("senhaAtual", "novaSenha", "novaSenha");

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        // Act & Assert
        BusinessException thrown = assertThrows(
                BusinessException.class,
                () -> usuarioService.editarSenha(usuarioId, senhaDto)
        );

        assertEquals("Id não encontrado", thrown.getMessage());
    }

    @Test
    public void testEditarSenhaSenhaAtualInvalida() {
        // Arrange
        Long usuarioId = 1L;
        Usuario usuario = new Usuario(usuarioId, "leandro", "leandro@email.com",30, "senhaAntigaCodificada");
        UsuarioSenhaDto senhaDto = new UsuarioSenhaDto("senhaAtual", "novaSenha", "novaSenha");

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(senhaDto.getSenhaAtual(), usuario.getPassword())).thenReturn(false);

        // Act & Assert
        BusinessException thrown = assertThrows(
                BusinessException.class,
                () -> usuarioService.editarSenha(usuarioId, senhaDto)
        );

        assertEquals("Sua senha não confere com senha atual", thrown.getMessage());
    }

    @Test
    public void testEditarSenhaNovaSenhaInvalida() {
        // Arrange
        Long usuarioId = 1L;
        Usuario usuario = new Usuario(usuarioId, "leandro", "leandro@email.com",30, "senhaAntigaCodificada");
        UsuarioSenhaDto senhaDto = new UsuarioSenhaDto("senhaAtual", "novaSenha", "confirmaSenhaDiferente");

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(senhaDto.getSenhaAtual(), usuario.getPassword())).thenReturn(true);

        // Act & Assert
        BusinessException thrown = assertThrows(
                BusinessException.class,
                () -> usuarioService.editarSenha(usuarioId, senhaDto)
        );

        assertEquals("Sua nova senha não confere com senha de confirmação", thrown.getMessage());
    }

}