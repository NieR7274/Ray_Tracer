package mx.unam.ciencias;

/**
 * Cámara perspectiva que genera rayos a través de los píxeles de una imagen.
 * El campo de visión recibido es horizontal y se da en grados.
 */
public class Camara {

    private final Vector3 posicion;
    private final Vector3 haciaDerecha;
    private final Vector3 haciaArriba;
    private final Vector3 haciaAtras;
    private final double mitadAncho;
    private final double mitadAlto;

    public Camara(Vector3 posicion, Vector3 miraA, double fovHorizontal,
                  int anchoImagen, int altoImagen) {
        if (posicion == null || miraA == null)
            throw new IllegalArgumentException("La posición y mira_a son obligatorias.");
        if (anchoImagen <= 0 || altoImagen <= 0)
            throw new IllegalArgumentException("Las dimensiones de imagen deben ser positivas.");
        if (!Double.isFinite(fovHorizontal) || fovHorizontal <= 0 || fovHorizontal >= 180)
            throw new IllegalArgumentException("El FOV debe estar entre 0 y 180 grados.");

        this.posicion = posicion;

        Vector3 direccionVista = Vector3.restar(miraA, posicion);
        if (direccionVista.magnitudAlCuadrado() == 0.0)
            throw new IllegalArgumentException("La cámara no puede mirar hacia su propia posición.");

        // haciaAtras apunta en dirección contraria a la vista.
        this.haciaAtras = Vector3.vectorUnitario(direccionVista.negar());

        // El vector vertical del mundo es (0, 1, 0).
        Vector3 arribaMundo = new Vector3(0, 1, 0);

        // Si la vista es paralela al eje vertical, se usa otro vector auxiliar.
        if (Math.abs(Vector3.productoPunto(this.haciaAtras, arribaMundo)) > 0.999999)
            arribaMundo = new Vector3(0, 0, 1);

        this.haciaDerecha = Vector3.vectorUnitario(
            Vector3.productoCruz(arribaMundo, this.haciaAtras)
        );
        this.haciaArriba = Vector3.productoCruz(this.haciaAtras, this.haciaDerecha);

        this.mitadAncho = Math.tan(Math.toRadians(fovHorizontal) / 2.0);
        this.mitadAlto = mitadAncho * ((double) altoImagen / anchoImagen);
    }

    public Vector3 posicion() {
        return posicion;
    }

    /**
     * Genera el rayo que atraviesa el centro del píxel (x, y).
     * El píxel (0, 0) es el de la esquina superior izquierda.
     */
    public Rayo generarRayo(int x, int y, int anchoImagen, int altoImagen) {
        if (x < 0 || x >= anchoImagen || y < 0 || y >= altoImagen)
            throw new IllegalArgumentException("El píxel está fuera de la imagen.");
        if (anchoImagen <= 0 || altoImagen <= 0)
            throw new IllegalArgumentException("Las dimensiones deben ser positivas.");

        // Coordenadas normalizadas del centro del píxel, en el intervalo [-1, 1].
        double u = 2.0 * (x + 0.5) / anchoImagen - 1.0;
        double v = 1.0 - 2.0 * (y + 0.5) / altoImagen;

        Vector3 direccion = Vector3.sumar(
            this.haciaAtras.negar(),
            Vector3.sumar(
                Vector3.multiplicar(u * mitadAncho, haciaDerecha),
                Vector3.multiplicar(v * mitadAlto, haciaArriba)
            )
        );

        return new Rayo(posicion, Vector3.vectorUnitario(direccion));
    }
}
