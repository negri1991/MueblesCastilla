package es.mueblesCastilla.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import es.mueblesCastilla.model.Usuario;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BindException.class)
    public ModelAndView handleBindException(BindException ex, Usuario usuario) {
        ModelAndView modelAndView = new ModelAndView("usuario/registro");
        modelAndView.addObject("usuario", usuario);
        modelAndView.addObject("errors", ex.getBindingResult());
        return modelAndView;
    }

}
