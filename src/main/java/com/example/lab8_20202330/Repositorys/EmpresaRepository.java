package com.example.lab8_20202330.Repositorys;

import com.example.lab8_20202330.Entitys.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
}
