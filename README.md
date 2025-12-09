# Advent of Code 2025 - Day 8: Playground

## Descripción del Problema

El desafío consiste en ayudar a los Elfos a conectar cajas de conexiones eléctricas en un patio de juegos gigante. Se nos proporciona una lista de coordenadas 3D de estas cajas.

- **Parte 1:** Conectar los 1000 pares de cajas más cercanas y calcular el producto del tamaño de los tres circuitos resultantes más grandes.
- **Parte 2:** Continuar conectando las cajas más cercanas hasta que todas formen un único circuito gigante y calcular el producto de las coordenadas X de las dos últimas cajas conectadas.

## Estructura del Proyecto

El proyecto está estructurado en dos paquetes principales bajo `software.aoc.day8`, correspondiendo a cada parte del problema:

- `a`: Solución para la Parte 1.
- `b`: Solución para la Parte 2.

Ambos paquetes comparten una estructura de clases similar, reutilizando conceptos clave.

## Análisis de Calidad de Software

A continuación, se detalla cómo la solución cumple con los principios de diseño de software modernos, específicamente SOLID y Moducionalidad.

### 1. Modularity (Modularidad)

El código exhibe un **alto grado de modularidad**. El problema se ha descompuesto en componentes pequeños y manejables, cada uno con un propósito claro. No existe una clase "Dios" que haga todo; en su lugar, la lógica está distribuida:

- **Lectura de Datos:** Separada en `FileInstructionReader`.
- **Modelo de Datos:** `JunctionBox` y `Connection` encapsulan los datos puros.
- **Lógica de Grafo (Union-Find):** `JunctionBoxList` encapsula toda la complejidad de la estructura de datos, manteniendo la integridad del grafo.
- **Lógica de Negocio:** `Solver` se encarga exclusivamente de las reglas específicas del puzzle (iterar conexiones, calcular respuestas).

### 2. Principios SOLID

#### S - Single Responsibility Principle (SRP)

Este es el principio que mejor se cumple en la solución:

- **`FileInstructionReader`**: Su única responsabilidad es leer el archivo de entrada y parsear las líneas a objetos.
- **`JunctionBox` (Record)**: Responsable únicamente de contener los datos inmutables de una caja.
- **`Connection` (Record)**: Responsable de contener los datos de una conexión y calcular la distancia.
- **`JunctionBoxList`**: Responsable de gestionar la colección de cajas y realizar operaciones de conjunto (Union-Find) de manera inmutable.
- **`Solver`**: Responsable de la orquestación de alto nivel para resolver el problema específico.

#### O - Open/Closed Principle (OCP)

El uso de `Records` (inmutables) y un enfoque funcional (creando nuevas instancias de listas en lugar de mutar el estado) facilita que el comportamiento sea predecible. Si bien la extensión directa a través de herencia está limitada por el uso de `records`, la separación clara permite cambiar la implementación de `Solver` sin tocar la lógica de `JunctionBoxList`, o viceversa.

#### L - Liskov Substitution Principle (LSP)

Al no utilizarse una jerarquía de herencia compleja, no hay violaciones de este principio. Los `records` son clases finales, lo que evita problemas derivados de la herencia incorrecta.

#### I - Interface Segregation Principle (ISP)

El código es simple y directo, sin interfaces grandes que obliguen a implementar métodos innecesarios. Cada clase expone solo los métodos públicos que necesita su consumidor.

#### D - Dependency Inversion Principle (DIP)

Si bien el código depende de implementaciones concretas (por ejemplo, `Main` instancia `FileInstructionReader` directamente), esto es aceptable y pragmático para el alcance de un script de desafío. La separación lógica existe, y refactorizar a interfaces sería trivial si el proyecto creciera.

## Conclusión

La solución cumple satisfactoriamente con los principios de **Modularidad** y **SRP**. El código es limpio, legible y fácil de mantener debido a la clara separación de responsabilidades entre la gestión de datos, la lógica de grafos y la resolución del problema.
