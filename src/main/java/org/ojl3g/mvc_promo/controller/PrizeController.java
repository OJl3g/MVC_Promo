package org.ojl3g.mvc_promo.controller;

import org.ojl3g.mvc_promo.model.Prize;
import org.ojl3g.mvc_promo.service.PrizeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

@Controller
public class PrizeController {
    private PrizeService prizeService;

    @Autowired
    private ResourceLoader resourceLoader;

    public PrizeController(PrizeService prizeService) {
        this.prizeService = prizeService;
    }

    private static final Logger log = LoggerFactory.getLogger(PrizeController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexGet() {
        return "users/index";
    }


    @RequestMapping(value = "/add_prize", method = RequestMethod.GET)
    public String addPrize() {
        return "admins/addPrize";
    }

    @RequestMapping(value = "/add_prize", method = RequestMethod.POST)
    public String addPostPrize(
            @RequestParam String prizeName,
            @RequestParam String promoCode,
            @RequestParam() MultipartFile prizeImage
    ) {
        String[] splitCodes = promoCode.split("[,;]");
        String linkImg;
        try {
            linkImg = saveImgToDirectory(prizeImage);
        } catch (IOException e) {
            log.error(e.getMessage());
            return "admins/addPrize";
        }

        for (String code : splitCodes) {
            Prize prize = new Prize(false, code, prizeName, linkImg);
            prizeService.addPrize(prize);
        }


        return "admins/addPrize";
    }

    public static String[] codeToArray(String promoCode) {
        return promoCode.split("[,;]");
    }


    public String saveImgToDirectory(MultipartFile multipartFile) throws IOException {
        Path path =
                Paths.get("C:\\Users\\Олег\\IdeaProjects\\MVC_Promo\\src\\main\\resources\\static\\img\\" + multipartFile.getOriginalFilename());
        Files.write(path, multipartFile.getBytes(), StandardOpenOption.CREATE);
        return "/img/" + multipartFile.getOriginalFilename();
    }

    @GetMapping("/winners")
    public String winnersGet(Model model) {
        List<Prize> listPrize = prizeService.getAllPrize();
        model.addAttribute("listPrize", listPrize);
        return "admins/winners";
    }


    @PostMapping("/edit_status")
    public ResponseEntity<String> editStatus(
            // @RequestBody указывает, что этот параметр метода будет привязан к телу HTTP-запроса.
            // Map<String, Long> payload представляет данные, отправленные в теле запроса в формате JSON.
            @RequestBody Map<String, Long> payload) {

        // Извлечение значения по ключу "id" из карты (payload) и присвоение его переменной prizeId.
        Long prizeId = payload.get("id");

        // Вызов метода changeStatus из prizeService с параметром prizeId.
        // Этот метод изменяет статус приза и возвращает булево значение (true, если изменение успешно, false в противном случае).
        boolean updated = prizeService.changeStatus(prizeId);

        // Проверка результата изменения статуса.
        if (updated) {
            // Если статус был успешно изменен, возвращается HTTP-ответ с кодом 200 (OK) и сообщением "Status changed".
            return ResponseEntity.ok("Status changed");
        } else {
            // Если изменение статуса не удалось, возвращается HTTP-ответ с кодом 500 (Internal Server Error) и сообщением "Error changing status".
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error changing status");
        }
    }

    @GetMapping("/activate-promo-code")
    public String activatePromoCode(Model model) {
        return "/users/promoCode";
    }


    @PostMapping("/activate-promo-code")
    public String activatePromoCode(@RequestParam("promoCode") String promoCode, Model model) {
        Prize prize = prizeService.findPrizeByCode(promoCode);

        if (prize == null) {
            model.addAttribute("errorMessage", "Код не найден");
            return "users/promoCode";
        } else {
            model.addAttribute("prize", prize);
            return "users/claimPrize";
        }
    }

    @GetMapping("/rule")
    public String showRules() {
        return "users/promotionRules";
    }


    //обработка открытия файла
    @GetMapping("/promotion-rules")
    public ResponseEntity<Resource> openPdf() {
        Resource resource = resourceLoader.getResource("classpath:/static/promotionsRules.pdf");
        if (!resource.exists()) {
            resource = new FileSystemResource("C:\\Users\\Олег\\IdeaProjects\\MVC_Promo\\src\\main\\resources\\static\\img\\promotionsRules.pdf");
        }
        //возврат ответа в body которого pdf файл
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=promotionsRules.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @GetMapping("/prizes")
    public String showPrizes(Model model) {
        List<Prize> prizes = prizeService.getAllPrize();
        model.addAttribute("prizes", prizes);
        return "users/listPrizes";
    }

    @GetMapping("/participate")
    public String showHowToParticipate() {
        return "participate";
    }


}



