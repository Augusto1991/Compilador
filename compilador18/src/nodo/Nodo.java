package nodo;


public class Nodo {
    private Nodo nodoIzq;
    private Nodo nodoDer;
    private String nombre;
    private String tipo;
    private String nombreAlternativo;

    public Nodo(String nombre, String tipo, String nombreAlternativo) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.nombreAlternativo = nombreAlternativo;
        nodoIzq = null;
        nodoDer = null;
    }
    
    public Nodo(Nodo nodoIzq, Nodo nodoDer, String nombre, String tipo) {
        this.nodoIzq = nodoIzq;
        this.nodoDer = nodoDer;
        this.nombre = nombre;
        this.tipo = tipo;
        nombreAlternativo = null;
    }

    public Nodo(String nombre, String tipo) {
        this.nodoIzq = null;
        this.nodoDer = null;
        this.tipo = tipo;
        this.nombre = nombre;
        nombreAlternativo = null;
    }
    
    public void setNombre(String name) {
        nombre = name;
    }
    
    public void setIzq(Nodo n) {
        this.nodoIzq = n;
    }
    
    public void setDer(Nodo n) {
        this.nodoDer = n;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Nodo getNodoIzq() {
        return nodoIzq;
    }

    public Nodo getNodoDer() {
        return nodoDer;
    }

    public String getNombre() {
       return nombre;   
    }
    
    public String getTipo() {
        return tipo;
        
    }

    public String getNombreAlternativo() {
        if (nombreAlternativo == null)
            return nombre;
        return nombreAlternativo;
    }
    
    
    public boolean esHoja(){
        if ((this.getNodoIzq()==null)&&(this.getNodoDer()==null))
            return true;
        else 
            return false;
    }
    
    
   public Nodo subArbolIzquierdo(Nodo n) {
        if (!n.esHoja()){
	        if(((n.getNodoIzq().esHoja())&&((n.getNodoDer()==null)||(n.getNodoDer().esHoja())))||(n.getNombre().equals("WHILE"))||(n.getNombre().equals(":")))
	            return n;
	        else 
	            if((n.getNodoDer()==null))
	                return subArbolIzquierdo(n.nodoIzq);   
                else
	                if ((n.nodoIzq.esHoja()) && (!n.nodoDer.esHoja()))
	                    return subArbolIzquierdo(n.getNodoDer());
	                 else if ((!n.nodoIzq.esHoja()) && (n.nodoDer.esHoja()))
	                            return subArbolIzquierdo(n.getNodoIzq());
	                      else 
	                            return subArbolIzquierdo(n.nodoIzq);
        }
        return null; 
    }
    
    public void imprimirArbol(Nodo n) {
		if (n != null){
			System.out.println(n.getNombre());
			imprimirArbol(n.getNodoIzq());
			imprimirArbol(n.getNodoDer());
		}
    }

    @Override
    public String toString() {
        return "Nodo{" + "nodoIzq=" + nodoIzq + ", nodoDer=" + nodoDer + ", nombre=" + nombre + ", tipo=" + tipo + '}';
    }   
    
}