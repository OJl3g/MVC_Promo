package org.ojl3g.mvc_promo.service;

import org.ojl3g.mvc_promo.model.Prize;
import org.ojl3g.mvc_promo.repository.PromoRepository;
import org.springframework.stereotype.Service;

@Service
public class PrizeService {
    PromoRepository promoRepository;

    public PrizeService(PromoRepository promoRepository) {
        this.promoRepository = promoRepository;
    }

    public void addPrize(Prize prize) {
        promoRepository.save(prize);
    }


}
