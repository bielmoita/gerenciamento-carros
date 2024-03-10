package br.gabriel.gerenciamento.carros.Gerenciamento.de.Carros.controllers;
import br.gabriel.gerenciamento.carros.Gerenciamento.de.Carros.exceptions.ObjectNotFoundException;
import br.gabriel.gerenciamento.carros.Gerenciamento.de.Carros.models.Carro;
import br.gabriel.gerenciamento.carros.Gerenciamento.de.Carros.services.CarroService;
import br.gabriel.gerenciamento.carros.Gerenciamento.de.Carros.utils.StatusCarEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

@RestController
public class CarroController {

    @Autowired
    private CarroService carroService;

    //Cadastrar Carro
    @PostMapping
    public ResponseEntity<?> createCarro (@RequestBody Carro carro){

        try {
            carro = carroService.create(carro);
        } catch (DataIntegrityViolationException dex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\n\"message:\" : \"chassi ou placa já registrada\"\n}");
        }

        return new ResponseEntity( carro,HttpStatus.CREATED);
    }

    //Listar Carros
    @GetMapping
    public ResponseEntity<?> listarCarros(@RequestParam(name = "name", required = false) String name,
                                          @RequestParam(name = "manufacturer", required = false) String manufacturer,
                                          @RequestParam(name = "year", required = false) String year){

        try {
            String resultado = carroService.listarCarros(name, manufacturer, year);
            return ResponseEntity.ok(resultado);
        } catch (ObjectNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    //Ver Detalhes
    @GetMapping("/{id}")
    public ResponseEntity<Carro> verDetalhes(@PathVariable long id){
        Carro carro = carroService.verDetalhes(id);
        return new ResponseEntity( carro, HttpStatus.FOUND);
        //return carroService.verDetalhes(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
        //Optional<Carro> carro = carroService.verDetalhes(id);
        //return carro.isPresent() ? ResponseEntity.of(carro) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    //Trocar Status
    @PutMapping("/{id}/{status}")
    public ResponseEntity<Carro> trocarStatus(@PathVariable long id, @PathVariable StatusCarEnum status){
        boolean statusTrocado = carroService.trocarStatus(id, status);
        return new ResponseEntity("",(statusTrocado ? HttpStatus.OK : HttpStatus.BAD_REQUEST) );
    }

    //Delecao Logica
    @DeleteMapping("/{id}")
    public ResponseEntity<Carro> delecaoLogica(@PathVariable long id){
        boolean delecaoLogica = carroService.delecaoLogica(id);
        return new ResponseEntity("", HttpStatus.OK);
    }
}
