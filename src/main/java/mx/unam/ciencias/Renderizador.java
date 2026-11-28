package mx.unam.ciencias;

import java.util.Optional;

public class Renderizador {

    private static final double EPS = 0.001;

    // Color de un rayo: fondo si no toca nada, iluminacion de Lambert si toca algo
    public static Vector3 colorDeRayo(Escena escena, Rayo rayo) {
        Optional<Interseccion> choque = escena.masCercana(rayo, EPS, Double.POSITIVE_INFINITY);
        if (choque.isEmpty()) {
            return escena.fondo();
        }

        Interseccion h = choque.get();
        double luzTotal = escena.luzAmbiental();

        for (Luz luz : escena.luces()) {
            // direccion del punto hacia la luz (sin normalizar: t = 1 es la luz)
            Vector3 haciaLuz = Vector3.restar(luz.posicion(), h.punto());

            // rayo de sombra: si algo esta entre el punto y la luz, no aporta
            if (escena.hayObstaculo(new Rayo(h.punto(), haciaLuz), EPS, 1.0)) {
                continue;
            }

            double coseno = Math.max(0.0,
                    Vector3.productoPunto(h.normal(), Vector3.vectorUnitario(haciaLuz)));
            luzTotal += luz.intensidad() * coseno;
        }

        return limitar(Vector3.multiplicar(luzTotal, h.color()));
    }

    // Calcula todos los pixeles, renglon por renglon 
    public static Vector3[] renderizar(Escena escena, Camara camara, int ancho, int alto) {
        Vector3[] pixeles = new Vector3[ancho * alto];
        for (int j = 0; j < alto; j++) {
            for (int i = 0; i < ancho; i++) {
                pixeles[j * ancho + i] = colorDeRayo(escena, camara.rayo(i, j));
            }
        }
        return pixeles;
    }

    // Limita cada componente al intervalo [0, 1]
    private static Vector3 limitar(Vector3 c) {
        return new Vector3(
                Math.min(1.0, Math.max(0.0, c.x())),
                Math.min(1.0, Math.max(0.0, c.y())),
                Math.min(1.0, Math.max(0.0, c.z())));
    }
}