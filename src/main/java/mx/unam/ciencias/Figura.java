package mx.unam.ciencias;

import java.util.Optional;

public interface Figura {

    Optional<Interseccion> intersectar(Rayo rayo, double tMin, double tMax);

}
