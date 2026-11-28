package mx.unam.ciencias;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        int ancho = 400, alto = 225;

        Camara camara = new Camara(
                new Vector3(0, 1, 3), new Vector3(0, 0, -3), 60, ancho, alto);

        Escena escena = new Escena(
                List.of(
                    new Esfera(new Vector3(0, 0, -3), 1, new Vector3(0.9, 0.3, 0.2)),
                    new Plano(new Vector3(0, -1, 0), new Vector3(0, 1, 0),
                              new Vector3(0.8, 0.8, 0.8))),
                List.of(new Luz(new Vector3(5, 5, 0), 1.0)),
                new Vector3(0.6, 0.8, 1.0),
                0.1);

        Vector3[] pixeles = Renderizador.renderizar(escena, camara, ancho, alto);
        EscritorPPM.escribir("salida.ppm", pixeles, ancho, alto);
    }
}