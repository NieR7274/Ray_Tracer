package mx.unam.ciencias;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class PlanoTest {

    private static final double E = 1e-6;

    @Test
    public void testInterseccionPlanoInclinado() {
        // Plano inclinado a 45 grados que pasa por (0, 0, -4)
        Vector3 puntoPlano = new Vector3(0, 0, -4);
        Vector3 normalInclinada = new Vector3(0, 1, 1); 
        Plano plano = new Plano(puntoPlano, normalInclinada, new Vector3(0, 1, 0));

        // Rayo desde el origen hacia -Z
        Rayo rayo = new Rayo(new Vector3(0, 0, 0), new Vector3(0, 0, -1));

        Optional<Interseccion> resultado = plano.intersectar(rayo, 0.001, Double.POSITIVE_INFINITY);

        assertTrue(resultado.isPresent(), "El rayo deberia chocar con el plano inclinado");
        Interseccion impacto = resultado.get();

        // Debe chocar exactamente en (0, 0, -4) a distancia t = 4.0
        assertEquals(4.0, impacto.t(), E);
        assertEquals(-4.0, impacto.punto().z(), E);
        // La normal debe ser de magnitud 1.0
        assertEquals(1.0, impacto.normal().magnitud(), E);
    }

    @Test
    public void testRayoParaleloAlPlano() {
        // Piso horizontal en y = -1 con normal apuntando hacia arriba (0, 1, 0)
        Plano piso = new Plano(new Vector3(0, -1, 0), new Vector3(0, 1, 0), new Vector3(0.5, 0.5, 0.5));

        // Rayo en y = 0 viajando horizontalmente hacia -Z (paralelo al piso)
        Rayo rayoParalelo = new Rayo(new Vector3(0, 0, 0), new Vector3(0, 0, -1));

        Optional<Interseccion> resultado = piso.intersectar(rayoParalelo, 0.001, Double.POSITIVE_INFINITY);

        assertTrue(resultado.isEmpty(), "Un rayo paralelo nunca debe intersectar el plano");
    }
}