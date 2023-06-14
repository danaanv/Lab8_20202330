package com.example.lab8_20202330.Repositorys;

import com.example.lab8_20202330.Dtos.CantidadTicketsPorUsuario;
import com.example.lab8_20202330.Entitys.Ticket;
import com.example.lab8_20202330.Entitys.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    @Query(value = "select u.idusuario as usuario, count(t.id) as cantidadTickets\n" +
            "FROM ticket t\n" +
            "inner join usuario u on (t.idUsuario = u.idusuario)\n" +
            "group by u.idusuario",nativeQuery = true)
    List<CantidadTicketsPorUsuario> obtenerCantidadTicketsPorUsuario();
}
