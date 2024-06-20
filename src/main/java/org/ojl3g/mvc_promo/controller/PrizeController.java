package org.ojl3g.mvc_promo.controller;

import org.ojl3g.mvc_promo.model.Prize;
import org.ojl3g.mvc_promo.service.PrizeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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

    public PrizeController(PrizeService prizeService) {
        this.prizeService = prizeService;
    }

    private static final Logger log = LoggerFactory.getLogger(PrizeController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexGet() {
        return "index";
    }


    @RequestMapping(value = "/add_prize", method = RequestMethod.GET)
    public String addPrize() {
        return "addPrize";
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
            return "redirect:/";
        }

        for (String code : splitCodes) {
            Prize prize = new Prize(false, code, prizeName, linkImg);
            prizeService.addPrize(prize);
        }


        return "addPrize";
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
        return "winners";
    }

    @PostMapping("/edit_status")
    public ResponseEntity<String> editStatus(
            @RequestBody Map<String, Long> payload) {
        Long prizeId = payload.get("id");

        boolean updated = prizeService.changeStatus(prizeId);

        if (updated) {
            return ResponseEntity.ok("Status changed");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error changing status");
        }
    }


}



