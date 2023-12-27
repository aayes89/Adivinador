# Adivinador
Juego de adivinar palabras

* Añadir mejoras para evitar visibilidad en la consola.
<code>
Console console = System.console();
if (console == null) {
   System.err.println("No se puede obtener la consola. Use Scanner en su lugar.");
   System.exit(1);
}
char[] palabraArray = console.readPassword("Ingrese la palabra que debe ser adivinada: ");
String palabra = new String(palabraArray);
</code>
