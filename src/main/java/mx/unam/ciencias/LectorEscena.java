package mx.unam.ciencias;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Lee y valida archivos JSON de escenas.
 * Los errores de sintaxis o de lectura se reportan como IOException;
 * los datos que no cumplen el formato se reportan como IllegalArgumentException.
 */
public final class LectorEscena {
    private static final ObjectMapper JSON = new ObjectMapper();

    private LectorEscena() {}

    public static Escena leer(Path archivo) throws IOException {
        JsonNode raiz = JSON.readTree(archivo.toFile());
        if (raiz == null || !raiz.isObject())
            throw new IllegalArgumentException("La raíz del JSON debe ser un objeto.");

        JsonNode imagen = objetoObligatorio(raiz, "imagen");
        int ancho = enteroPositivo(imagen, "ancho");
        int alto = enteroPositivo(imagen, "alto");

        JsonNode datosCamara = objetoObligatorio(raiz, "camara");
        Vector3 posicion = vector(datosCamara, "posicion");
        Vector3 miraA = vector(datosCamara, "mira_a");
        double fov = numero(datosCamara, "fov");
        Camara camara = new Camara(posicion, miraA, fov, ancho, alto);

        Vector3 fondo = color(raiz, "fondo");
        double luzAmbiental = numero(raiz, "luz_ambiental");
        if (luzAmbiental < 0 || luzAmbiental > 1)
            throw new IllegalArgumentException("'luz_ambiental' debe estar entre 0 y 1.");

        JsonNode datosLuces = arregloObligatorio(raiz, "luces");
        List<Luz> luces = new ArrayList<>();
        for (int i = 0; i < datosLuces.size(); i++) {
            JsonNode l = datosLuces.get(i);
            if (!l.isObject())
                throw new IllegalArgumentException("Cada luz debe ser un objeto JSON.");
            luces.add(new Luz(vector(l, "posicion"), numero(l, "intensidad")));
        }

        JsonNode datosObjetos = arregloObligatorio(raiz, "objetos");
        List<Figura> objetos = new ArrayList<>();
        for (int i = 0; i < datosObjetos.size(); i++) {
            JsonNode o = datosObjetos.get(i);
            if (!o.isObject())
                throw new IllegalArgumentException("Cada objeto debe ser un objeto JSON.");

            String tipo = textoObligatorio(o, "tipo");
            Vector3 color = color(o, "color");

            switch (tipo) {
                case "esfera":
                    Vector3 centro = vector(o, "centro");
                    double radio = numero(o, "radio");
                    if (radio <= 0)
                        throw new IllegalArgumentException("El radio de una esfera debe ser positivo.");
                    objetos.add(new Esfera(centro, radio, color));
                    break;

                case "plano":
                    Vector3 punto = vector(o, "punto");
                    Vector3 normal = vector(o, "normal");
                    if (normal.magnitudAlCuadrado() == 0.0)
                        throw new IllegalArgumentException("La normal de un plano no puede ser cero.");
                    objetos.add(new Plano(punto, normal, color));
                    break;

                default:
                    throw new IllegalArgumentException("Tipo de objeto desconocido: " + tipo);
            }
        }

        return new Escena(ancho, alto, camara, fondo, luzAmbiental, luces, objetos);
    }

    private static JsonNode objetoObligatorio(JsonNode padre, String campo) {
        JsonNode n = padre.get(campo);
        if (n == null || !n.isObject())
            throw new IllegalArgumentException("Falta el objeto '" + campo + "' o no es válido.");
        return n;
    }

    private static JsonNode arregloObligatorio(JsonNode padre, String campo) {
        JsonNode n = padre.get(campo);
        if (n == null || !n.isArray())
            throw new IllegalArgumentException("Falta el arreglo '" + campo + "' o no es válido.");
        return n;
    }

    private static String textoObligatorio(JsonNode padre, String campo) {
        JsonNode n = padre.get(campo);
        if (n == null || !n.isTextual() || n.asText().isBlank())
            throw new IllegalArgumentException("Falta el texto '" + campo + "' o no es válido.");
        return n.asText();
    }

    private static double numero(JsonNode padre, String campo) {
        JsonNode n = padre.get(campo);
        if (n == null || !n.isNumber() || !Double.isFinite(n.doubleValue()))
            throw new IllegalArgumentException("Falta el número '" + campo + "' o no es válido.");
        return n.doubleValue();
    }

    private static int enteroPositivo(JsonNode padre, String campo) {
        JsonNode n = padre.get(campo);
        if (n == null || !n.isIntegralNumber() || !n.canConvertToInt() || n.intValue() <= 0)
            throw new IllegalArgumentException("'" + campo + "' debe ser un entero positivo.");
        return n.intValue();
    }

    private static Vector3 vector(JsonNode padre, String campo) {
        JsonNode n = padre.get(campo);
        if (n == null || !n.isArray() || n.size() != 3)
            throw new IllegalArgumentException("'" + campo + "' debe ser un arreglo [x, y, z].");
        for (JsonNode componente : n) {
            if (!componente.isNumber() || !Double.isFinite(componente.doubleValue()))
                throw new IllegalArgumentException("Los componentes de '" + campo + "' deben ser números finitos.");
        }
        return new Vector3(n.get(0).doubleValue(), n.get(1).doubleValue(), n.get(2).doubleValue());
    }

    private static Vector3 color(JsonNode padre, String campo) {
        Vector3 c = vector(padre, campo);
        if (c.x() < 0 || c.x() > 1 || c.y() < 0 || c.y() > 1 || c.z() < 0 || c.z() > 1)
            throw new IllegalArgumentException("El color '" + campo + "' debe tener componentes entre 0 y 1.");
        return c;
    }
}
