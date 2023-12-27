import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
/*
Made by Slam
*/
public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean seguir = true;
        boolean ayuda = false;
        System.out.println("Juego de adivinar palabras");
        while (seguir) {
            System.out.println("Seleccione el nivel:");
            System.out.println("1. Con ayuda");
            System.out.println("2. Sin ayuda");
            int opcion = scan.nextInt();
            if (opcion == 1) {
                ayuda = true;
            }
            System.out.println("Ingrese la palabra que debe ser adivinada:");
            String palabra = scan.next().concat(scan.nextLine());
            Juego juego = new Juego(palabra, ayuda);

            System.out.println("La palabra a adivinar es: " + juego.getMascara());
            int intentos = juego.getIntentos();
            System.out.println("Tiene " + intentos + " intentos antes de finalizar la partida");

            boolean ganador = false;
            for (int i = 0; i < intentos; i++) {
                System.out.println("Intento " + (i + 1));
                String entrada = scan.nextLine();
                if (juego.analisisEntrada(entrada)) {
                    System.out.println("¡Ganaste! Adivinaste la palabra.");
                    ganador = true;
                    break;
                } else {
                    System.out.println("Has agotado un intento");
                }
            }

            if (!ganador) {
                System.out.println("Has perdido la partida. :(");
            }

            System.out.println("Desea seguir jugando? (s/n)");
            String respuesta = scan.nextLine();
            seguir = respuesta.equalsIgnoreCase("s");
        }

        // Cierra el Scanner
        scan.close();
    }

    static class Juego {

        private String palabra;
        private StringBuilder mascara;
        private int intentos;
        private boolean ayuda;

        public Juego(String palabra, boolean ayuda) {
            this.ayuda = ayuda;
            this.palabra = palabra;
            generarPalabraMascara();
            this.intentos = new Random().nextInt(1, palabra.length());
        }

        private void generarPalabraMascara() {
            mascara = new StringBuilder();
            int ocultarPorcentaje = (int) (palabra.length() * 0.4);
            for (int i = 0; i < palabra.length(); i++) {
                mascara.append(i < ocultarPorcentaje ? '_' : palabra.charAt(i));
            }
        }

        public boolean analisisEntrada(String entrada) {
            if (acierto(entrada)) {
                return true;
            }

            if (entrada.length() > palabra.length()) {
                System.out.println("Las palabras no son iguales o no tienen la misma longitud, sigue intentando");
                return false;
            }
            int cantCorrectas = 0;
            for (int i = 0; i < entrada.length(); i++) {
                for (int j = i; j < palabra.length(); j++) {
                    if (palabra.charAt(j) == entrada.charAt(i) && mascara.charAt(i) == '_') {
                        cantCorrectas++;
                        mascara.setCharAt(j, palabra.charAt(j));
                    }
                }
            }

            if (ayuda) {
                Set<Character> presentes = obtenerLetrasPresentes(entrada);
                if (!presentes.isEmpty()) {
                    System.out.println("Algunas letras están presentes: " + presentes);
                }
                System.out.println("Tienes " + cantCorrectas + " letras de la palabra correcta");
            }

            System.out.println("El estado de la palabra es: " + mascara.toString());

            return mascara.toString().equals(palabra);
        }

        public Set<Character> obtenerLetrasPresentes(String fragmento) {
            Set<Character> letrasPresentes = new HashSet<>();

            for (char letra : fragmento.toCharArray()) {
                if (palabra.contains(String.valueOf(letra))) {
                    letrasPresentes.add(letra);
                }
            }

            return letrasPresentes;
        }

        // GETTERS
        public String getMascara() {
            return this.mascara.toString();
        }

        public int getIntentos() {
            return this.intentos;
        }

        private boolean acierto(String entrada) {
            return palabra.equals(entrada);
        }
    }
}
