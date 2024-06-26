package es.ceu.gisi.modcomp.gic_algorithms;

import es.ceu.gisi.modcomp.gic_algorithms.exceptions.CFGAlgorithmsException;
import es.ceu.gisi.modcomp.gic_algorithms.interfaces.*;
import java.util.AbstractMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;







/**
 * Esta clase contiene la implementación de las interfaces que establecen los
 * métodos necesarios para el correcto funcionamiento del
 * proyecto de programación de la asignatura Modelos de Computación.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 */
public class CFGAlgorithms implements CFGInterface, WFCFGInterface, CNFInterface, CYKInterface {
    Set<Character> setNonTerminal = new HashSet<>();
    char nonterminal;
    List<Character> nonTerminals = new ArrayList<>();
    
    Set<Character> setTerminal = new HashSet<>();
    Map<Character, List<String>> producciones = new HashMap<>();
    Character axioma = null;
    
    
    List<Character> setOrdenado = new ArrayList<>(nonterminal);
    String gramm;
    
    
    /**
     * Método que añade los elementos no terminales de la gramática.
     *
     * @param nonterminal Por ejemplo, 'S'
     *
     * @throws CFGAlgorithmsException Si el elemento no es una letra mayúscula o
     *                                si ya está en el conjunto.
     */
    @Override
    public void addNonTerminal(char nonterminal) throws CFGAlgorithmsException {
        
        if(!Character.isUpperCase(nonterminal)){
            throw new CFGAlgorithmsException("El noTerminal debe ser mayuscula");
        } 
        
        if(setNonTerminal.contains(nonterminal)){
            throw new CFGAlgorithmsException("Ya pertence al conjunto");
        }
        
        if(Character.isDigit(nonterminal)){
            throw new CFGAlgorithmsException("noTerminal debe ser una letra");
        }
        
        if(!setNonTerminal.contains(nonterminal)){    
        setNonTerminal.add(nonterminal);
        }
        
    }



    /**
     * Método que elimina el símbolo no terminal indicado de la gramática.
     * También debe eliminar todas las producciones asociadas a él y las
     * producciones en las que aparece.
     *
     * @param nonterminal Elemento no terminal a eliminar.
     *
     * @throws CFGAlgorithmsException Si el elemento no pertenece a la gramática
     */
    @Override
    public void removeNonTerminal(char nonterminal) throws CFGAlgorithmsException {
       if(!setNonTerminal.contains(nonterminal)) {
           throw new CFGAlgorithmsException("Ese noTerminal, no pertenece a la gramática");
       }
       setNonTerminal.remove(nonterminal);
    }



    /**
     * Método que devuelve un conjunto con todos los símbolos no terminales de
     * la gramática.
     *
     * @return Un conjunto con los no terminales definidos.
     */
    @Override
    public Set<Character> getNonTerminals() {
        Set<Character> set2 = new HashSet<>(setNonTerminal);
        return set2;
    }



    /**
     * Método que añade los elementos terminales de la gramática.
     *
     * @param terminal Por ejemplo, 'a'
     *
     * @throws CFGAlgorithmsException Si el elemento no es una letra minúscula o
     *                                si ya está en el conjunto.
     */
    @Override
    public void addTerminal(char terminal) throws CFGAlgorithmsException {
        if(setTerminal.contains(terminal)){
           throw new CFGAlgorithmsException("El terminal ya se ha definido");
        }
        if(!Character.isLowerCase(terminal)) {
           throw new CFGAlgorithmsException("El terminal debe ser minuscula");
       }
        if(Character.isDigit(terminal)) {
           throw new CFGAlgorithmsException("El terminal debe ser una letra");
       }
       setTerminal.add(terminal);
    }

    
    
    /**
     * Método que elimina el símbolo terminal indicado de la gramática.
     * También debe eliminar todas las producciones en las que aparece.
     *
     * @param terminal Elemento terminal a eliminar.
     *
     * @throws CFGAlgorithmsException Si el elemento no pertenece a la gramática
     */
    @Override
    public void removeTerminal(char terminal) throws CFGAlgorithmsException {
        if(!setTerminal.contains(terminal)) {
            throw new CFGAlgorithmsException("Ese terminal, no pertenece a la gramática");
        }
        setTerminal.remove(terminal);
    }
    


    /**
     * Método que devuelve un conjunto con todos los símbolos terminales de la
     * gramática.
     *
     * @return Un conjunto con los terminales definidos.
     */
    @Override
    public Set<Character> getTerminals() {
        Set<Character> set3 = new HashSet<>(setTerminal); 
        return set3;
    }



    /**
     * Método que indica, de los elementos no terminales, cuál es el axioma de
     * la gramática.
     *
     * @param nonterminal Por ejemplo, 'S'
     *
     * @throws CFGAlgorithmsException Si el elemento insertado no forma parte
     *                                del conjunto de elementos no terminales.
     */
    @Override
    public void setStartSymbol(char nonterminal) throws CFGAlgorithmsException {
    System.out.println("NonTerminals before setting start symbol: " + nonterminal);
   
    List<Character> nonTerminals = new ArrayList<>();
    nonTerminals.addAll(setNonTerminal);
    nonTerminals.remove(Character.valueOf(nonterminal));
    nonTerminals.add(0, nonterminal);
    axioma = nonterminal;
    
    if (!setNonTerminal.contains(nonterminal)) {
        throw new CFGAlgorithmsException("El axioma debe ser definido previamente");
    }
    
    
    System.out.println("NonTerminals after setting start symbol: " + setNonTerminal);
    System.out.println("Axioma set to: " + axioma);
}



    /**
     * Método que devuelve el axioma de la gramática.
     *
     * @return El axioma de la gramática
     *
     * @throws CFGAlgorithmsException Si el axioma todavía no ha sido
     *                                establecido.
     */
    @Override
    public Character getStartSymbol() throws CFGAlgorithmsException {
        if(axioma == null){
            throw new CFGAlgorithmsException("Defina un axioma");
        }
        return axioma;
    }



