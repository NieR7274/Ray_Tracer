package mx.unam.ciencias;

import java.util.List;

/** Contiene todos los datos necesarios para crear una escena. */
public class Escena {
    private final int ancho;
    private final int alto;
    private final Camara camara;
    private final Vector3 fondo;
    private final double luzAmbiental;
    private final List<Luz> luces;
    private final List<Figura> objetos;

    public Escena(int ancho, int alto, Camara camara, Vector3 fondo,
                  double luzAmbiental, List<Luz> luces, List<Figura> objetos) {
        this.ancho = ancho;
        this.alto = alto;
        this.camara = camara;
        this.fondo = fondo;
        this.luzAmbiental = luzAmbiental;
        this.luces = List.copyOf(luces);
        this.objetos = List.copyOf(objetos);
    }

    public int ancho() { return ancho; }
    public int alto() { return alto; }
    public Camara camara() { return camara; }
    public Vector3 fondo() { return fondo; }
    public double luzAmbiental() { return luzAmbiental; }
    public List<Luz> luces() { return luces; }
    public List<Figura> objetos() { return objetos; }
}
