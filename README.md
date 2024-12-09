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
    10. Sentencia de control según: [caso, predeterminado] 
    11. Implementar análisis sintáctico para aceptar expresiones que permitan paréntesis y corchetes en operaciones aritméticas.

## Funcionalidades implmentadas (Análisis Semántico)

    * HashMap para el mapa de tipos para validaciones semanticas.
    * HashMap para la tabla de simbolos para validaciones semanticas.

    1. Validación de que se usa el comando "regresa" en funciones con tipo establecido.
    2. Validación de que el tipo de retorno de una función coincida con el tipo de dato declarado para dicha función
    
## Funcionalidades que faltan implementar (Análisis Semántico)

    0) asignación, Suma, resta, multiplicación, división

    1) En declaracion de dimension de una varible dimensionada, “Validar si usa un Ide en Dimension se haya declarado constante”

    2) En declaracion de dimension de una varible dimensionada, “Validar si usa un Ide en Dimension tipo entero”

    3) Cuando inicialice variables o constantes, “Validar tipo
        corresponda con el declarado”

    4) Cuando se declare función, “Validar que no exista según
        nombre y firma (orden y tipo de parámetro)”

    7) En asignación “Validar el tipo”, que tipo de Ide asignado
    sea compatible al de la expresión

    8) Cuando se inicializa valor a una VARIABLE lineal
        “Validar que NO este dimensionada

    9) Cuando se inicializa como grupo de constantes Validar
        que este "dimensionada"

    10) Si existe interrumpe “validar que se este en un ciclo” siempre y cuando tampoco esté dentro de un "segun"

    11) Validar que si una funcion tiene parametros, al llamarla desde otra función que se le pase la firma esperada (tipo de datos iguales) y que el orden coincida con otra función ya definida. 