    /**
     * Método utilizado para construir la gramática. Admite producciones de tipo
     * 2. También permite añadir producciones a lambda (lambda se representa con
     * el caracter 'l' -- ele minúscula). Se permite añadirla en cualquier no
     * terminal.
     *
     * @param nonterminal A
     * @param production  Conjunto de elementos terminales y no terminales.
     *
     * @throws CFGAlgorithmsException Si está compuesta por elementos
     *                                (terminales o no terminales) no definidos previamente.
     */
    @Override
    public void addProduction(char nonterminal, String production) throws CFGAlgorithmsException { 
        if(!setNonTerminal.contains(nonterminal)){
            throw new CFGAlgorithmsException("El no terminal no existe" + nonterminal);
        }
        
        producciones.putIfAbsent(nonterminal, new ArrayList<>());
        List<String> produccionesList = producciones.get(nonterminal);
        
        if(produccionesList.contains(production)){
            throw new CFGAlgorithmsException("La produccion ya ha sido añadida previamente");
        }
        for(int i=0; i<production.length();i++){
        char simbolo = production.charAt(i);
            if(!setTerminal.contains(simbolo) && !setNonTerminal.contains(simbolo) && simbolo != 'l'){
            throw new CFGAlgorithmsException("La produccion contiene simbolos no defeinidos previamente");
            }
        }
        produccionesList.add(production);
    }
        
            


    /**
     * Elimina la producción indicada del elemento no terminal especificado.
     *
     * @param nonterminal Elemento no terminal al que pertenece la producción
     * @param production  Producción a eliminar
     *
     * @return True si la producción ha sido correctamente eliminada
     *
     * @throws CFGAlgorithmsException Si la producción no pertenecía a ese
     *                                elemento no terminal.
     */
    @Override
    public boolean removeProduction(char nonterminal, String production) throws CFGAlgorithmsException {
        if (!producciones.containsKey(nonterminal)) {
            throw new CFGAlgorithmsException("El no terminal no tiene producciones.");
        }
        
        List<String> produccionesList = producciones.get(nonterminal);
        if (!produccionesList.contains(production)) {
            throw new CFGAlgorithmsException("El valor no está en la producción");
        }
        produccionesList.remove(production);
        if (produccionesList.isEmpty()) {
            producciones.remove(nonterminal);
        }
        return true;
    }
    
    /**
     * Devuelve una lista de String que representan todas las producciones que
     * han sido agregadas a un elemento no terminal.
     *
     * @param nonterminal Elemento no terminal del que se buscan las
     *                    producciones
     *
     * @return Devuelve una lista de String donde cada String es la parte
     *         derecha de cada producción
     */
    @Override
    public List<String> getProductions(char nonterminal) {
       producciones.get(nonterminal);
    return producciones.get(nonterminal);
    }




    /**
     * Devuelve un String que representa todas las producciones que han sido
     * agregadas a un elemento no terminal.
     *
     * @param nonterminal
     *
     * @return Devuelve un String donde se indica, el elemento no terminal, el
     *         símbolo de producción "::=" y las producciones agregadas separadas, única
     *         y exclusivamente por una barra '|' (no incluya ningún espacio). Por
     *         ejemplo, si se piden las producciones del elemento 'S', el String de
     *         salida podría ser: "S::=aBb|bC|dC". Las producciones DEBEN IR ORDENADAS
     *         POR ORDEN ALFABÉTICO.
     */
    @Override
public String getProductionsToString(char nonterminal) {
    if (!producciones.containsKey(nonterminal)) {
        return nonterminal + "::=";
    }

    StringBuilder produccionesBuilder = new StringBuilder(nonterminal + "::=");
    List<String> var = getProductions(nonterminal).stream()
        .filter(p -> !p.isEmpty()) // Filtrar producciones vacías
        .sorted()
        .collect(Collectors.toList());

    for (int j = 0; j < var.size(); j++) {
        produccionesBuilder.append(var.get(j));
        if (j < var.size() - 1) {
            produccionesBuilder.append("|");
        }
    }

    return produccionesBuilder.toString();
}

    
    /**
     * Devuelve un String con la gramática completa. Todos los elementos no
     * terminales deberán aparecer por orden alfabético (A,B,C...).
     *
     * @return Devuelve el agregado de hacer getProductionsToString sobre todos
     *         los elementos no terminales ORDENADOS POR ORDEN ALFABÉTICO.
     */
    @Override
    public String getGrammar() {
        Set<Character> set3 = new HashSet<>(setNonTerminal); 
        List<Character> sortedNoTerminales = new ArrayList<>(set3);
        Collections.sort(sortedNoTerminales);
         
        String nonTerminalsString = sortedNoTerminales.stream().map(String::valueOf).collect(Collectors.joining(","));
        Set<Character> terminals = getTerminals();
        Character startSymbol = axioma;
        String producciones="";
        int i=0;
        int j=sortedNoTerminales.size();
        for (Character noTerminal : sortedNoTerminales){
            producciones = producciones + getProductionsToString(noTerminal);
            i++;
            if (i<j){
                producciones = producciones + ", ";
            }
            
        }
        //String produccion = getProductionsToString(nonterminal).trim();
        
        String gramm = nonTerminalsString +","+terminals+","+startSymbol+","+producciones;
        return gramm;
        
    }

    

    /**
     * Elimina todos los elementos que se han introducido hasta el momento en la
     * gramática (elementos terminales, no terminales, axioma y producciones),
     * dejando el algoritmo listo para volver a insertar una gramática nueva.
     */
    @Override
public void deleteGrammar() {
    if (!setNonTerminal.isEmpty() || !setTerminal.isEmpty() || !producciones.isEmpty() || axioma != null) {
        setNonTerminal.clear();
        setTerminal.clear();
        producciones.clear();
        axioma = null;
        gramm = "";
    } else {
        throw new UnsupportedOperationException("No se ha podido borrar la gramática");
    }
}



