package compilador18;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AS10 extends AbstractAS {

	public AS10(Lex lexico) {
		super(lexico);
	}
	
	@Override
	public void ejecutar(HashMap<String, Object> tablaSimbolos) throws IOException {
	
		lexico.agregarCaracterAlToken(lexico.getCaracterActual());
		if(!lexico.estaEnTS(lexico.getTokenActual())) {
			lexico.setTokenDesdeAS(lexico.altaEnTS(lexico.getTokenActual(),"cadena"));
		}
	}

}
