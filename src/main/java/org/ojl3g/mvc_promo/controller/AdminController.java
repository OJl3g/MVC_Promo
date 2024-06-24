package org.ojl3g.mvc_promo.controller;
/**
 * Сделать страницы
 * Правила акции
 * вернуть обратно pdf файл который будет находиться в папаке static
 *
 * Призы (выводит таблицу призов ) для этого нужно в контролле получить лист призов из сервиса
 * (а в сервисе из репозитория) и передать на тсраницу где в виде таблицы отобразить призы
 *
 * как учавствовать просто информация
 */

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
