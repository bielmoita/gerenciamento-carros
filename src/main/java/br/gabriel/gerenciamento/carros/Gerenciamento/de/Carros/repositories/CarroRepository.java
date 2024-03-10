package br.gabriel.gerenciamento.carros.Gerenciamento.de.Carros.repositories;

import br.gabriel.gerenciamento.carros.Gerenciamento.de.Carros.models.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {
    List<Carro> findByManufacturerIgnoreCase(String manufacturer);
    List<Carro> findByNameIgnoreCase(String name);
    List<Carro> findByYear(int year);

}