package org.example;

import java.util.*;

public class Main {

    // ==========================================
    // CLASE NODO PARA LISTA SIMPLEMENTE ENLAZADA
    // ==========================================

    static class Nodo {
        int dato;
        Nodo siguiente;

        public Nodo(int dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    // ==========================================
    // CLASE PARA GUARDAR LOS RESULTADOS
    // ==========================================

    static class Resultado {
        Set<Integer> duplicados;
        long operaciones;
        double tiempo;

        public Resultado(Set<Integer> duplicados,
                         long operaciones,
                         double tiempo) {
            this.duplicados = duplicados;
            this.operaciones = operaciones;
            this.tiempo = tiempo;
        }
    }

    // ==========================================
    // ALGORITMO A: LISTA SIMPLEMENTE ENLAZADA
    // ==========================================

    public static Resultado listaSimple(int[] datos) {

        Nodo cabeza = null;
        Nodo cola = null;

        Set<Integer> duplicados = new LinkedHashSet<>();
        Set<Integer> reportados = new HashSet<>();

        long operaciones = 0;

        long inicio = System.nanoTime();

        for (int numero : datos) {

            Nodo actual = cabeza;
            boolean encontrado = false;

            while (actual != null) {

                operaciones++;

                if (actual.dato == numero) {
                    encontrado = true;
                    break;
                }

                actual = actual.siguiente;
            }

            if (encontrado) {

                if (reportados.add(numero)) {
                    duplicados.add(numero);
                }

            } else {

                Nodo nuevo = new Nodo(numero);
                operaciones++;

                if (cabeza == null) {
                    cabeza = nuevo;
                } else {
                    cola.siguiente = nuevo;
                }

                cola = nuevo;
            }
        }

        long fin = System.nanoTime();

        double tiempo = (fin - inicio) / 1_000_000.0;

        return new Resultado(duplicados, operaciones, tiempo);
    }

    // ==========================================
    // ALGORITMO B: PILA (STACK)
    // ==========================================

    public static Resultado pila(int[] datos) {

        Deque<Integer> pila = new ArrayDeque<>();

        Set<Integer> duplicados = new LinkedHashSet<>();
        Set<Integer> reportados = new HashSet<>();

        long operaciones = 0;

        long inicio = System.nanoTime();

        for (int numero : datos) {

            Deque<Integer> auxiliar = new ArrayDeque<>();

            boolean encontrado = false;

            // Extraer y comparar elementos
            while (!pila.isEmpty() && !encontrado) {

                int elemento = pila.pop();
                operaciones++;

                operaciones++;

                if (elemento == numero) {
                    encontrado = true;
                }

                auxiliar.push(elemento);
                operaciones++;
            }

            // Restaurar la pila
            while (!auxiliar.isEmpty()) {

                int elemento = auxiliar.pop();
                operaciones++;

                pila.push(elemento);
                operaciones++;
            }

            if (encontrado) {

                if (reportados.add(numero)) {
                    duplicados.add(numero);
                }

            } else {

                pila.push(numero);
                operaciones++;
            }
        }

        long fin = System.nanoTime();

        double tiempo = (fin - inicio) / 1_000_000.0;

        return new Resultado(duplicados, operaciones, tiempo);
    }

    // ==========================================
    // GENERAR DATASETS
    // ==========================================

    public static int[] generarDatos(int n, Random random) {

        int[] datos = new int[n];

        for (int i = 0; i < n; i++) {
            datos[i] = random.nextInt(n / 2) + 1;
        }

        return datos;
    }

    // ==========================================
    // EJECUCION PRINCIPAL
    // ==========================================

    public static void main(String[] args) {

        // Ejemplo original
        int[] datos = {8, 3, 12, 8, 5, 9, 3, 20};

        System.out.println("==================================");
        System.out.println(" BUSQUEDA DE NUMEROS DUPLICADOS");
        System.out.println("==================================");

        Resultado resultadoA = listaSimple(datos);
        Resultado resultadoB = pila(datos);

        System.out.println("\nDATOS:");
        System.out.println(Arrays.toString(datos));

        System.out.println("\nALGORITMO A: LISTA SIMPLE");
        System.out.println("Duplicados: " + resultadoA.duplicados);
        System.out.println("Operaciones: " + resultadoA.operaciones);
        System.out.printf("Tiempo: %.6f ms%n", resultadoA.tiempo);

        System.out.println("\nALGORITMO B: PILA");
        System.out.println("Duplicados: " + resultadoB.duplicados);
        System.out.println("Operaciones: " + resultadoB.operaciones);
        System.out.printf("Tiempo: %.6f ms%n", resultadoB.tiempo);

        System.out.println("\n==================================");
        System.out.println(" BENCHMARKING DE ALGORITMOS");
        System.out.println("==================================");

        int[] tamanos = {100, 500, 1000, 2000};

        int repeticiones = 5;

        Random random = new Random(42);

        System.out.printf("%-10s %-15s %-15s %-15s %-15s%n",
                "Datos", "Lista (ms)", "Pila (ms)",
                "Ops. Lista", "Ops. Pila");

        for (int n : tamanos) {

            int[] dataset = generarDatos(n, random);

            double tiempoLista = 0;
            double tiempoPila = 0;

            Resultado rA = null;
            Resultado rB = null;

            for (int i = 0; i < repeticiones; i++) {

                rA = listaSimple(dataset);
                rB = pila(dataset);

                tiempoLista += rA.tiempo;
                tiempoPila += rB.tiempo;

                if (!rA.duplicados.equals(rB.duplicados)) {
                    throw new RuntimeException(
                            "Los resultados no coinciden");
                }
            }

            tiempoLista /= repeticiones;
            tiempoPila /= repeticiones;

            System.out.printf("%-10d %-15.6f %-15.6f %-15d %-15d%n",
                    n,
                    tiempoLista,
                    tiempoPila,
                    rA.operaciones,
                    rB.operaciones);
        }

        System.out.println("\nPruebas finalizadas correctamente.");
    }
}