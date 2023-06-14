package com.example.lab8_20202330.Repositorys;

import com.example.lab8_20202330.Entitys.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento,Integer> {
}
