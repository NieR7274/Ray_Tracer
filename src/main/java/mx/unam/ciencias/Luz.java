package mx.unam.ciencias;

/** Luz con una posición y una intensidad. */
public class Luz {
    private final Vector3 posicion;
    private final double intensidad;

    public Luz(Vector3 posicion, double intensidad) {
        if (posicion == null)
            throw new IllegalArgumentException("La posición de la luz es obligatoria.");
        if (!Double.isFinite(intensidad) || intensidad < 0)
            throw new IllegalArgumentException("La intensidad de la luz debe ser finita y no negativa.");
        this.posicion = posicion;
        this.intensidad = intensidad;
    }

    public Vector3 posicion() { return posicion; }
    public double intensidad() { return intensidad; }
}