    /**
     * Método que comprueba si la gramática dada de alta es una gramática
     * independiente del contexto.
     *
     * @return true Si la gramática es una gramática independiente del contexto.
     */
    @Override
    public boolean isCFG() {
        Set<Map.Entry<Character, List<String>>> entradas = producciones.entrySet();
        for(Map.Entry<Character, List<String>>entrada:entradas){
        char elementoNoTerminalIzq = entrada.getKey();
        List<String> listaProduccionesDcha = entrada.getValue();
        
        if(!setNonTerminal.contains(elementoNoTerminalIzq)){
            return false;
        }
        
        for(int i=0; i<listaProduccionesDcha.size(); i++){
            String produccionDcha = listaProduccionesDcha.get(i);
           
                for(int j=0; j< produccionDcha.length(); j++){
                char simbolo = produccionDcha.charAt(j);
                    if(!setTerminal.contains(simbolo) && !setNonTerminal.contains(simbolo)&& simbolo !='l'){
                        return false;
                    }
                } 
            }
        }
    return true;
    }

    /**
     * Método que comprueba si la gramática almacenada tiene reglas innecesarias
     * (A::=A).
     *
     * @return True si contiene ese tipo de reglas
     */
    @Override
    public boolean hasUselessProductions() {
        Set<Map.Entry<Character, List<String>>> entradas = producciones.entrySet();
        for(Map.Entry<Character, List<String>>entrada:entradas){
            char elementoNoTerminalIzq = entrada.getKey();
            List<String> listaProduccionesDcha = entrada.getValue(); 
            for(String production : listaProduccionesDcha) {
                if(production.length() == 1 && production.charAt(0) == elementoNoTerminalIzq){
                    return true;
                }
            }   
        }
        return false;
    }



    /**
     * Método que elimina las reglas innecesarias de la gramática almacenada.
     *
     * @return Devuelve una lista de producciones (un String de la forma "A::=A"
     *         por cada producción), con todas las reglas innecesarias
     *         eliminadas.
     */
    @Override
    public List<String> removeUselessProductions() {
    List<String> produccionesEliminadas = new ArrayList<>();
    List<Map.Entry<Character, String>> produccionesAEliminar = new ArrayList<>();

    Set<Map.Entry<Character, List<String>>> entradas = producciones.entrySet();

    for (Map.Entry<Character, List<String>> entrada : entradas) {
        char elementoNoTerminalIzq = entrada.getKey();
        List<String> listaProduccionesDcha = entrada.getValue();
        for (String production : listaProduccionesDcha) {
            if (production.length() == 1 && production.charAt(0) == elementoNoTerminalIzq) {
                produccionesEliminadas.add(elementoNoTerminalIzq + "::=" + production);
                produccionesAEliminar.add(new AbstractMap.SimpleEntry<>(elementoNoTerminalIzq, production));
            }
        }
    }

    for (Map.Entry<Character, String> produccion : produccionesAEliminar) {
        producciones.get(produccion.getKey()).remove(produccion.getValue());
    }

    return produccionesEliminadas;
}



    /**
     * Método que elimina los símbolos inútiles de la gramática almacenada.
     *
     * @return Devuelve una lista con todos los símbolos no terminales y
     *         terminales eliminados.
     */
    @Override
    public List<Character> removeUselessSymbols() {
        Set<Character> Viejo = new HashSet<>();
        Set<Character> Nuevo = new HashSet<>();
        
        for (Map.Entry<Character, List<String>> entrada : producciones.entrySet()) {
                char A = entrada.getKey();
                for (String produccion : entrada.getValue()) {
                    if (isTerminalString(produccion)) {
                      Nuevo.add(A);
                     break;
                    }
                }
            }
        
        while (!Viejo.equals(Nuevo)) {
            Viejo = new HashSet<>(Nuevo);
            for (Map.Entry<Character, List<String>> entrada : producciones.entrySet()) {
                char A = entrada.getKey();
                for (String produccion : entrada.getValue()) {
                    if (containsOnly(Viejo, produccion)) {
                        Nuevo.add(A);
                        break;
                    }
                }
            }
        }
        
        Set<Character> inutiles = new HashSet<>(setNonTerminal);
        inutiles.removeAll(Nuevo);
        
        List<Character> eliminados = new ArrayList<>(inutiles);
        setNonTerminal.removeAll(inutiles);
        
        for (char inutil : inutiles) {
            producciones.remove(inutil);
        }

        for (Map.Entry<Character, List<String>> entrada : producciones.entrySet()) {
            entrada.getValue().removeIf(produccion -> containsAny(inutiles, produccion));
        }
        return eliminados;
    }
        
    private boolean isTerminalString(String produccion) {
        if (produccion==null || produccion.equals("")){
            return false;
        }
        for (char c : produccion.toCharArray()) {
            if (!setTerminal.contains(c) && c != 'l') {
                return false;
            }
        }
        return true;
    }
        
    private boolean containsOnly(Set<Character> conjunto, String produccion) {
        if (produccion==null || produccion.equals("")){
            return false;
        }
        for (char c : produccion.toCharArray()) {
            if (!conjunto.contains(c) && !setTerminal.contains(c) && c != 'l') {
                return false;
            }
        }
        return true;
    }  
        
