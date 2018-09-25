package compilador18;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AS9 extends AbstractAS {

	public AS9(Lex lexico) {
		super(lexico);
	}
	
	@Override
	public void ejecutar(HashMap<String, Object> tablaSimbolos) throws IOException {
		lexico.devolverBuffer(lexico.getCaracterActual());
	}

}
