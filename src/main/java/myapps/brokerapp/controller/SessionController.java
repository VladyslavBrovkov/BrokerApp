package myapps.brokerapp.controller;

import myapps.brokerapp.service.SessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public String sessionPage(Model model) {
        model.addAttribute("session", sessionService.isActive());
        return "sessionPage";
    }

    @PostMapping("/start")
    public String startSession() {
        CompletableFuture.runAsync(() -> {
            try {
                sessionService.startSession();
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return "home";
    }

    @PostMapping("/end")
    public String endSession() {
        sessionService.endSession();
        return "home";
    }


}
