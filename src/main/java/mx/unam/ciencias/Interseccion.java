package mx.unam.ciencias;

// Guarda los datos del punto exacto donde un rayo choca con una figura
public class Interseccion {
    private final Vector3 punto;
    private final Vector3 normal;
    private final double t;
    private final Vector3 color;

    // Constructor principal
    public Interseccion(Vector3 punto, Vector3 normal, double t, Vector3 color) {
        this.punto = punto;
        this.normal = normal;
        this.t = t;
        this.color = color;
    }

    // Coordenada 3D donde ocurrio el choque
    public Vector3 punto() {
        return punto;
    }

    // Vector unitario perpendicular a la superficie en el punto de choque
    public Vector3 normal() {
        return normal;
    }

    // Distancia a lo largo del rayo desde su origen hasta el choque
    public double t() {
        return t;
    }

    // Color RGB de la figura golpeada
    public Vector3 color() {
        return color;
    }
}