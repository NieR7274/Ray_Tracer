package mx.unam.ciencias;

import java.util.Optional;

// Representa un plano infinito en el espacio 3D definido por un punto, su normal y un color
public class Plano implements Figura {

    private final Vector3 punto;
    private final Vector3 normal;
    private final Vector3 color;

    public Plano(Vector3 punto, Vector3 normal, Vector3 color) {
        this.punto = punto;
        this.normal = Vector3.vectorUnitario(normal);
        this.color = color;
    }

    @Override
    public Optional<Interseccion> intersectar(Rayo rayo, double tMin, double tMax) {
        // Producto punto entre la direccion del rayo (D) y la normal del plano (N)
        double denominador = Vector3.productoPunto(rayo.direccion(), normal);

        // Si el denominador es casi cero, el rayo es paralelo al plano y nunca lo toca
        // Usamos 1e-6 en lugar de == 0 (por los decimales de double), evita dividir entre casi cero
        // Math.abs saca el valor absoluto
        if (Math.abs(denominador) < 1e-6) {
            return Optional.empty();
        }

        // Calculamos la distancia t usando la formula: t = ((P0 - O) . N) / (D . N)
        Vector3 origenAPlano = Vector3.restar(punto, rayo.origen());
        double t = Vector3.productoPunto(origenAPlano, normal) / denominador;

        // Verificamos que el impacto este dentro del rango permitido (tMin, tMax)
        if (t <= tMin || t >= tMax) {
            return Optional.empty();
        }

        Vector3 puntoInterseccion = rayo.en(t);

        // Si denominador > 0, el rayo golpeo el plano por detras:
        // invertimos la normal para que siempre apunte en contra del rayo
        Vector3 normalFinal = (denominador > 0) ? normal.negar() : normal;

        return Optional.of(new Interseccion(puntoInterseccion, normalFinal, t, color));
    }
}