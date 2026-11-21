package mx.unam.ciencias;

public class Main {


    //Prueba que crea un gradiente de color en formato PPM (P3) de 256x256 píxeles, donde el color varía horizontalmente de negro a rojo y verticalmente de negro a verde.

    public static void main(String[] args) {

        // Tamaño de la imagen

        int image_width = 256;
        int image_height = 256;

        // Informacion de la imagen

        System.out.println("P3");
        System.out.println(image_width + " " + image_height);
        System.out.println("255");


        // crea  de izquieda a derecha , de arriba a abajo los "pixeles". combinando solo  rojo y verde de  0 (negro) a 256 (su "tono" mas brillante)

        for (int j = 0; j < image_height; j++) {
            for (int i = 0; i < image_width; i++) {

                double r = (double) i / (image_width - 1);
                double g = (double) j / (image_height - 1);
                double b = 0.0;

                int ir = (int) (255.999 * r);
                int ig = (int) (255.999 * g);
                int ib = (int) (255.999 * b);

                System.out.println(ir + " " + ig + " " + ib);
            }
        }
    }
}