# Comdandos:

###### compilar:

* javac -d target\classes src\main\java\mx\unam\ciencias\Main.java

###### Crear imagen:

* java -cp target\classes mx.unam.ciencias.Main | Out-File -Encoding ascii image.ppm 

###### Eliminar la imgen:

 * Remove-Item image.ppm -ErrorAction SilentlyContinue