package mx.unam.ciencias;


public class Rayo {
    private Vector3 origen;
    private Vector3 direccion;

    // Constructor por defecto (inicializa origen y direccion en cero para evitar null)
    public Rayo() {
        this.origen = new Vector3();
        this.direccion = new Vector3();
    }

    // Constructor principal a partir de un punto de origen y un vector de direccion
    public Rayo(Vector3 origen, Vector3 direccion) {
        this.origen = origen;
        this.direccion = direccion;
    }

    // Getters
    public Vector3 origen() {
        return origen;
    }

    public Vector3 direccion() {
        return direccion;
    }

    // Calcula el punto 3D a lo largo del rayo en la posicion t: P(t) = origen + t * direccion
    public Vector3 en(double t) {
        return Vector3.sumar(origen, Vector3.multiplicar(t, direccion));
    }
}
