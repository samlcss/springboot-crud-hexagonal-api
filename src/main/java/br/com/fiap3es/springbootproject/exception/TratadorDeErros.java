package br.com.fiap3es.springbootproject.exception;

import br.com.fiap3es.springbootproject.exception.types.aluno.AlunoInativoException;
import br.com.fiap3es.springbootproject.exception.types.aluno.AlunoNaoEncontradoException;
import br.com.fiap3es.springbootproject.exception.types.instrucao.InstrucaoNaoEncontradaException;
import br.com.fiap3es.springbootproject.exception.types.instrucao.ValidacaoException;
import br.com.fiap3es.springbootproject.exception.types.instrutor.InstrutorInativoException;
import br.com.fiap3es.springbootproject.exception.types.instrutor.InstrutorNaoEncontradoException;
import br.com.fiap3es.springbootproject.exception.types.usuario.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosBadRequest>> handleBadRequestException(MethodArgumentNotValidException ex) {
        List<FieldError> erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosBadRequest::new).toList());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleNotReadlableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas!");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação!");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado!");
    }

    @ExceptionHandler(AlunoInativoException.class)
    public ResponseEntity<String> handleBusinessRulesException(AlunoInativoException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

    @ExceptionHandler(AlunoNaoEncontradoException.class)
    public ResponseEntity<String> handleBusinessRulesException(AlunoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

    @ExceptionHandler(InstrucaoNaoEncontradaException.class)
    public ResponseEntity<String> handleBusinessRulesException(InstrucaoNaoEncontradaException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<String> handleBusinessRulesException(ValidacaoException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

    @ExceptionHandler(InstrutorInativoException.class)
    public ResponseEntity<String> handleBusinessRulesException(InstrutorInativoException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

    @ExceptionHandler(InstrutorNaoEncontradoException.class)
    public ResponseEntity<String> handleBusinessRulesException(InstrutorNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

    @ExceptionHandler(SenhaIncorretaException.class)
    public ResponseEntity<String> handleBusinessRulesException(SenhaIncorretaException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

    @ExceptionHandler(UsuarioAlunoNaoEncontradoException.class)
    public ResponseEntity<String> handleBusinessRulesException(UsuarioAlunoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

    @ExceptionHandler(UsuarioInativoException.class)
    public ResponseEntity<String> handleBusinessRulesException(UsuarioInativoException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

    @ExceptionHandler(UsuarioInstrutorNaoEncontradoException.class)
    public ResponseEntity<String> handleBusinessRulesException(UsuarioInstrutorNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<String> handleBusinessRulesException(UsuarioNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleInternalServerError(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + ex.getLocalizedMessage());
    }

    private record DadosBadRequest(String campo, String mensagem) {
        public DadosBadRequest(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}