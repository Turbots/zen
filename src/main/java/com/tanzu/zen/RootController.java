package com.tanzu.zen;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class RootController {

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
}
