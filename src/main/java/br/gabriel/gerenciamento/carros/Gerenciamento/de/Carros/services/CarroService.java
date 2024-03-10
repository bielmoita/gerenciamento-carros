package br.gabriel.gerenciamento.carros.Gerenciamento.de.Carros.services;
import br.gabriel.gerenciamento.carros.Gerenciamento.de.Carros.exceptions.ObjectNotFoundException;
import br.gabriel.gerenciamento.carros.Gerenciamento.de.Carros.models.Carro;
import br.gabriel.gerenciamento.carros.Gerenciamento.de.Carros.repositories.CarroRepository;
import br.gabriel.gerenciamento.carros.Gerenciamento.de.Carros.utils.StatusCarEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarroService {
    @Autowired
    private CarroRepository repo;

    public Carro create(Carro carro) {
        return repo.save(carro);
    }

    public String listarCarros(String name, String manufacturer, String year) {
        List<Carro> carros = repo.findAll();

        if (carros.isEmpty()) {
            throw new ObjectNotFoundException("Nenhum carro registrado!");
        }

        if (name != null) {
            carros = new ArrayList<>();
            carros = repo.findByNameIgnoreCase(name);
        }
        if (manufacturer != null) {
            carros = new ArrayList<>();
            carros = repo.findByManufacturerIgnoreCase(manufacturer);
        }
        if (year != null) {
            carros = new ArrayList<>();
            carros = repo.findByYear(Integer.parseInt(year));
        }


        return agruparPorFabricanteOrdenarPorNomeEAnoJSON(carros);
    }

    public Carro verDetalhes(long id) {
        Optional<Carro> verDetalhe = repo.findById(id);
        return verDetalhe.orElseThrow(() -> new ObjectNotFoundException("Carro nao encontrado por esse ID"));
    }

    public boolean trocarStatus(long id, StatusCarEnum status) {
        Optional<Carro> verDetalhe = repo.findById(id);
        if (verDetalhe.isEmpty()) {
            return false;
        }
        Carro mudarStatus = verDetalhe.get();
        mudarStatus.setStatus(status);
        repo.save(mudarStatus);
        return true;
    }

    public boolean delecaoLogica(long id) {
        Optional<Carro> verDetalhe = repo.findById(id);
        if (verDetalhe.isEmpty()) {
            return false;
        }
        Carro mudarStatus = verDetalhe.get();
        mudarStatus.setStatus(StatusCarEnum.DEACTIVATED);
        repo.save(mudarStatus);
        return true;
    }

    private static String agruparPorFabricanteOrdenarPorNomeEAnoJSON(List<Carro> carros) {

        if (carros.isEmpty()) {
            throw new ObjectNotFoundException("Nenhum carro registrado!");
        }

        Map<String, List<Carro>> carrosAgrupados = carros.stream()
                .sorted(Comparator.comparing(Carro::getName))
                .sorted(Comparator.comparingInt(Carro::getYear).reversed())
                .sorted(Comparator.comparing(Carro::getManufacturer))
                .collect(Collectors.groupingBy(Carro::getManufacturer));

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(carrosAgrupados);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
