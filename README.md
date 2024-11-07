# Compilador del Lenguaje "VAMOS"

    Este lenguaje es un subconjunto del lenguaje GO en español desarrollado en la clase de traductores de lenguaje 2024-A.

## Las pruebas individuales para el análisis lexico - sintáctico en archivo

    test.icc (Pruebas individuales en desarrollo)
    TestVamos.icc (Prueba final)

## Funcionalidades implementadas (Análisis léxico)

    * Implementé los estados #15, #16, #17 en la matriz de transición para aceptar los operadores lógicos y los símbolos especiales.

## Funcionalidades implmentadas (Análisis Sintáctico)

    1. Declaración de múltiples variables o constantes en una sola línea
    2. Declaración de funciones con parametros y tipo de dato
    3. Declaracion de comando "regresa"
    4. Definición de vectores "arreglos".
    5. Sentencia de control si, sino con OpRel ==, !=, <=, >=. 
    6. Definición de sentencia si con && (y), || (o)
    7. Definición de sentencia iterativa "desde" con [inicialización de iterador]; [condición]; [actualización de iterador] { bloque }   
    8. Definición de sentencia iterativa "desde" únicamente con bloque: desde { bloque }

## Funcionalidades que faltan implementar (Análisis Sintáctico)

    1. Sentencia de control según
    2. Llamada entre funciones

## Análisis semántico
