package org.ojl3g.mvc_promo.service;

import org.ojl3g.mvc_promo.model.Prize;
import org.ojl3g.mvc_promo.repository.PromoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PrizeService {
    PromoRepository promoRepository;
    private List<Prize> prizes = new ArrayList<>();

    public PrizeService(PromoRepository promoRepository) {
        this.promoRepository = promoRepository;
    }

    public void addPrize(Prize prize) {
        promoRepository.save(prize);
    }


    public List<Prize> getAllPrize() {
        return promoRepository.findAll();
    }

    public boolean changeStatus(Long prizeId) {
        Optional<Prize> prizeOptional = promoRepository.findById(prizeId);
        if (prizeOptional.isPresent()) {
            Prize prize = prizeOptional.get();

            prize.setStatus(!prize.isStatus()); // смена статуса на противоположный
            promoRepository.save(prize);
            return true;
        }
        return false;
    }

    public Prize findPrizeByCode(String promoCode){
        return promoRepository.findPrizeByCode(promoCode);
    }




    public List<Prize> getAllPrizes() {
        return prizes;
    }
}
