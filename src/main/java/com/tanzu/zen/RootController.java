package com.tanzu.zen;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
public class RootController implements CommandLineRunner {

    private final MessageService messageService;

    public RootController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("index");

        mav.addObject("nodeName", System.getenv("NODE_NAME"));
        mav.addObject("podIp", System.getenv("POD_IP"));
        mav.addObject("podName", System.getenv("POD_NAME"));

        mav.addObject("saying", messageService.getRandomSaying());

        return mav;
    }

    @Override
    public void run(String... args) {
        List<String> sayings = List.of(
                "If you think it’s expensive to hire a professional, wait until you hire an amateur.",
                "Programming is not a zero-sum game. Teaching something to a fellow programmer does not take it away from you.",
                "The key in such a transition to continuous delivery is to expect things to get worse before you’ll be able to make them better.",
                "To successfully implement continuous delivery, you need to change the culture of how an entire organization views software development efforts.",
                "If you adopt only one agile practice, let it be retrospectives. Everything else will follow.",
                "The key to following the continuous delivery path is to continually question your own assumptions about what’s possible.",
                "It is not the strongest of the species that survive, nor the most intelligent, but the one most responsive to change.",
                "Showing a strong success and visible benefits is key to getting others to agree to try your way of doing things.",
                "Any code commit that passes the entire CI/CD pipeline should be considered a candidate for production.",
                "Bad KPIs always lead to bad behaviour.",
                "Practice the philosophy of continuous improvement. Get a little bit better every single day.",
                "People should understand shared goals. The sort of closed, need-to-know thinking gets in the way of product and technical delivery. Everyone should know how systems work if they are interested."
        );

        sayings.forEach(this.messageService::save);
    }
}
