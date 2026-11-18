import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import comparadores.Comparadores;
import datos.Datos;
import models.Estudiante;

public class App {

    public static void main(String[] args) {
        List<Estudiante> original = Datos.listaOriginal();
        List<String> codigosInicio = codigos(original);

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

        pruebas(original, codigosInicio);
    }

    private static void print(String id, String criterio, List<Estudiante> lista) {
        System.out.println("--- " + id + ": " + criterio + " ---");
        for (Estudiante e : lista) {
            System.out.println(e);
        }
        System.out.println();
    }

    private static void pruebas(List<Estudiante> original, List<String> codigosInicio) {
        System.out.println("--- PRUEBAS ---");

        verificar("C1", Comparadores.c1CodigoAsc(original), Comparator.naturalOrder(), original);
        verificar("C2", Comparadores.c2NombreCodigo(original), Comparadores.compC2Lambda(), original);
        verificar("C3", Comparadores.c3NotaAsc(original), Comparadores.compC3(), original);
        verificar("C4", Comparadores.c4NotaDescNombreAsc(original), Comparadores.compC4(), original);
        verificar("C5", Comparadores.c5EdadAscNombreDesc(original), Comparadores.compC5(), original);
        verificar("C6", Comparadores.c6CarreraNotaNombreCodigo(original), Comparadores.compC6(), original);
        verificar("C7", Comparadores.c7NotaAjustada(original), Comparadores.compC7(), original);

        System.out.println("C2 lambda == C2 referencias: "
                + codigos(Comparadores.c2NombreCodigo(original))
                        .equals(codigos(Comparadores.c2NombreCodigoRef(original))));

        List<String> nota85 = new ArrayList<>();
        for (Estudiante e : Comparadores.c3NotaAsc(original)) {
            if (e.getNota() == 8.5) {
                nota85.add(e.getCodigo());
            }
        }
        System.out.println("C3 nota 8.5: " + nota85);

        List<Estudiante> vacia = new ArrayList<>();
        System.out.println("Lista vacia: " + Comparadores.c4NotaDescNombreAsc(vacia).size());

        List<Estudiante> uno = new ArrayList<>();
        uno.add(original.get(0));
        System.out.println("Un elemento: " + codigos(Comparadores.c4NotaDescNombreAsc(uno)));

        List<Estudiante> empate = new ArrayList<>();
        empate.add(original.get(0));
        empate.add(original.get(6));
        empate.add(original.get(18));
        System.out.println("C4 empate completo: " + codigos(Comparadores.c4NotaDescNombreAsc(empate)));

        Estudiante zoe = new Estudiante("E100", "Zoe", 20, 9.9, "Computacion");
        System.out.println("Propio 1 (tope 10.0): ajustada " + Comparadores.notaAjustada(zoe)
                + " | original " + zoe.getNota());

        List<Estudiante> mismoNombre = new ArrayList<>();
        mismoNombre.add(new Estudiante("E900", "Ana", 20, 8.0, "Computacion"));
        mismoNombre.add(new Estudiante("E200", "Ana", 20, 8.0, "Computacion"));
        System.out.println("Propio 2 (mismo nombre): "
                + codigos(Comparadores.c2NombreCodigo(mismoNombre)));

        System.out.println("Original intacta: " + codigos(original).equals(codigosInicio));
    }

    private static void verificar(String id, List<Estudiante> lista,
                                  Comparator<Estudiante> comp, List<Estudiante> original) {
        boolean ordenada = true;
        for (int i = 1; i < lista.size(); i++) {
            if (comp.compare(lista.get(i - 1), lista.get(i)) > 0) {
                ordenada = false;
            }
        }
        boolean completa = lista.size() == original.size()
                && new HashSet<>(codigos(lista)).equals(new HashSet<>(codigos(original)));
        System.out.println(id + " -> ordenada: " + ordenada + " | 20 codigos: " + completa);
    }

    private static List<String> codigos(List<Estudiante> lista) {
        List<String> r = new ArrayList<>();
        for (Estudiante e : lista) {
            r.add(e.getCodigo());
        }
        return r;
    }
}