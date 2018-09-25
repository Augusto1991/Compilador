package compilador18;

import java.util.Map;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractAS {
    Lex lexico;

    public AbstractAS(Lex lexico) {
        this.lexico = lexico;
    }

    public abstract void ejecutar(HashMap<String, Object> tablaSimbolos) throws IOException;
}
