package mx.unam.ciencias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class Vector3Test {

    // Tolerancia para comparar decimales 
    private static final double E = 1e-6;

    @Test
    public void testOperacionesBasicas() {
        Vector3 a = new Vector3(1, 2, 3);
        Vector3 b = new Vector3(4, 5, 6);

        Vector3 suma = Vector3.sumar(a, b);
        assertEquals(5.0, suma.x(), E);
        assertEquals(7.0, suma.y(), E);
        assertEquals(9.0, suma.z(), E   );

        Vector3 resta = Vector3.restar(b, a);
        assertEquals(3.0, resta.x(), E);
        assertEquals(3.0, resta.y(), E);
        assertEquals(3.0, resta.z(), E);

        // Producto punto: (1*4) + (2*5) + (3*6) = 32
        assertEquals(32.0, Vector3.productoPunto(a, b), E);
    }

    @Test
    public void testNormalizarLongitudUno() {
        Vector3 v = new Vector3(3, 4, 12);
        Vector3 unitario = Vector3.vectorUnitario(v);

        // La magnitud de un vector normalizado siempre debe ser 1.0
        assertEquals(1.0, unitario.magnitud(), E);
    }

    @Test
    public void testProductoCruzEjes() {
        Vector3 i = new Vector3(1, 0, 0);
        Vector3 j = new Vector3(0, 1, 0);

        // i x j debe dar k = (0, 0, 1)
        Vector3 k = Vector3.productoCruz(i, j);
        assertEquals(0.0, k.x(), E);
        assertEquals(0.0, k.y(), E);
        assertEquals(1.0, k.z(), E);
    }
}