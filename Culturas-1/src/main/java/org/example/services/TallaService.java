package org.example.services;

import org.example.models.Talla;
import org.example.repositories.TallaRepository;

import java.util.List;

public class TallaService {

    private final TallaRepository tallaRepository;

    public TallaService() {
        this.tallaRepository = new TallaRepository();
    }

    public List<Talla> getAllTallas() {
        return tallaRepository.findAll();
    }

    public Talla getTallaById(int tallaId) {
        if (tallaId <= 0) {
            throw new IllegalArgumentException("El ID de la talla debe ser mayor a 0");
        }

        Talla talla = tallaRepository.findById(tallaId);

        if (talla == null) {
            throw new RuntimeException("Talla no encontrada con ID: " + tallaId);
        }

        return talla;
    }

    public Talla getTallaByCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de la talla no puede estar vacío");
        }

        Talla talla = tallaRepository.findByCodigo(codigo);

        if (talla == null) {
            throw new RuntimeException("Talla no encontrada con código: " + codigo);
        }

        return talla;
    }

    public Talla createTalla(Talla talla) {
        validateTalla(talla);

        if (tallaRepository.existsByCodigo(talla.getCodigo())) {
            throw new RuntimeException("Ya existe una talla con el código: " + talla.getCodigo());
        }

        return tallaRepository.save(talla);
    }

    public Talla updateTalla(int tallaId, Talla talla) {
        Talla existingTalla = tallaRepository.findById(tallaId);
        if (existingTalla == null) {
            throw new RuntimeException("Talla no encontrada con ID: " + tallaId);
        }

        validateTalla(talla);

        Talla tallaWithSameCodigo = tallaRepository.findByCodigo(talla.getCodigo());
        if (tallaWithSameCodigo != null && tallaWithSameCodigo.getTallaId() != tallaId) {
            throw new RuntimeException("Ya existe otra talla con el código: " + talla.getCodigo());
        }

        talla.setTallaId(tallaId);
        return tallaRepository.update(talla);
    }

    public boolean deleteTalla(int tallaId) {
        if (tallaId <= 0) {
            throw new IllegalArgumentException("El ID de la talla debe ser mayor a 0");
        }

        Talla talla = tallaRepository.findById(tallaId);
        if (talla == null) {
            throw new RuntimeException("Talla no encontrada con ID: " + tallaId);
        }

        return tallaRepository.deleteById(tallaId);
    }

    public long countTallas() {
        return tallaRepository.count();
    }

    private void validateTalla(Talla talla) {
        if (talla == null) {
            throw new IllegalArgumentException("La talla no puede ser nula");
        }

        if (talla.getCodigo() == null || talla.getCodigo().trim().isEmpty()) {
            throw new IllegalArgumentException("El código de la talla es obligatorio");
        }

        if (talla.getNombre() == null || talla.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la talla es obligatorio");
        }

        if (talla.getCodigo().length() > 10) {
            throw new IllegalArgumentException("El código de la talla no puede exceder 10 caracteres");
        }

        if (talla.getNombre().length() > 50) {
            throw new IllegalArgumentException("El nombre de la talla no puede exceder 50 caracteres");
        }
    }
}