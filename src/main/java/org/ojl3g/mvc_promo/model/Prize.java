package org.ojl3g.mvc_promo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Prize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fullNameWinner;
    private String number;
    private String email;

    private boolean status = false;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String namePrize;
    private String picture;

    public Prize(boolean status, String code, String namePrize, String picture) {
        this.status = status;
        this.code = code;
        this.namePrize = namePrize;
        this.picture = picture;
    }
}