    private boolean containsAny(Set<Character> conjunto, String produccion) {
        for (char c : produccion.toCharArray()) {
            if (conjunto.contains(c)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Método que comprueba si la gramática almacenada tiene reglas no
     * generativas (reglas lambda). Excepto S::=l si sólo es para reconocer la
     * palabra vacía.
     *
     * @return True si contiene ese tipo de reglas
     */
    @Override
public boolean hasLambdaProductions() {
    for (Map.Entry<Character, List<String>> entrada : producciones.entrySet()) {
        char noTerminal = entrada.getKey();
        List<String> listaProducciones = entrada.getValue();

        for (String produccion : listaProducciones) {
            if (produccion.equals("l")) {
                System.out.println("Producción lambda encontrada: " + noTerminal + " ::= l");
                if (noTerminal == axioma && listaProducciones.size() == 1) {
                    System.out.println("Es la producción S ::= l y es la única producción del axioma");
                    continue;  // Excluir esta producción
                }
                return true;
            }
        }
    }
    return false;
}



    /**
     * Método que elimina todas las reglas no generativas de la gramática
     * almacenada. La única regla que puede quedar es S::=l y debe haber sido
     * sustituida (y, por lo tanto, devuelta en la lista de producciones
     * "eliminadas").
     *
     * @return Devuelve una lista de no terminales que tenían reglas no
     *         generativas y han sido tratadas.
     */
@Override  
public List<Character> removeLambdaProductions() {

    List<Character> anulables = new ArrayList<>();
    Map<Character,List<String>> produccionesNuevas = new HashMap<>();
    Map<Character,String> eliminarProd = new HashMap<>();
    //añade a anulables solo los que directamente producen lambda y las añade a la lista eliminarProd para eliminarlas mas tarde 
    for (Map.Entry<Character, List<String>> entry : producciones.entrySet()) {
        Character noTerminal = entry.getKey();
        List<String> listaProducciones = entry.getValue();
        if (listaProducciones.contains("l")) {
            anulables.add(noTerminal);
            eliminarProd.put(noTerminal, "l");
        }
    }
    //añade a anulables los no terminales que producen elementos anulables
    //y se repite mientras se hagan actualizaciones en la lista anulables
    List<Character> anulablesViejo = new ArrayList<>();
    while(anulablesViejo != anulables){
        anulablesViejo = anulables;
        for (Map.Entry<Character, List<String>> entry : producciones.entrySet()) {
            outerLoop:
            for (String produccion : entry.getValue()) {
                boolean esAnulable = false;
                for (int w=0; w<produccion.length(); w++){
                    if (anulables.contains(produccion.charAt(w))){
                        esAnulable=true;
                    } 
                    else{
                        continue outerLoop;
                    }
                }
                if (esAnulable){
                    if (!anulables.contains(entry.getKey())){
                        anulables.add(entry.getKey());
                    }
                    
                }
            }
        }
    }

    //añade a produccionesNuevas las producciones que contienen algun valor de 'anulables' eliminando ese valor
    //puede haber problemas si la produccion contiene dos valores de anulables
    for (Map.Entry<Character, List<String>> entry : producciones.entrySet()) {
        for (String produccion : entry.getValue()) {
            List<Integer> posicionAnulable= new ArrayList<>();
            for (int i=0; i<produccion.length(); i++){
                if (anulables.contains(produccion.charAt(i))){
                    posicionAnulable.add(i);
                    List<String> a = new ArrayList<>();
                    if (produccionesNuevas.get(entry.getKey())!=null){
                        a = produccionesNuevas.get(entry.getKey());
                    }
                    String newProd = produccion.substring(0, i) + produccion.substring(i+1);
                    a.add(newProd);
                    produccionesNuevas.put(entry.getKey(),a);
                }
            }
            
            if(posicionAnulable.size()>=2){
                List<String> b = produccionesNuevas.get(entry.getKey());
                for (int p=0; p<posicionAnulable.size(); p++){
                    StringBuilder nuevasProd= new StringBuilder(produccion);
                    int w=0;
                    for (int j=0; j<posicionAnulable.size(); j++){
                        if (j==p){
                            continue;
                        }
                        nuevasProd.deleteCharAt(posicionAnulable.get(j)-w);
                        w++;
                    }
                    b.add(nuevasProd.toString());
                }
                int z=0;
                StringBuilder nuevasProd2= new StringBuilder(produccion);
                for (int j=0; j<posicionAnulable.size(); j++){
                    nuevasProd2.deleteCharAt(posicionAnulable.get(j)-z);
                    z++;
                }
                b.add(nuevasProd2.toString());
                produccionesNuevas.put(entry.getKey(),b);
            }
        }
    }
    //añade las producciones nuevas a la gramatica
    for (Map.Entry<Character,List<String>> entry : produccionesNuevas.entrySet()){
        for (String produccion : entry.getValue()) {
            try{
            addProduction(entry.getKey(),produccion);
            }catch(CFGAlgorithmsException f){
                System.out.println(f.getMessage());
            }
        }
    }
    
    //elimina las todas las producciones lambda y el axioma es anulable le añade la produccion S::='l'


    for (Map.Entry<Character,String> entry : eliminarProd.entrySet()){
        try {
            removeProduction(entry.getKey(),entry.getValue());
        }catch(CFGAlgorithmsException v){
            System.out.println(v.getMessage());
        }
    }
    try{
    if (anulables.contains(getStartSymbol())){
        addProduction(getStartSymbol(),"l");
    }
    }catch(CFGAlgorithmsException r){
        System.out.println(r.getMessage());
    }
    return anulables;
}


/**public List<Character> removeLambdaProductions() {
    List<Character> noTerminalesTratados = new ArrayList<>();
    Set<Character> lambdaNoTerminals = new HashSet<>();

    // Identificar los no terminales que tienen producciones lambda
    for (Map.Entry<Character, List<String>> entry : producciones.entrySet()) {
        char noTerminal = entry.getKey();
        List<String> listaProducciones = entry.getValue();
        if (listaProducciones.contains("l")) {
            lambdaNoTerminals.add(noTerminal);
            noTerminalesTratados.add(noTerminal);
        }
    }

    // Eliminar las producciones lambda excepto para el axioma si es necesario
    for (Character noTerminal : lambdaNoTerminals) {
        producciones.get(noTerminal).remove("l");
        // Si es el axioma y no tiene más producciones, mantenemos S::=l
        if (noTerminal.equals(axioma) && producciones.get(noTerminal).isEmpty()) {
            producciones.get(noTerminal).add("l");
        }
    }

    // Añadir nuevas producciones eliminando las apariciones de los no terminales que generan lambda
    Map<Character, List<String>> nuevasProduccionesMap = new HashMap<>();
    for (Map.Entry<Character, List<String>> entry : producciones.entrySet()) {
        char noTerminal = entry.getKey();
        List<String> nuevasProducciones = new ArrayList<>(entry.getValue());
        for (String produccion : entry.getValue()) {
            if (!produccion.equals("l")) {
                List<String> combinaciones = generarCombinaciones(produccion, lambdaNoTerminals);
                nuevasProducciones.addAll(combinaciones);
            }
        }
        // Eliminar duplicados y producciones vacías
        nuevasProducciones = nuevasProducciones.stream().distinct().filter(p -> !p.isEmpty()).collect(Collectors.toList());
        nuevasProduccionesMap.put(noTerminal, nuevasProducciones);
    }

    producciones = nuevasProduccionesMap;
    return noTerminalesTratados;
}

private List<String> generarCombinaciones(String produccion, Set<Character> lambdaNoTerminals) {
    List<String> combinaciones = new ArrayList<>();
    combinaciones.add(produccion);
    for (Character lambdaNonTerminal : lambdaNoTerminals) {
        if (produccion.indexOf(lambdaNonTerminal) != -1) {
            int size = combinaciones.size();
            for (int i = 0; i < size; i++) {
                String current = combinaciones.get(i);
                int index = current.indexOf(lambdaNonTerminal);
                while (index != -1) {
                    String nuevaProduccion = current.substring(0, index) + current.substring(index + 1);
                    if (!nuevaProduccion.isEmpty() && !combinaciones.contains(nuevaProduccion)) {
                        combinaciones.add(nuevaProduccion);
                    }
                    index = current.indexOf(lambdaNonTerminal, index + 1);
                }
            }
        }
    }
    return combinaciones;
}

    
*/

    /**
     * Método que comprueba si la gramática almacenada tiene reglas unitarias
     * (A::=B).
     *
     * @return True si contiene ese tipo de reglas
     */
    @Override
    public boolean hasUnitProductions() {
            for (Map.Entry<Character, List<String>> entry : producciones.entrySet()) {
                for (String produccion : entry.getValue()) {
                    if (produccion.length()==1){
                        for (Character noTerminal : getNonTerminals()){
                            if (produccion.charAt(0)== noTerminal){
                                return true;
                            }
                        }
                    }
                }
            }
            return false;

    }

    
    
    @Override
    public List<String> removeUnitProductions() {

        Map<Character, String> unitario = new HashMap<>();
        String unitariocambios= "";
        while(!unitario.toString().equals(unitariocambios)){
                unitariocambios=unitario.toString();
            for (Map.Entry<Character, List<String>> entry : producciones.entrySet()){
                for (String produccion : entry.getValue()) {
                    if (produccion.length() == 1 && getNonTerminals().contains(produccion.charAt(0))){
                        if (!unitario.containsKey(entry.getKey())){
                            unitario.put(entry.getKey(), produccion);
                        }else{
                            if (!unitario.get(entry.getKey()).contains(produccion)){
                                String prod = unitario.get(entry.getKey())+produccion;
                                unitario.put(entry.getKey(),prod);
                            }
                        }
                    }
                }
            }
// ahora en la variable unitario esta cada no terminal que tiene producciones unitarias y sus producciones unitarias correspondientes
            for (Map.Entry<Character, List<String>> entry : producciones.entrySet()){
                for (String produccion : entry.getValue()) {
                    if (produccion.length() == 2 && getNonTerminals().contains(produccion.charAt(0)) && getNonTerminals().contains(produccion.charAt(1))){
                        String var = unitario.get(entry.getKey());
                        if (var==null){
                            continue;
                        }
                        for (int i =0; i < var.length(); i++){
                            if (var.charAt(i)==produccion.charAt(0)){
                                if (!unitario.get(entry.getKey()).contains(produccion)){
                                String prod = unitario.get(entry.getKey())+produccion.charAt(1);
                                unitario.put(entry.getKey(),prod);
                                }
                            }
                            if (var.charAt(i)==produccion.charAt(1)){
                                if (!unitario.get(entry.getKey()).contains(produccion)){
                                String prod = unitario.get(entry.getKey())+produccion.charAt(0);
                                unitario.put(entry.getKey(), prod);
                                }
                            }
                        }
                    }
                }
            }
            for (Character noTerminal : unitario.keySet()){
                int tamanno = unitario.get(noTerminal).length();
                for (int j =0; j < tamanno; j++){
                    Character a = unitario.get(noTerminal).charAt(j);
                    if (getProductions(a)==null){
                        continue;
                    }
                    for (String newProduction : getProductions(a)){
                        try{
                            if ("l".equals(newProduction) && !noTerminal.equals(getStartSymbol())){
                                continue;
                            }
                                addProduction(noTerminal, newProduction);
                        }catch(CFGAlgorithmsException f){
                            if (f.getMessage()== "La produccion ya ha sido añadida previamente"){
                            }else{
                                System.out.println(f.getMessage());
                            }
                        }
                    }
                }
            }
        }
        List<String> eliminados = new ArrayList<>();
        for (Map.Entry<Character, List<String>> entry : producciones.entrySet()) {
            for (String produccion : entry.getValue()) {
                if (produccion.length()==1 && getNonTerminals().contains(produccion.charAt(0))){
                        String a = entry.getKey().toString() + "::=" + produccion;
                        eliminados.add(a);
                }
            }
        }
        for (String e : eliminados){
            try {
                removeProduction(e.charAt(0), e.charAt(4)+"" );
            }catch(CFGAlgorithmsException p){
                System.out.println(p.getMessage());
            }
        }
        return eliminados;   
    }
    /**
     * Método que elimina las reglas unitarias de la gramática almacenada.
     *
     * @return Devuelve una lista de producciones (un String de la forma "A::=B"
     *         por cada producción), con todas las reglas unitarias eliminadas.
     */
//    @Override
/**    public List<String> removeUnitProductions() {
        List<String> eliminados = new ArrayList<>();
        outerLoop:
       while (hasUnitProductions()==true){
            for (Map.Entry<Character, List<String>> entry : producciones.entrySet()) {  
                for (String produccion : entry.getValue()) {
                    if (produccion.length()==1){
                        for (Character noTerminal : getNonTerminals()){
                            if (produccion.charAt(0)== noTerminal){
                                //si llega aqui es porque entry.getkey() tiene una produccion unitaria
                                //es decir entry.getKey()::= produccion.charAt(0) es una produccion unitaria
                                eliminados.add(entry.getKey().toString() + "::=" + produccion);
                                System.out.print(eliminados);
                                try{
                                    if(getProductions(produccion.charAt(0))==null){
                                        throw new NullPointerException(produccion +" no tiene producciones");
                                    }
                                    for(String cambios : getProductions(produccion.charAt(0))){
                                        //cambios es cada una de las producciones de produccion.charAt(0)
                                        try{
                                            if ((cambios.length()== 1 && getNonTerminals().contains(cambios.charAt(0)))|| "l" == cambios){
                                                continue;
                                            }
                                            if (cambios.length() ==2 && (entry.getKey().equals(cambios.charAt(1)))){
                                                for (String a : getProductions(cambios.charAt(0))){
                                                    addProduction(entry.getKey(),a);
                                                }
                                            }
                                            if (cambios.length() ==2 && (entry.getKey().equals(cambios.charAt(0)))){
                                                for (String a : getProductions(cambios.charAt(1))){
                                                    addProduction(entry.getKey(),a);
                                                }
                                            }
                                            addProduction(entry.getKey(), cambios);
                                        }
                                        catch(CFGAlgorithmsException e){
                                            System.out.println(e.getMessage());
                                        }
                                    } 
                                }catch(NullPointerException e){
                                    try {removeNonTerminal(produccion.charAt(0));
                                    }catch(CFGAlgorithmsException f){
                                        System.out.println(f.getMessage());
                                    }
                                }
                                try{
                                    removeProduction(entry.getKey(),produccion);
                                }
                                catch(CFGAlgorithmsException e){
                                        System.out.println(e.getMessage());
                                    }
                                continue outerLoop;
                            }
                        }
                    }
                }
            }
        }
        return eliminados;
    }

*/

    /**
     * Método que transforma la gramática almacenada en una gramática bien
     * formada:
     * - 1. Elimina las reglas innecesarias.
     * - 2. Elimina las reglas no generativas.
     * - 3. Elimina las reglas unitarias.
     * - 4. Elimina los símbolo inútiles.
     */
    @Override
    public void transformToWellFormedGrammar() {
        removeUselessProductions();
        removeLambdaProductions();
        removeUnitProductions();
        removeUselessSymbols();
    }



    /**
     * Método que chequea que las producciones estén en Forma Normal de Chomsky.
     *
     * @param nonterminal A
     * @param production  A::=BC o A::=a (siendo B, C no terminales definidos
     *                    previamente y a terminal definido previamente). Se acepta S::=l si S es
     *                    no terminal y axioma.
     *
     * @throws CFGAlgorithmsException Si no se ajusta a Forma Normal de Chomsky
     *                                o si está compuesta por elementos
     *                                (terminales o no terminales) no definidos
     *                                previamente.
     * la cadena production solo contiene la parte derecha de la produccion, no toda la produccion como pone
     */
    @Override
    public void checkCNFProduction(char nonterminal, String production) throws CFGAlgorithmsException {

        if (!getNonTerminals().contains(nonterminal)){
            throw new CFGAlgorithmsException ("el no terminal no está definido previamente");
        }
        if (production.length() > 2){
            throw new CFGAlgorithmsException("la produccion no tiene la longitud adecuada");
        }
        if (production.length() == 1 && !getTerminals().contains(production.charAt(0))){
            if (!getStartSymbol().equals(nonterminal) && !production.equals('l')){
                throw new CFGAlgorithmsException("el caracter no es terminal");
            }
            
        }
        if (production.length()==2 && (getTerminals().contains(production.charAt(0)) || getTerminals().contains(production.charAt(0)))){
            throw new CFGAlgorithmsException("la produccion contiene dos elementos y  uno de ellos es terminal");
        }
        if (production.length()==2 && (production.charAt(0)==nonterminal  || production.charAt(1)==nonterminal)){
            throw new CFGAlgorithmsException("hay un caracter en la produccion igual al caracter que la produce");
        }
        else {
        }
    }



    /**
     * Método que comprueba si la gramática dada de alta se encuentra en Forma
     * Normal de Chomsky. Es una precondición para la aplicación del algoritmo
     * CYK.
     *
     * @return true Si la gramática está en Forma Normal de Chomsky
     */
    @Override
    public boolean isCNF() {
        for (Map.Entry<Character, List<String>> entry : producciones.entrySet()) {  
            for (String produccion : entry.getValue()) {
                try{
                    checkCNFProduction(entry.getKey(), produccion);
                }catch(CFGAlgorithmsException e){
                    return false;
                }
            }
        }
        return true;
    }


    //no añado hasLambdaProductions porque si el axioma produce lambda es gramatica bien formada y hasLambdaProductions()==true
    public boolean esGramaticaBienFormada(){
        return !hasUselessProductions() && !hasUnitProductions();
    }
    
    //este metodo devuelve una lista con los caracteres en mayuscula que no estan utilizados en la gramatica
    public List<Character> caracteresMayusculasLibres(){
        List<Character> caracteresDisponibles = new ArrayList();
        Set<Character> noTerminalesUsados=getNonTerminals();
        for (Character libre='A'; libre<='Z'; libre++){
            if (!noTerminalesUsados.contains(libre)){
                caracteresDisponibles.add(libre);
            }
        }
        return caracteresDisponibles;
    }
    
    //este metodo crea los terminales que produccen un terminal y añade las producciones a la gramatica
    //devuelve una lista con los terminales que se han añadido 
    public List<Character> producirTerminalesSolitarios(){
        List<Character> caracteresLibres = caracteresMayusculasLibres();
        List<Character> noTerminalesNuevos = new ArrayList();
        int i =0;
        for (Character terminal: getTerminals()){
            try{
                addNonTerminal(caracteresLibres.get(i));
                addProduction(caracteresLibres.get(i), terminal.toString());
                noTerminalesNuevos.add(caracteresLibres.get(i));
                
                i++;
            }
            catch(CFGAlgorithmsException e){
                System.out.println(e.getMessage());
            }
        }
        return noTerminalesNuevos;
    }
    
    public boolean tieneTerminalesEnProducciones(String prod){
        for (int i=0; i<prod.length(); i++){
            if (getTerminals().contains(prod.charAt(i))){
                return true;
            }
        }
        return false;
    }
    
    //metodo que cambia las producciones que contienen terminales por su no terminal correspondiente
    public void actualizarProducciones(){
        List<Character> noTerminalesAnnadidos = producirTerminalesSolitarios();
        Map<Character, List<String>> copiaProducciones = producciones;
        for (Map.Entry<Character, List<String>> entry : copiaProducciones.entrySet()) {
            for (int i=0; i<entry.getValue().size();i++){
            
                if(!noTerminalesAnnadidos.contains(entry.getKey())){
                    String produccion=entry.getValue().get(i);
                    // poner algo
                    while(tieneTerminalesEnProducciones(produccion)){
                        String nuevaProduccion = "";
                        for (Character a : noTerminalesAnnadidos){
                            nuevaProduccion = produccion.replace(getProductions(a).get(0), a.toString());
                        try{
                            addProduction(entry.getKey(),nuevaProduccion);
                            if(removeProduction(entry.getKey(), produccion)){
                                i--;
                            }                         
                            produccion=nuevaProduccion;
                        }catch(CFGAlgorithmsException e){
                            System.out.println(e.getMessage());
                        }
                        }
                    }
                }
                if (i<0){
                    i=-1;
                }
            }
        }
    }
    
    /**
     * Método que transforma la gramática almacenada en su Forma Normal de
     * Chomsky equivalente.
     *
     * @throws CFGAlgorithmsException Si la gramática de la que partimos no es
     *                                una gramática bien formada.
     */
    //no pasa los test pero creo que es porque el metodo transformToWellFormedGrammar() da errores
    @Override
    public void transformIntoCNF() throws CFGAlgorithmsException {
        
    if (!esGramaticaBienFormada()) {
        throw new CFGAlgorithmsException("La gramatica no es una gramática bien formada");
    }
    
    if (!(isCNF() == true)) {
        removeLambdaProductions();
        removeUnitProductions();
        removeUselessSymbols();
        actualizarProducciones();
        decomposeLongProductions();
    }
    
    //System.out.println(getGrammar());
}

    private void updateProductions() throws CFGAlgorithmsException {
    // Implement the logic to replace terminals within productions.
    List<Character> caracteresLibres = caracteresMayusculasLibres();
    Map<Character, Character> terminalReplacements = new HashMap<>();

    for (Character terminal : setTerminal) {
        Character newNonTerminal = caracteresLibres.remove(0);
        terminalReplacements.put(terminal, newNonTerminal);
        addNonTerminal(newNonTerminal);
        addProduction(newNonTerminal, terminal.toString());
    }

    for (Map.Entry<Character, List<String>> entry : producciones.entrySet()) {
        List<String> updatedProductions = new ArrayList<>();
        for (String produccion : entry.getValue()) {
            StringBuilder updatedProduction = new StringBuilder();
            for (char symbol : produccion.toCharArray()) {
                if (setTerminal.contains(symbol)) {
                    updatedProduction.append(terminalReplacements.get(symbol));
                } else {
                    updatedProduction.append(symbol);
                }
            }
            updatedProductions.add(updatedProduction.toString());
        }
        entry.setValue(updatedProductions);
    }
}
    
    private void decomposeLongProductions() throws CFGAlgorithmsException {
    List<Character> caracteresLibres = caracteresMayusculasLibres();
    boolean updated = true;

    while (updated) {
        updated = false;
        Map<Character, List<String>> copiaProducciones = new HashMap<>();
        for (Map.Entry<Character, List<String>> entry : producciones.entrySet()) {
            copiaProducciones.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }

        for (Map.Entry<Character, List<String>> entry : copiaProducciones.entrySet()) {
            for (String produccion : entry.getValue()) {
                if (produccion.length() > 2) {
                    Character newNonTerminal = caracteresLibres.remove(0);
                    addNonTerminal(newNonTerminal);
                    String newProduction = produccion.substring(0, 2);
                    addProduction(newNonTerminal, newProduction);
                    String updatedProduction = newNonTerminal + produccion.substring(2);
                    removeProduction(entry.getKey(), produccion);
                    addProduction(entry.getKey(), updatedProduction);
                    updated = true;
                }
            }
        }
    }
}

    

    /**
     * Método que indica si una palabra pertenece al lenguaje generado por la
     * gramática que se ha introducido. Se utilizará el algoritmo CYK para
     * decidir si la palabra pertenece al lenguaje.
     *
     * La gramática deberá estar en FNC.
     *
     * @param word La palabra a verificar, tiene que estar formada sólo por
     *             elementos no terminales.
     *
     * @return TRUE si la palabra pertenece, FALSE en caso contrario
     *
     * @throws CFGAlgorithmsException Si la palabra proporcionada no está
     *                                formada sólo por terminales, si está formada por terminales que no
     *                                pertenecen al conjunto de terminales definido para la gramática
     *                                introducida, si la gramática es vacía o si el autómata carece de axioma.
     */
    @Override
    public boolean isDerivedUsignCYK(String word) throws CFGAlgorithmsException {
        
        if (word.isEmpty() || axioma == null) {
            throw new CFGAlgorithmsException("La palabra es vacía o el axioma no está definido.");
        }

        int n = word.length();
        Set<Character>[][] table = new HashSet[n][n];

        // Inicializo la tabla con conjuntos vacíos
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                table[i][j] = new HashSet<>();
            }
        }

        // Lleno la base de la recursión
        for (int i = 0; i < n; i++) {
            char terminal = word.charAt(i);
            if (!setTerminal.contains(terminal)) {
                throw new CFGAlgorithmsException("La palabra contiene terminales no definidos en la gramática.");
            }
            for (Character nonTerminal : setNonTerminal) {
                if (producciones.get(nonTerminal) != null) {
                    for (String production : producciones.get(nonTerminal)) {
                        if (production.equals(String.valueOf(terminal))) {
                            table[0][i].add(nonTerminal);
                        }
                    }
                }
            }
        }

        // Lleno la tabla para subcadenas más largas
        for (int l = 2; l <= n; l++) { // l es la longitud de la subcadena
            for (int i = 0; i <= n - l; i++) {
                for (int j = 1; j <= l - 1; j++) {
                    Set<Character> bSet = table[j - 1][i];
                    Set<Character> cSet = table[l - j - 1][i + j];
                    for (Character b : bSet) {
                        for (Character c : cSet) {
                            for (Character nonTerminal : setNonTerminal) {
                                if (producciones.get(nonTerminal) != null) {
                                    for (String production : producciones.get(nonTerminal)) {
                                        if (production.length() == 2 && production.charAt(0) == b && production.charAt(1) == c) {
                                            table[l - 1][i].add(nonTerminal);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Compruebo si el axioma está en la última celda
        return table[n - 1][0].contains(axioma);
    }



    /**
     * Método que, para una palabra, devuelve un String que contiene todas las
     * celdas calculadas por el algoritmo CYK (la visualización debe ser similar
     * al ejemplo proporcionado en el PDF que contiene el paso a paso del
     * algoritmo).
     *
     * @param word La palabra a verificar, tiene que estar formada sólo por
     *             elementos no terminales.
     *
     * @return Un String donde se vea la tabla calculada de manera completa,
     *         todas las celdas que ha calculado el algoritmo.
     *
     * @throws CFGAlgorithmsException Si la palabra proporcionada no está
     *                                formada sólo por terminales, si está formada por terminales que no
     *                                pertenecen al conjunto de terminales definido para la gramática
     *                                introducida, si la gramática es vacía o si carece de axioma.
     */
    @Override
    public String algorithmCYKStateToString(String word) throws CFGAlgorithmsException {
      
        if (word.isEmpty() || axioma == null) {
            throw new CFGAlgorithmsException("La palabra es vacía o el axioma no está definido.");
        }

        if (!isCNF()) {
            throw new CFGAlgorithmsException("La gramática no está en Forma Normal de Chomsky.");
        }

        int n = word.length();
        Set<Character>[][] table = new HashSet[n][n];

        // Inicializar la tabla con conjuntos vacíos
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                table[i][j] = new HashSet<>();
            }
        }

        StringBuilder stateBuilder = new StringBuilder();

        // Lleno la base de la recursión
        for (int i = 0; i < n; i++) {
            char terminal = word.charAt(i);
            if (!setTerminal.contains(terminal)) {
                throw new CFGAlgorithmsException("La palabra contiene terminales no definidos en la gramática.");
            }
            for (Character nonTerminal : setNonTerminal) {
                if (producciones.get(nonTerminal) != null) {
                    for (String production : producciones.get(nonTerminal)) {
                        if (production.equals(String.valueOf(terminal))) {
                            table[0][i].add(nonTerminal);
                        }
                    }
                }
            }
            stateBuilder.append(getTableState(table, n)).append("\n");
        }

        // Lleno la tabla para subcadenas más largas
        for (int l = 2; l <= n; l++) { // l es la longitud de la subcadena
            for (int i = 0; i <= n - l; i++) {
                for (int j = 1; j <= l - 1; j++) {
                    Set<Character> bSet = table[j - 1][i];
                    Set<Character> cSet = table[l - j - 1][i + j];
                    for (Character b : bSet) {
                        for (Character c : cSet) {
                            for (Character nonTerminal : setNonTerminal) {
                                if (producciones.get(nonTerminal) != null) {
                                    for (String production : producciones.get(nonTerminal)) {
                                        if (production.length() == 2 && production.charAt(0) == b && production.charAt(1) == c) {
                                            table[l - 1][i].add(nonTerminal);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                stateBuilder.append(getTableState(table, n)).append("\n");
            }
        }

        return stateBuilder.toString();
    }

    private String getTableState(Set<Character>[][] table, int n) {
        StringBuilder state = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                state.append("Cell[").append(i).append("][").append(j).append("]: ");
                state.append(table[i][j].toString());
                state.append(" ");
            }
            state.append("\n");
        }
        return state.toString();
    }
        
}