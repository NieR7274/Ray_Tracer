package mx.unam.ciencias;

import java.util.Optional;

// Representa una esfera 3D definida por su centro, radio y color
public class Esfera implements Figura {

    private final Vector3 centro;
    private final double radio;
    private final Vector3 color;

    public Esfera(Vector3 centro, double radio, Vector3 color) {
        this.centro = centro;
        this.radio = radio;
        this.color = color;
    }

    @Override
    public Optional<Interseccion> intersectar(Rayo rayo, double tMin, double tMax) {
        // Vector que va desde el origen del rayo hasta el centro de la esfera
        Vector3 origenACentro = Vector3.restar(centro, rayo.origen());

        // Coeficientes simplificados de la ecuacion cuadratica (at^2 - 2ht + c = 0)
        // Evita multiplicar y dividir por 2 y 4 en cada calculo
        // aparece en el libro en la seccion 6.2
        double a = rayo.direccion().magnitudAlCuadrado();
        double h = Vector3.productoPunto(rayo.direccion(), origenACentro);
        double c = origenACentro.magnitudAlCuadrado() - radio * radio;

        // El discriminante indica cuantas veces toca el rayo a la esfera:
        // < 0: no la toca | = 0: la roza (tangente) | > 0: entra y sale (2 puntos)
        double discriminante = h * h - a * c;
        if (discriminante < 0) {
            return Optional.empty();
        }

        double raizDiscriminante = Math.sqrt(discriminante);

        // Probamos primero la raiz negativa porque es el punto de impacto mas cercano
        double raiz = (h - raizDiscriminante) / a;
        if (raiz <= tMin || raiz >= tMax) {
            // Si la primera quedo fuera de rango (ej. detras de la camara), probamos la segunda
            raiz = (h + raizDiscriminante) / a;
            if (raiz <= tMin || raiz >= tMax) {
                return Optional.empty();
            }
        }

        // Calculamos la coordenada 3D exacta donde golpeo el rayo
        Vector3 punto = rayo.en(raiz);

        // La normal nace en el centro, pasa por el punto de impacto y se divide entre el radio para medir 1
        Vector3 normal = Vector3.dividir(Vector3.restar(punto, centro), radio);

        // Si el producto punto es positivo, el rayo pego desde adentro de la esfera:
        // invertimos la normal para que siempre apunte en contra del rayo
        if (Vector3.productoPunto(rayo.direccion(), normal) > 0) {
            normal = normal.negar();
        }

        // Empaquetamos todos los datos del choque en un objeto Interseccion
        return Optional.of(new Interseccion(punto, normal, raiz, color));
    }
}