package es.ceu.gisi.modcomp.gic_algorithms;

import es.ceu.gisi.modcomp.gic_algorithms.exceptions.CFGAlgorithmsException;
import es.ceu.gisi.modcomp.gic_algorithms.interfaces.*;
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
    Set<Character> setTerminal = new HashSet<>();
    Map<Character, List<String>> producciones = new HashMap<>();
    char nonterminal;
    Character axioma = null;
    List<Character> setOrdenado = new ArrayList<>(nonterminal);
    List<Character> nonTerminals = new ArrayList<>();
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
            setNonTerminal.add(nonterminal);
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
        nonTerminals.remove(Character.valueOf(nonterminal));
        nonTerminals.add(0,nonterminal);
        axioma=nonterminal;
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
        producciones.putIfAbsent(nonterminal, new ArrayList<>());
        List<String> produccionesList = producciones.get(nonterminal);
        if(!produccionesList.contains(production)) {
            produccionesList.add(production);
        } else {
            throw new CFGAlgorithmsException("El valor ya estaba en la produccion");
        }
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
        String producciones = nonterminal + "::=";
        List<String> var = getProductions(nonterminal);
        Collections.sort(var);
        for(int j=0; j<var.size();j++){
        producciones=producciones+var.get(j);
            if(j<var.size()-1){
                producciones +="|";
            }
        }
        return producciones;
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
        String producciones = getProductionsToString(nonterminal).trim();
        
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
        
        
    return true;
    }



    /**
     * Método que elimina las reglas innecesarias de la gramática almacenada.
     *
     * @return Devuelve una lista de producciones (un String de la forma "A::=A"
     *         por cada producción), con todas las reglas innecesarias
     *         eliminadas.
     */
    public List<String> removeUselessProductions() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    /**
     * Método que elimina los símbolos inútiles de la gramática almacenada.
     *
     * @return Devuelve una lista con todos los símbolos no terminales y
     *         terminales eliminados.
     */
    public List<Character> removeUselessSymbols() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    /**
     * Método que comprueba si la gramática almacenada tiene reglas no
     * generativas (reglas lambda). Excepto S::=l si sólo es para reconocer la
     * palabra vacía.
     *
     * @return True si contiene ese tipo de reglas
     */
    public boolean hasLambdaProductions() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    public List<Character> removeLambdaProductions() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    /**
     * Método que comprueba si la gramática almacenada tiene reglas unitarias
     * (A::=B).
     *
     * @return True si contiene ese tipo de reglas
     */
    public boolean hasUnitProductions() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    /**
     * Método que elimina las reglas unitarias de la gramática almacenada.
     *
     * @return Devuelve una lista de producciones (un String de la forma "A::=B"
     *         por cada producción), con todas las reglas unitarias eliminadas.
     */
    public List<String> removeUnitProductions() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    /**
     * Método que transforma la gramática almacenada en una gramática bien
     * formada:
     * - 1. Elimina las reglas innecesarias.
     * - 2. Elimina las reglas no generativas.
     * - 3. Elimina las reglas unitarias.
     * - 4. Elimina los símbolo inútiles.
     */
    public void transformToWellFormedGrammar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
     */
    public void checkCNFProduction(char nonterminal, String production) throws CFGAlgorithmsException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    /**
     * Método que comprueba si la gramática dada de alta se encuentra en Forma
     * Normal de Chomsky. Es una precondición para la aplicación del algoritmo
     * CYK.
     *
     * @return true Si la gramática está en Forma Normal de Chomsky
     */
    public boolean isCNF() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    /**
     * Método que transforma la gramática almacenada en su Forma Normal de
     * Chomsky equivalente.
     *
     * @throws CFGAlgorithmsException Si la gramática de la que partimos no es
     *                                una gramática bien formada.
     */
    public void transformIntoCNF() throws CFGAlgorithmsException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    public boolean isDerivedUsignCYK(String word) throws CFGAlgorithmsException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    public String algorithmCYKStateToString(String word) throws CFGAlgorithmsException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
