package mx.unam.ciencias;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class EsferaTest {

    private static final double E = 1e-6;

    @Test
    public void testRayoDirectoAlCentro() {
        // Esfera en (0, 0, -5) con radio 1
        Esfera esfera = new Esfera(new Vector3(0, 0, -5), 1.0, new Vector3(1, 0, 0));
        // Rayo desde el origen apuntando directo hacia -Z
        Rayo rayo = new Rayo(new Vector3(0, 0, 0), new Vector3(0, 0, -1));

        Optional<Interseccion> resultado = esfera.intersectar(rayo, 0.001, Double.POSITIVE_INFINITY);

        assertTrue(resultado.isPresent(), "El rayo deberia golpear la esfera");
        Interseccion impacto = resultado.get();

        // Debe chocar a distancia t = 4.0 (en el punto (0, 0, -4))
        assertEquals(4.0, impacto.t(), E );
        assertEquals(0.0, impacto.punto().x(), E);
        assertEquals(0.0, impacto.punto().y(), E);
        assertEquals(-4.0, impacto.punto().z(), E);

        // La normal debe apuntar hacia : (0, 0, 1)
        assertEquals(0.0, impacto.normal().x(), E);
        assertEquals(0.0, impacto.normal().y(), E);
        assertEquals(1.0, impacto.normal().z(), E);
    }

    @Test
    public void testRayoPasaDeLargo() {
        Esfera esfera = new Esfera(new Vector3(0, 0, -5), 1.0, new Vector3(1, 0, 0));
        // Rayo apuntando muy arriba en Y, pasa lejos de la esfera
        Rayo rayo = new Rayo(new Vector3(0, 0, 0), new Vector3(0, 5, -1));

        Optional<Interseccion> resultado = esfera.intersectar(rayo, 0.001, Double.POSITIVE_INFINITY);

        assertTrue(resultado.isEmpty(), "El rayo no deberia tocar la esfera");
    }

    @Test
    public void testEsferaDetrasDelRayo() {
        // Esfera ubicada en +Z 
        Esfera esfera = new Esfera(new Vector3(0, 0, 5), 1.0, new Vector3(1, 0, 0));
        Rayo rayo = new Rayo(new Vector3(0, 0, 0), new Vector3(0, 0, -1));

        Optional<Interseccion> resultado = esfera.intersectar(rayo, 0.001, Double.POSITIVE_INFINITY);

        assertTrue(resultado.isEmpty(), "No debe detectar objetos detras del origen del rayo");
    }
}