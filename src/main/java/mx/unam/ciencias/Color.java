package mx.unam.ciencias;


public class Color {

    // Convierte un vector RGB con valores en [0.0, 1.0] a enteros [0, 255] y los escribe en la salida
    public static void escribirColor( Vector3 colorPixel) {
        double r = colorPixel.x();
        double g = colorPixel.y();
        double b = colorPixel.z();

        // Traduce los componentes del rango [0, 1] al rango de bytes [0, 255]
        int rByte = (int) (255.999 * r);
        int gByte = (int) (255.999 * g);
        int bByte = (int) (255.999 * b);

        // Escribe los componentes del color del pixel
        System.out.println(rByte + " " + gByte + " " + bByte);
    }
}