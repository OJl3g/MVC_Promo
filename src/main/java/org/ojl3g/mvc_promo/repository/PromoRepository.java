package org.ojl3g.mvc_promo.repository;

import org.ojl3g.mvc_promo.model.Prize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoRepository extends JpaRepository<Prize,Long> {
}
