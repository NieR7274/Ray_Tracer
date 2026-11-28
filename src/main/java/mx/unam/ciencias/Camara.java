package mx.unam.ciencias;

public class Camara {
    private final Vector3 origen;
    private final Vector3 adelante, derecha, arriba;
    private final double mitadAncho, mitadAlto;
    private final int ancho, alto;

    public Camara(Vector3 posicion, Vector3 miraA, double fovGrados, int ancho, int alto) {
        this.origen = posicion;
        this.ancho = ancho;
        this.alto = alto;

        this.adelante = Vector3.vectorUnitario(Vector3.restar(miraA, posicion));
        this.derecha = Vector3.vectorUnitario(
                Vector3.productoCruz(adelante, new Vector3(0, 1, 0)));
        this.arriba = Vector3.productoCruz(derecha, adelante);

        this.mitadAncho = Math.tan(Math.toRadians(fovGrados) / 2);
        this.mitadAlto = mitadAncho * alto / ancho;
    }

    // Rayo que sale de la camara y pasa por el centro del pixel (i, j)
    public Rayo rayo(int i, int j) {
        double px = (2.0 * (i + 0.5) / ancho - 1) * mitadAncho;
        double py = (1 - 2.0 * (j + 0.5) / alto) * mitadAlto;

        Vector3 desplazamiento = Vector3.sumar(
                Vector3.multiplicar(px, derecha),
                Vector3.multiplicar(py, arriba));
        Vector3 direccion = Vector3.vectorUnitario(
                Vector3.sumar(adelante, desplazamiento));

        return new Rayo(origen, direccion);
    }
}