package com.tool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tool.entity.Tools;

@Repository
public interface ToolsRepository extends JpaRepository<Tools, String> {

}
