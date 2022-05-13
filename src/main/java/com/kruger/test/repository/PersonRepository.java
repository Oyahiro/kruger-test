package com.kruger.test.repository;

import com.kruger.test.enitity.Employee;
import com.kruger.test.enitity.Person;
import com.kruger.test.enums.VaccinateType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public interface PersonRepository extends AbstractEntityRepository<Person> {

    Optional<Person> findByIdentificationNumber(String identificationNumber);

    Page<Person> findAllByEmployeeVaccinateDataVaccinatedInAndEmployeeVaccinateDataVaccinateTypeInOrderBySurnames(
            Collection<Boolean> vaccinated,
            Collection<VaccinateType> vaccinateTypes,
            Pageable pageable);

    Page<Person> findAllByEmployeeVaccinateDataVaccinatedInAndEmployeeVaccinateDataVaccinateDateBetweenAndEmployeeVaccinateDataVaccinateTypeInOrderBySurnames(
            Collection<Boolean> vaccinated,
            Date vaccinateDateFrom,
            Date vaccinateDateTo,
            Collection<VaccinateType> vaccinateTypes,
            Pageable pageable);


}
