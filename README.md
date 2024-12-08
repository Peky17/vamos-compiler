# Compilador del Lenguaje "VAMOS"

    Este lenguaje es un subconjunto del lenguaje GO en español desarrollado en la clase de traductores de lenguaje 2024-A.

## Las pruebas individuales para el análisis lexico - sintáctico en archivo

    test.icc (Pruebas individuales en desarrollo)
    TestVamos.icc (Prueba final)

## Funcionalidades implementadas (Análisis léxico)

    * Implementé los estados #15, #16, #17 en la matriz de transición para aceptar los operadores lógicos y los símbolos especiales.

    * Implementé un nuevo estado #18 para los delimitadores, específicamente para reconocer el delimitador ":" en la sentencia de control "segun"

## Funcionalidades implmentadas (Análisis Sintáctico)

    1. Declaración de múltiples variables o constantes en una sola línea
    2. Declaración de funciones con parametros y tipo de dato
    3. Declaracion de comando "regresa"
    4. Definición de vectores "arreglos".
    5. Sentencia de control si, sino con OpRel ==, !=, <=, >=. 
    6. Definición de sentencia si con && (y), || (o)
    7. Definición de sentencia iterativa "desde" con [inicialización de iterador]; [condición]; [actualización de iterador] { bloque }   
    8. Definición de sentencia iterativa "desde" únicamente con bloque: desde { bloque }
    9. Llamada entre funciones sin validación semántica.
    10. Sentencia de control según 

## Funcionalidades que faltan implementar (Análisis Sintáctico)

    1. Expresiones que permitan parentesis en operaciones aritméticas.

    2. "interrumpe" lo reconoce como ide y en realidad es un delimitador en el contexto de la sentencia segun.

    3. Corregir error con comando "predeterminado" en sentencia "segun"

    3. Implementar análisis sintáctico para aceptar expresiones que permitan paréntesis en operaciones aritméticas.

    4. Permitir definir la longitud de un vector con una constante entera.
    
## Análisis semántico

    1. Validar que si una funcion tiene parametros, al llamarla desde otra función que se le pase la firma esperada (tipo de datos iguales) y que el orden coincida con otra función ya definida. 
