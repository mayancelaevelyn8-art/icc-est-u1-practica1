import java.util.List;
import datos.Datos;
import models.Estudiante;
import comparadores.Comparadores;


public class App {

    public static void main(String[] args) {
        List<Estudiante> original = Datos.listaOriginal();

        System.out.println("Lista original");
        print("ORIGINAL", "secuencia inicial E014...E013", original);

        print("C1", "codigo ascendente (Comparable, orden natural)",
                Comparadores.c1CodigoAsc(original));

        print("C2", "nombre ascendente; codigo ascendente (lambda 2 parametros)",
                Comparadores.c2NombreCodigo(original));

        print("C3", "nota ascendente, sin desempate (comparingDouble)",
                Comparadores.c3NotaAsc(original));

        print("C4", "nota descendente; nombre ascendente (reversed + thenComparing)",
                Comparadores.c4NotaDescNombreAsc(original));

        print("C5", "edad ascendente; nombre descendente (comparingInt + sentido independiente)",
                Comparadores.c5EdadAscNombreDesc(original));

        print("C6", "carrera asc; nota desc; nombre asc; codigo asc (4 criterios)",
                Comparadores.c6CarreraNotaNombreCodigo(original));

        System.out.println("C7: nota ajustada descendente; nombre asc; codigo asc");

        List<Estudiante> c7 = Comparadores.c7NotaAjustada(original);
        for (Estudiante e : c7) {
            System.out.printf("%s -> nota original %.1f | nota ajustada %.1f%n",
                    e.getCodigo(), e.getNota(), Comparadores.notaAjustada(e));
        }
    }

    private static void print(String id, String criterio, List<Estudiante> lista) {
        System.out.println("--- " + id + ": " + criterio + " ---");
        for (Estudiante e : lista) {
            System.out.println(e);
        }
        System.out.println();
    }
}