package org.ojl3g.mvc_promo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {





    @RequestMapping(value = "/admin_panel", method = RequestMethod.GET)
    public String adminPanel() {
        return "adminPanel";
    }

}
