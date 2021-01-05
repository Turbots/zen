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
                "The temptation to give up is strongest just before victory.",
                "The goal in life is to die young, but to do as late as possible.",
                "Don't speak if it doesn't improve on silence.",
                "A thousand-mile journey begins with just one step.",
                "A strong man overcomes an obstacle, a wise man goes the whole way.",
                "Don’t be afraid to go slowly. Be afraid of stopping.",
                "A hut full of laughter is richer than a palace full of sadness.",
                "A good teacher opens the door for you, but you must enter the room by yourself."
        );

        sayings.forEach(this.messageService::save);
    }
}
