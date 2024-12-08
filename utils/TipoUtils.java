package utils;

import java.util.HashMap;
import java.util.Map;

public class TipoUtils {
    public static final Map<String, String> tiposTab = new HashMap<>();

    static {
        tiposTab.put("E=E", "");
        tiposTab.put("A=A", "");
        tiposTab.put("D=D", "");
        tiposTab.put("L=L", "");
        tiposTab.put("D=E", "");
        tiposTab.put("E+E", "E");
        tiposTab.put("E+D", "D");
        tiposTab.put("D+E", "D");
        tiposTab.put("D+D", "D");
        tiposTab.put("A+A", "A");
        tiposTab.put("E-E", "E");
        tiposTab.put("E-D", "D");
        tiposTab.put("D-E", "D");
        tiposTab.put("D-D", "D");
        tiposTab.put("E*E", "E");
        tiposTab.put("E*D", "D");
        tiposTab.put("D*E", "D");
        tiposTab.put("D*D", "D");
        tiposTab.put("E/E", "D");
        tiposTab.put("E/D", "D");
        tiposTab.put("D/E", "D");
        tiposTab.put("D/D", "D");
        tiposTab.put("E%E", "E");
        tiposTab.put("-E", "E");
        tiposTab.put("-D", "D");
        tiposTab.put("L&&L", "L");
        tiposTab.put("L||L", "L");
        tiposTab.put("!L", "L");
        tiposTab.put("E>E", "L");
        tiposTab.put("D>E", "L");
        tiposTab.put("E>D", "L");
        tiposTab.put("D>D", "L");
        tiposTab.put("E<E", "L");
        tiposTab.put("D<E", "L");
        tiposTab.put("E<D", "L");
        tiposTab.put("D<D", "L");
        tiposTab.put("E>=E", "L");
        tiposTab.put("D>=E", "L");
        tiposTab.put("E>=D", "L");
        tiposTab.put("D>=D", "L");
        tiposTab.put("E<=E", "L");
        tiposTab.put("D<=E", "L");
        tiposTab.put("E<=D", "L");
        tiposTab.put("D<=D", "L");
        tiposTab.put("E!=E", "L");
        tiposTab.put("D!=E", "L");
        tiposTab.put("E!=D", "L");
        tiposTab.put("D!=D", "L");
        tiposTab.put("A!=A", "L");
        tiposTab.put("E==E", "L");
        tiposTab.put("D==E", "L");
        tiposTab.put("E==D", "L");
        tiposTab.put("D==D", "L");
        tiposTab.put("A==A", "L");
    }
}