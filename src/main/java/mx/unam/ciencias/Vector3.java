package mx.unam.ciencias;

public class Vector3 {
    public double[] componentes;

    // Constructores
    public Vector3() {
        this.componentes = new double[]{0.0, 0.0, 0.0};
    }

    public Vector3(double x, double y, double z) {
        this.componentes = new double[]{x, y, z};
    }

    // Getters de coordenadas
    public double x() { return componentes[0]; }
    public double y() { return componentes[1]; }
    public double z() { return componentes[2]; }

    // Acceso por indice
    public double obtener(int indice) {
        return componentes[indice];
    }

    public void asignar(int indice, double valor) {
        componentes[indice] = valor;
    }

    // Negacion unaria (invierte el sentido del vector)
    public Vector3 negar() {
        return new Vector3(-componentes[0], -componentes[1], -componentes[2]);
    }

    // Operaciones  sobre el mismo vector (equivalentes a +=, *=, /=)
    public Vector3 sumarAsignar(Vector3 otroVector) {
        componentes[0] += otroVector.componentes[0];
        componentes[1] += otroVector.componentes[1];
        componentes[2] += otroVector.componentes[2];
        return this;
    }

    public Vector3 multiplicarAsignar(double escalar) {
        componentes[0] *= escalar;
        componentes[1] *= escalar;
        componentes[2] *= escalar;
        return this;
    }

    public Vector3 dividirAsignar(double escalar) {
        if (escalar == 0.0) {
            throw new IllegalArgumentException("No se puede dividir un vector entre cero.");
        }
        return this.multiplicarAsignar(1.0 / escalar);
    }

    // Magnitud del vector (raiz de la suma de los cuadrados de sus componentes)
    public double magnitud() {
        return Math.sqrt(magnitudAlCuadrado());
    }

    public double magnitudAlCuadrado() {
        return componentes[0] * componentes[0]
             + componentes[1] * componentes[1]
             + componentes[2] * componentes[2];
    }

    // Representacion en texto de las coordenadas (x y z)
    @Override
    public String toString() {
        return componentes[0] + " " + componentes[1] + " " + componentes[2];
    }

    // Suma de vectores
    public static Vector3 sumar(Vector3 vectorA, Vector3 vectorB) {
        return new Vector3(
            vectorA.componentes[0] + vectorB.componentes[0],
            vectorA.componentes[1] + vectorB.componentes[1],
            vectorA.componentes[2] + vectorB.componentes[2]
        );
    }

    // Resta de vectores
    public static Vector3 restar(Vector3 vectorA, Vector3 vectorB) {
        return new Vector3(
            vectorA.componentes[0] - vectorB.componentes[0],
            vectorA.componentes[1] - vectorB.componentes[1],
            vectorA.componentes[2] - vectorB.componentes[2]
        );
    }

    // Multiplicacion componente a componente (producto de Hadamard, creo ...)
    public static Vector3 multiplicar(Vector3 vectorA, Vector3 vectorB) {
        return new Vector3(
            vectorA.componentes[0] * vectorB.componentes[0],
            vectorA.componentes[1] * vectorB.componentes[1],
            vectorA.componentes[2] * vectorB.componentes[2]
        );
    }

    // Multiplicacion por un escalar
    public static Vector3 multiplicar(double escalar, Vector3 vector) {
        return new Vector3(
            escalar * vector.componentes[0],
            escalar * vector.componentes[1],
            escalar * vector.componentes[2]
        );
    }

    public static Vector3 multiplicar(Vector3 vector, double escalar) {
        return multiplicar(escalar, vector);
    }

    // Division entre un escalar (valida que no sea cero para evitar NaN o Infinity)
    public static Vector3 dividir(Vector3 vector, double escalar) {
        if (escalar == 0.0) {
            throw new IllegalArgumentException("No se puede dividir un vector entre cero.");
        }
        return multiplicar(1.0 / escalar, vector);
    }

    // Producto punto (escalar): mide que tanto apuntan dos vectores en la misma direccion, creo... 
    public static double productoPunto(Vector3 vectorA, Vector3 vectorB) {
        return vectorA.componentes[0] * vectorB.componentes[0]
             + vectorA.componentes[1] * vectorB.componentes[1]
             + vectorA.componentes[2] * vectorB.componentes[2];
    }

    // Producto cruz (vectorial): devuelve un vector perpendicular a ambos
    public static Vector3 productoCruz(Vector3 vectorA, Vector3 vectorB) {
        return new Vector3(
            vectorA.componentes[1] * vectorB.componentes[2] - vectorA.componentes[2] * vectorB.componentes[1],
            vectorA.componentes[2] * vectorB.componentes[0] - vectorA.componentes[0] * vectorB.componentes[2],
            vectorA.componentes[0] * vectorB.componentes[1] - vectorA.componentes[1] * vectorB.componentes[0]
        );
    }

    // Normaliza el vector para que tenga magnitud 1 conservando su direccion
    public static Vector3 vectorUnitario(Vector3 vector) {
        return dividir(vector, vector.magnitud());
    }
}