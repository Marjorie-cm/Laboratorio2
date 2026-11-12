# Laboratorio 02: Benchmarking de algoritmos

**Algoritmos · Semana 2 · Eficiencia y complejidad**
Carrera de Ingeniería en Sistemas de Información · Cuarto semestre

## Descripción

Este proyecto resuelve el **caso guía: buscar duplicados**. El problema consiste en determinar qué valores se repiten en una lista y comparar dos estrategias que producen la misma respuesta, midiendo su comportamiento con los mismos datos.

| Estrategia | Estructura | Idea |
|---|---|---|
| **A: Lista simple** | Lista simplemente enlazada (`Nodo`) | Por cada número, recorre la lista de los valores ya vistos para saber si está repetido. |
| **B: Pila** | `Deque` usado como pila (`ArrayDeque`) | Por cada número, saca elementos de la pila para buscarlo y luego los restaura con una pila auxiliar. |

**Métricas:** tiempo de ejecución (ms), número de operaciones y memoria adicional.

## Estructura del proyecto

```
RetoClase/
└── src/main/java/org/example/
    └── Main.java
```

El archivo `Main.java` contiene todo el código:

- `Nodo`: nodo de la lista simplemente enlazada.
- `Resultado`: guarda los duplicados encontrados, las operaciones y el tiempo.
- `listaSimple(int[] datos)`: Algoritmo A.
- `pila(int[] datos)`: Algoritmo B.
- `generarDatos(int n, Random random)`: genera los datasets de prueba.
- `main`: ejecuta el ejemplo de la diapositiva y la tabla de benchmarking.

## Requisitos

- Java JDK 11 o superior (probado con OpenJDK 27).
- IntelliJ IDEA (opcional) o cualquier terminal con `javac`.

## Cómo ejecutarlo

**Desde IntelliJ IDEA:** abrir el proyecto y ejecutar la clase `Main`.

**Desde la terminal:**

```bash
cd src/main/java
javac org/example/Main.java
java org.example.Main
```

## Cómo funciona cada algoritmo

### Algoritmo A: lista simple

1. Para cada número del arreglo, recorre la lista enlazada desde la cabeza.
2. Si lo encuentra, es un duplicado y se registra una sola vez.
3. Si no lo encuentra, lo agrega al final de la lista.

Cada nodo visitado cuenta como una operación, y cada inserción también.

### Algoritmo B: pila

1. Para cada número, saca elementos de la pila y los compara con el número, pasándolos a una pila auxiliar.
2. Cuando lo encuentra (o la pila queda vacía), devuelve todos los elementos a la pila original para conservar el orden.
3. Si no estaba, lo agrega con `push`.

Cada `pop`, comparación y `push` cuenta como una operación.

## Datos de prueba

- **Ejemplo de la diapositiva:** `[8, 3, 12, 8, 5, 9, 3, 20]`, con duplicados `[8, 3]`.
- **Benchmark:** tamaños `n = 100, 500, 1000, 2000`, con números aleatorios entre `1` y `n/2` (semilla `42`, para que sea reproducible).
- Cada prueba se **repite 5 veces** y se promedia el tiempo.
- En cada repetición se verifica que ambos algoritmos devuelvan **los mismos duplicados**. Si no coinciden, el programa lanza una excepción.

## Ejemplo de salida

```
==================================
 BUSQUEDA DE NUMEROS DUPLICADOS
==================================

DATOS:
[8, 3, 12, 8, 5, 9, 3, 20]

ALGORITMO A: LISTA SIMPLE
Duplicados: [8, 3]
Operaciones: 24
Tiempo: 1,868900 ms

ALGORITMO B: PILA
Duplicados: [8, 3]
Operaciones: 116
Tiempo: 0,049700 ms

==================================
 BENCHMARKING DE ALGORITMOS
==================================
Datos      Lista (ms)      Pila (ms)       Ops. Lista      Ops. Pila
100        0,060160        0,376040        1874            9157
500        0,344860        3,415960        47198           234549
1000       0,390080        8,287900        191080          924841
2000       1,382940        11,051000       753961          3765916

Pruebas finalizadas correctamente.
```

> Los tiempos varían según la máquina y la configuración regional (coma o punto decimal). Las operaciones son deterministas gracias a la semilla fija.

## Tabla de resultados (n / tiempo / operaciones)

| n | Tiempo lista (ms) | Tiempo pila (ms) | Ops. lista | Ops. pila |
|---:|---:|---:|---:|---:|
| 100 | 0,060 | 0,376 | 1.874 | 9.157 |
| 500 | 0,345 | 3,416 | 47.198 | 234.549 |
| 1000 | 0,390 | 8,288 | 191.080 | 924.841 |
| 2000 | 1,383 | 11,051 | 753.961 | 3.765.916 |

## Análisis técnico

- **Complejidad temporal:** ambas estrategias son **O(n²)** en el peor caso, porque cada elemento se compara contra los valores ya vistos. Al duplicar `n`, las operaciones se multiplican por aproximadamente 4 (por ejemplo, 191.080 → 753.961 en la lista).
- **Complejidad espacial:** ambas guardan los valores únicos vistos, es decir **O(k)** memoria adicional (k = cantidad de valores distintos). La pila usa además una pila auxiliar temporal.
- **¿Por qué la pila es más lenta?** Solo puede acceder al elemento superior, así que para buscar debe sacar los elementos, compararlos, guardarlos en una pila auxiliar y volver a restaurarlos. Eso son unas **5 operaciones por elemento revisado**, contra 1 en la lista enlazada. Por eso hace alrededor de 5 veces más operaciones (3.765.916 frente a 753.961 con n = 2000).
- **Conclusión:** la pila no es una buena estructura para buscar, porque no permite recorrer sin modificarse. La lista enlazada es más eficiente en este problema. Para escalar a 1.000.000 de datos, ninguna de las dos es adecuada: lo recomendable sería una estructura de búsqueda rápida como `HashSet`, con complejidad **O(n)**.

## Evidencia mínima entregada

- Código instrumentado (conteo de operaciones y medición de tiempo).
- Tabla n / tiempo / operaciones.
- Explicación de resultados (sección de análisis técnico).

## Autora

Marjorie Fernanda Sarango Condor
