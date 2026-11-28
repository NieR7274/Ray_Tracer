package mx.unam.ciencias;

import java.util.List;
import java.util.Optional;

public class Escena {
    private final List<Figura> figuras;
    private final List<Luz> luces;
    private final Vector3 fondo;
    private final double luzAmbiental;

    public Escena(List<Figura> figuras, List<Luz> luces,
                  Vector3 fondo, double luzAmbiental) {
        // copias inmutables: la escena solo se lee durante el render
        this.figuras = List.copyOf(figuras);
        this.luces = List.copyOf(luces);
        this.fondo = fondo;
        this.luzAmbiental = luzAmbiental;
    }

    public List<Luz> luces() { return luces; }
    public Vector3 fondo() { return fondo; }
    public double luzAmbiental() { return luzAmbiental; }

    // ¿Qué es lo primero que choca este rayo?
    public Optional<Interseccion> masCercana(Rayo rayo, double tMin, double tMax) {
        Optional<Interseccion> mejor = Optional.empty();
        double limite = tMax;

        for (Figura figura : figuras) {
            Optional<Interseccion> choque = figura.intersectar(rayo, tMin, limite);
            if (choque.isPresent()) {
                mejor = choque;
                limite = choque.get().t();   // desde ahora solo cuenta lo mas cercano
            }
        }
        return mejor;
    }

    // ¿Hay algo en el camino de este rayo?
    public boolean hayObstaculo(Rayo rayo, double tMin, double tMax) {
        for (Figura figura : figuras) {
            if (figura.intersectar(rayo, tMin, tMax).isPresent()) {
                return true;
            }
        }
        return false;
    }
}