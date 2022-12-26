package myapps.brokerapp.controller;

import myapps.brokerapp.model.User;
import myapps.brokerapp.service.SessionService;
import myapps.brokerapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/agreements")
public class AgreementController {

    private final UserService userService;

    private final SessionService sessionService;

    public AgreementController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping("/all")
    public String getAllAgreements(Model model) {
        model.addAttribute("agreements", sessionService.getAgreementList());
        return "allAgreements";
    }

    @GetMapping("/trader")
    public String getTraderAgreements(Model model, Principal principal) {
        User trader = userService.findUserByLogin(principal.getName());
        model.addAttribute("trader_agreements", sessionService.getUserAgreements(trader.getId()));
        return "user-agreements";
    }

}
