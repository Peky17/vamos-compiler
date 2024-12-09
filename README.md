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
    12. Mensaje de error cuando un identificador no es un comando, ni variable definida o algo en específico.

## Funcionalidades implmentadas (Análisis Semántico)

    * HashMap para el mapa de tipos para validaciones semanticas.
    * HashMap para la tabla de simbolos para validaciones semanticas.

    1. Validación par que se usa el comando "regresa" en funciones con tipo establecido.
    2. Validación para que el tipo de retorno de una función coincida con el tipo de dato declarado para dicha función
    3. Se agregó asignación como comando "=", permitiendo operaciones aritmeticas o asignacion simple con sus respectivas validaciones semánticas de tipo.
    4. Validar que no existan funciones con la misma firma (se permite sobrecarga)
    5. Validar si existe la función principal (debe estar definida)
    6. Validar si una funcion tiene parametros, al llamarla desde otra función que se le pase la firma esperada (tipo de datos iguales) y que el orden coincida con otra función previamente definida. 
    
## Funcionalidades que faltan implementar (Análisis Semántico)

    1) En declaracion de dimension de una varible dimensionada, “Validar si usa un Ide en Dimension se haya declarado constante”

    2) En declaracion de dimension de una varible dimensionada, “Validar si usa un Ide en Dimension tipo entero”

    3) Cuando inicialice variables o constantes, “Validar tipo
        corresponda con el declarado”

    8) Cuando se inicializa valor a una VARIABLE lineal
        “Validar que NO este dimensionada

    9) Cuando se inicializa como grupo de constantes Validar
        que este "dimensionada"

    10) Si existe interrumpe “validar que se este en un ciclo” siempre y cuando tampoco esté dentro de un "segun"
