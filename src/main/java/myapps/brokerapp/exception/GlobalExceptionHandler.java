package myapps.brokerapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView internalServerErrorHandler(HttpServletRequest request, Exception exception) {
        return getModelAndView(request, exception);
    }

    private ModelAndView getModelAndView(HttpServletRequest request, Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        if (HttpStatus.INTERNAL_SERVER_ERROR.value() == HttpStatus.NOT_FOUND.value()) {
            modelAndView.setViewName("404");
        } else {
            modelAndView.setViewName("500");
        }
        modelAndView.addObject("code", HttpStatus.INTERNAL_SERVER_ERROR.value() + " / " + HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }
}
