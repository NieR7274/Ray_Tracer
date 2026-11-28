package mx.unam.ciencias;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class EscritorPPM {

    public static void escribir(String ruta, Vector3[] pixeles, int ancho, int alto)
            throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(Path.of(ruta))) {
            w.write("P3\n" + ancho + " " + alto + "\n255\n");
            for (Vector3 p : pixeles) {
                w.write(aByte(p.x()) + " " + aByte(p.y()) + " " + aByte(p.z()) + "\n");
            }
        }
    }

    private static int aByte(double x) {
        return (int) (255.999 * x);
    }
}