package com.tool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tool.entity.Charges;

public interface ChargesRepository extends JpaRepository<Charges, String> {

}
