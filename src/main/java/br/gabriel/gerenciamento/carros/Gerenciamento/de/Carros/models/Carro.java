package br.gabriel.gerenciamento.carros.Gerenciamento.de.Carros.models;
import br.gabriel.gerenciamento.carros.Gerenciamento.de.Carros.utils.StatusCarEnum;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carro {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String chassi;

    private String name;

    private String manufacturer;

    private int year;

    private String color;

    private StatusCarEnum status;

    @Column(unique = true)
    @NotNull
    private String placa;

    private String image64;
}
