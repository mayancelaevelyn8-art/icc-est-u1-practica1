package comparadores;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import models.Estudiante;

public class Comparadores {

   
    // C2: nombre ascendente; codigo ascendente. Lambda de dos parametros. */
    public static Comparator<Estudiante> compC2Lambda() {
        return (e1, e2) -> {
            int comp = e1.getNombre().compareTo(e2.getNombre());
            if (comp != 0) {
                return comp;
            }
            return e1.getCodigo().compareTo(e2.getCodigo());
        };
    }

    //C2 (version alternativa): misma secuencia con comparing/thenComparing. */
    public static Comparator<Estudiante> compC2Ref() {
        return Comparator.comparing(Estudiante::getNombre)
                         .thenComparing(Estudiante::getCodigo);
    }

    // C3: nota ascendente, sin desempate (la estabilidad lo resuelve). */
    public static Comparator<Estudiante> compC3() {
        return Comparator.comparingDouble(Estudiante::getNota);
    }

    // C4: nota descendente; nombre ascendente. */
    public static Comparator<Estudiante> compC4() {
        return Comparator.comparingDouble(Estudiante::getNota)
                         .reversed()
                         .thenComparing(Estudiante::getNombre);
    }

    // C5: edad ascendente; nombre descendente (sentido independiente). */
    public static Comparator<Estudiante> compC5() {
        return Comparator.comparingInt(Estudiante::getEdad)
                         .thenComparing(Estudiante::getNombre,
                                        Comparator.reverseOrder());
    }

    // C6: carrera asc; nota desc; nombre asc; codigo asc. */
    public static Comparator<Estudiante> compC6() {
        return Comparator.comparing(Estudiante::getCarrera)
                .thenComparing(Comparator.comparingDouble(Estudiante::getNota)
                                         .reversed())
                .thenComparing(Estudiante::getNombre)
                .thenComparing(Estudiante::getCodigo);
    }

    // Clave de C7: ajuste de nota SIN modificar el objeto. */
    public static double notaAjustada(Estudiante e) {
        if ("Computacion".equals(e.getCarrera())) {
            return Math.min(10.0, e.getNota() + 0.5);
        }
        return e.getNota();
    }

    // C7: nota ajustada descendente; nombre asc; codigo asc. */
    public static Comparator<Estudiante> compC7() {
        return Comparator.comparingDouble((Estudiante e) -> notaAjustada(e))
                         .reversed()
                         .thenComparing(Estudiante::getNombre)
                         .thenComparing(Estudiante::getCodigo);
    }

 
    // C1: codigo ascendente con el orden natural (Comparable). */
    public static List<Estudiante> c1CodigoAsc(List<Estudiante> origen) {
        List<Estudiante> lista = new ArrayList<>(origen);
        lista.sort(null); // orden natural: codigo ascendente
        return lista;
    }

    public static List<Estudiante> c2NombreCodigo(List<Estudiante> origen) {
        return ordenarCopia(origen, compC2Lambda());
    }

    public static List<Estudiante> c2NombreCodigoRef(List<Estudiante> origen) {
        return ordenarCopia(origen, compC2Ref());
    }

    public static List<Estudiante> c3NotaAsc(List<Estudiante> origen) {
        return ordenarCopia(origen, compC3());
    }

    public static List<Estudiante> c4NotaDescNombreAsc(List<Estudiante> origen) {
        return ordenarCopia(origen, compC4());
    }

    public static List<Estudiante> c5EdadAscNombreDesc(List<Estudiante> origen) {
        return ordenarCopia(origen, compC5());
    }

    public static List<Estudiante> c6CarreraNotaNombreCodigo(List<Estudiante> origen) {
        return ordenarCopia(origen, compC6());
    }

    public static List<Estudiante> c7NotaAjustada(List<Estudiante> origen) {
        return ordenarCopia(origen, compC7());
    }

    private static List<Estudiante> ordenarCopia(List<Estudiante> origen,
                                                 Comparator<Estudiante> comp) {
        List<Estudiante> lista = new ArrayList<>(origen);
        lista.sort(comp);
        return lista;
    }
}