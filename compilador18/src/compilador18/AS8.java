package compilador18;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AS8 extends AbstractAS {

	public AS8(Lex lexico) {
		super(lexico);
	}
	
	@Override
	public void ejecutar(HashMap<String, Object> tablaSimbolos) throws IOException {
		
		lexico.agregarCaracterAlToken(lexico.getCaracterActual());
		lexico.setSimbolo(lexico.getTokenActual());
	}

}
