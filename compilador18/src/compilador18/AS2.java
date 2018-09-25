package compilador18;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AS2 extends AbstractAS {

	public AS2(Lex lexico) {
		super(lexico);
	}
	
	@Override
	public void ejecutar(HashMap<String, Object> tablaSimbolos) {
		lexico.agregarCaracterAlToken(lexico.getCaracterActual());
	}

}
