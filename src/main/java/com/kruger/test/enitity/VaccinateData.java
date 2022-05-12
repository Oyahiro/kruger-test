package com.kruger.test.enitity;

import com.kruger.test.enums.VaccinateType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccinateData {

    @Column(name="vaccinated")
    private Boolean vaccinated;

    @Enumerated(EnumType.STRING)
    @Column(name="vaccinate_type", length = 20)
    private VaccinateType vaccinateType;

    @Temporal(TemporalType.DATE)
    @Column(name="vaccinate_date")
    private Date vaccinateDate;

    @Column(name="dose_amount")
    private Short doseAmount;

}
