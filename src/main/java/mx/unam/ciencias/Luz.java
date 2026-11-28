package mx.unam.ciencias;

public class Luz{
    private final Vector3 posicion;
    private final double intensidad;

    public Luz(Vector3 posicion, double intensidad){
        this.posicion = posicion;
        this. intensidad = intensidad;
    }

    public Vector3 posicion(){
        return posicion;
    }

    public double intensidad(){
        return intensidad;
    }
}