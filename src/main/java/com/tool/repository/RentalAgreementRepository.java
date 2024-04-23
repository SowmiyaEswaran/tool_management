package com.tool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tool.entity.RentalAgreement;

@Repository
public interface RentalAgreementRepository extends JpaRepository<RentalAgreement, Long> {

}
