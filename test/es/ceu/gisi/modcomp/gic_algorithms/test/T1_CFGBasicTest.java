package es.ceu.gisi.modcomp.gic_algorithms.test;

import es.ceu.gisi.modcomp.gic_algorithms.CFGAlgorithms;
import es.ceu.gisi.modcomp.gic_algorithms.exceptions.CFGAlgorithmsException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.rules.ExpectedException;



/**
 * Clase que testea el correcto funcionamiento de la implementación del
 * almacenamiento y procesamiento de una gramática independiente del contexto.
 *
 * El objetivo de estos tests es comprobar si la implementación del alumno en la
 * realización de su clase GICAlgorithms cumple con los requisitos básicos
 * respecto a este aspecto.
 *
 * El código del alumno, no obstante, será comprobado con tests adicionales.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 */
public class T1_CFGBasicTest {

    private CFGAlgorithms gica;



    public T1_CFGBasicTest() throws IOException, FileNotFoundException, CFGAlgorithmsException {
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();



    @Test
    public void comprobarAniadirTerminalValido() throws CFGAlgorithmsException {
        gica = new CFGAlgorithms();
        gica.addTerminal('a');
    }



    @Test
    public void comprobarAniadirTerminalNoValido1() throws CFGAlgorithmsException {
        thrown.expect(CFGAlgorithmsException.class);
        gica = new CFGAlgorithms();
        gica.addTerminal('0');
    }



    @Test
    public void comprobarAniadirTerminalNoValido2() throws CFGAlgorithmsException {
        thrown.expect(CFGAlgorithmsException.class);
        gica = new CFGAlgorithms();
        gica.addTerminal('A');
    }



    @Test
    public void comprobarAniadirTerminalNoValido3() throws CFGAlgorithmsException {
        thrown.expect(CFGAlgorithmsException.class);
        gica = new CFGAlgorithms();
        gica.addTerminal('a');
        gica.addTerminal('a');
    }

    @Test
    public void comprobarEliminarnoTerminalValido1() throws CFGAlgorithmsException {
        gica = new CFGAlgorithms();
        gica.addNonTerminal('A');
        gica.removeNonTerminal('A');
        assertTrue(gica.getNonTerminals().isEmpty());
    }
    
    @Test
    public void comprobarEliminarnoTerminalNoValido1() throws CFGAlgorithmsException {
        gica = new CFGAlgorithms();
        gica.addNonTerminal('A');
        gica.removeNonTerminal('A');
        assertFalse(gica.getNonTerminals().contains('A'));
        
}

    @Test
    public void comprobarEliminarTerminalValido1() throws CFGAlgorithmsException {
        gica = new CFGAlgorithms();
        gica.addTerminal('a');

        gica.removeTerminal('a');

        assertTrue(gica.getTerminals().isEmpty());
    }
    
    

    @Test
    public void comprobarEliminarTerminalValido2() throws CFGAlgorithmsException {
        gica = new CFGAlgorithms();
        gica.addTerminal('a');
        gica.addTerminal('b');
        gica.addNonTerminal('S');
        gica.addNonTerminal('A');
        gica.addProduction('S', "ASa");
        gica.addProduction('S', "b");
        gica.addProduction('S', "l");
        gica.addProduction('A', "l");
        gica.addProduction('A', "a");

        gica.removeTerminal('a');

        assertTrue(gica.getTerminals().size() == 1);
        assertTrue(gica.getTerminals().contains('b'));
        assertEquals("S::=ASa|b|l", gica.getProductionsToString('S'));
        assertEquals("A::=a|l", gica.getProductionsToString('A'));
    }



    @Test
    public void comprobarAniadirNoTerminalValido() throws CFGAlgorithmsException {
        gica = new CFGAlgorithms();
        gica.addNonTerminal('S');
    }



    @Test
    public void comprobarAniadirNoTerminalNoValido1() throws CFGAlgorithmsException {
        thrown.expect(CFGAlgorithmsException.class);
        gica = new CFGAlgorithms();
        gica.addNonTerminal('a');
    }



    @Test
    public void comprobarAniadirNoTerminalNoValido2() throws CFGAlgorithmsException {
        thrown.expect(CFGAlgorithmsException.class);
        gica = new CFGAlgorithms();
        gica.addNonTerminal('0');
    }



    @Test
    public void comprobarAniadirNoTerminalNoValido3() throws CFGAlgorithmsException {
        thrown.expect(CFGAlgorithmsException.class);
        gica = new CFGAlgorithms();
        gica.addNonTerminal('S');
        gica.addNonTerminal('S');
    }



    @Test
    public void comprobarAxiomaNoEstablecido() throws CFGAlgorithmsException {
        thrown.expect(CFGAlgorithmsException.class);
        gica = new CFGAlgorithms();
        gica.addNonTerminal('S');
        gica.getStartSymbol();
    }



    @Test
    public void comprobarEstablecerAxiomaValido() throws CFGAlgorithmsException {
        gica = new CFGAlgorithms();
        gica.addNonTerminal('S');
        gica.setStartSymbol('S');
        System.out.println(gica.getNonTerminals());
        assertTrue(gica.getStartSymbol() == 'S');
    }



    @Test
    public void comprobarEstablecerAxiomaNoValido1() throws CFGAlgorithmsException {
        thrown.expect(CFGAlgorithmsException.class);
        gica = new CFGAlgorithms();
        gica.getNonTerminals().add('A');
        gica.setStartSymbol('S');
    }



    @Test
    public void comprobarEstablecerAxiomaNoValido2() throws CFGAlgorithmsException {
        thrown.expect(CFGAlgorithmsException.class);
        gica = new CFGAlgorithms();
        gica.addNonTerminal('A');
        gica.addTerminal('a');
        gica.setStartSymbol('S');
    }



    @Test
    public void comprobarEstablecerAxiomaNoValido3() throws CFGAlgorithmsException {
        thrown.expect(CFGAlgorithmsException.class);
        gica = new CFGAlgorithms();
        gica.addNonTerminal('A');
        gica.addTerminal('a');
        gica.setStartSymbol('a');
    }



    @Test
    public void comprobarAniadirProduccionValida1() throws CFGAlgorithmsException {
        gica = new CFGAlgorithms();
        gica.addTerminal('a');
        gica.addNonTerminal('S');
        gica.addNonTerminal('A');
        gica.addProduction('S', "ASa");
        gica.addProduction('S', "a");

        assertEquals("S::=ASa|a", gica.getProductionsToString('S'));

    }



    @Test
    public void comprobarAniadirProduccionValida2() throws CFGAlgorithmsException {
        gica = new CFGAlgorithms();
        gica.addTerminal('a');
        gica.addNonTerminal('S');
        gica.addNonTerminal('A');
        gica.addProduction('S', "ASa");
        gica.addProduction('S', "a");
        gica.addProduction('S', "l");
        gica.addProduction('A', "l");

        assertEquals("S::=ASa|a|l", gica.getProductionsToString('S'));
        assertEquals("A::=l", gica.getProductionsToString('A'));
    }



    @Test
    public void comprobarAniadirProduccionNoValida1() throws CFGAlgorithmsException {
        thrown.expect(CFGAlgorithmsException.class);
        gica = new CFGAlgorithms();
        gica.addTerminal('a');

        gica.addNonTerminal('S');
        gica.addNonTerminal('A');

        gica.addProduction('S', "b");
        
    } //este test comprueba q se haga assert equals de b, cuando no se ha metido
    //mete a como terminal, pero luego no lo llama al hacer addProduction.
    //y la b previamente no ha sido añadida.



    @Test
    public void comprobarAniadirProduccionNoValida2() throws CFGAlgorithmsException {
        thrown.expect(CFGAlgorithmsException.class);
        gica = new CFGAlgorithms();
        gica.addTerminal('a');

        gica.addNonTerminal('S');
        gica.addNonTerminal('A');

        gica.addProduction('C', "a");
    } //este test comprueba q se haga assert equals de C, cuando no se ha metido



    @Test
    public void comprobarAniadirProduccionNoValida3() throws CFGAlgorithmsException {
        thrown.expect(CFGAlgorithmsException.class);
        gica = new CFGAlgorithms();
        gica.addTerminal('a');

        gica.addNonTerminal('S');
        gica.addNonTerminal('A');
        gica.addProduction('S', "AB");
    }//este test eleva debe elevar una excepcion ya que AB 



    @Test
    public void comprobarAniadirProduccionNoValida4() throws CFGAlgorithmsException {
        thrown.expect(CFGAlgorithmsException.class);
        gica = new CFGAlgorithms();
        gica.addTerminal('a');

        gica.addNonTerminal('S');
        gica.addNonTerminal('B');

        gica.addProduction('S', "Aa");
    }



    @Test
    public void comprobarAniadirProduccionNoValida5() throws CFGAlgorithmsException {
        thrown.expect(CFGAlgorithmsException.class);
        gica = new CFGAlgorithms();
        gica.addTerminal('a');

        gica.addNonTerminal('S');
        gica.addNonTerminal('A');
        gica.addProduction('A', "aABS");
    }



    @Test
    public void comprobarAniadirProduccionNoValida6() throws CFGAlgorithmsException {
        thrown.expect(CFGAlgorithmsException.class);
        gica = new CFGAlgorithms();
        gica.addTerminal('a');

        gica.addNonTerminal('S');
        gica.addNonTerminal('A');

        gica.addProduction('A', "aSbB");
    }



    @Test
    public void comprobarAniadirProduccionNoValida7() throws CFGAlgorithmsException {
        thrown.expect(CFGAlgorithmsException.class);
        gica = new CFGAlgorithms();
        gica.addTerminal('a');

        gica.addNonTerminal('S');
        gica.addNonTerminal('A');

        gica.addProduction('A', "SAAb");
    }



    @Test
    public void comprobarAniadirProduccionNoValida8() throws CFGAlgorithmsException {
        thrown.expect(CFGAlgorithmsException.class);
        gica = new CFGAlgorithms();
        gica.addTerminal('a');

        gica.addNonTerminal('S');
        gica.addNonTerminal('A');

        gica.addProduction('A', "SA");
        gica.addProduction('A', "SA");
    }


    /*
    @Test 
    public void comprobarGramatica() throws CFGAlgorithmsException {
    gica = new CFGAlgorithms();
    gica.addTerminal('a');
    gica.addNonTerminal('A');
    gica.addNonTerminal('S');
    gica.setStartSymbol('S');
    gica.addProduction('S', "ASa");
    gica.addProduction('A', "Aa");
    gica.addProduction('S', "a");
    assertEquals("S::=ASa|a", gica.getProductionsToString('S'));
    }
    */
    
    /*
    @Test 
    public void comprobarBorrarGramatica() throws CFGAlgorithmsException {
    thrown.expect(CFGAlgorithmsException.class);
    gica = new CFGAlgorithms();
    gica.addTerminal('a');
    gica.addNonTerminal('A');
    gica.addNonTerminal('S');
    gica.setStartSymbol('S');
    gica.addProduction('S', "ASa");
    gica.addProduction('A', "Aa");
    gica.addProduction('S', "a");
    gica.deleteGrammar();
    gica.deleteGrammar();
   }
   */
    
    @Test
    public void comprobarEliminaProduccionValida1() throws CFGAlgorithmsException {
        gica = new CFGAlgorithms();
        gica.addTerminal('a');
        gica.addNonTerminal('S');
        gica.addNonTerminal('A');
        gica.addProduction('S', "ASa");
        gica.addProduction('S', "a");

        gica.removeProduction('S', "ASa");
        assertEquals("S::=a", gica.getProductionsToString('S'));

    }



    @Test
    public void comprobarEliminaProduccionValida2() throws CFGAlgorithmsException {
        gica = new CFGAlgorithms();
        gica.addTerminal('a');
        gica.addNonTerminal('S');
        gica.addNonTerminal('A');
        gica.addProduction('S', "ASa");
        gica.addProduction('S', "a");

        gica.removeProduction('S', "a");
        assertEquals("S::=ASa", gica.getProductionsToString('S'));
    }



    @Test
    public void comprobarEliminaProduccionNoValida1() throws CFGAlgorithmsException {
        thrown.expect(CFGAlgorithmsException.class);
        gica = new CFGAlgorithms();
        gica.addTerminal('a');
        gica.addNonTerminal('S');
        gica.addNonTerminal('A');
        gica.addProduction('S', "ASa");
        gica.addProduction('S', "a");

        gica.removeProduction('S', "b");
    }


    @Test
    public void comprobarRecuperarnoterminales() throws CFGAlgorithmsException {
        gica = new CFGAlgorithms();
        gica.addNonTerminal('A');
        gica.getNonTerminals();
        assertTrue(!gica.getNonTerminals().isEmpty());
    }
          
    @Test
    public void comprobarRecuperarTerminales() throws CFGAlgorithmsException {
        gica = new CFGAlgorithms();
        gica.addTerminal('a');
        gica.getTerminals();
        assertTrue(!gica.getTerminals().isEmpty());
    }
    
    @Test
    public void comprobarRecuperarAxiomaValido() throws CFGAlgorithmsException {
        gica = new CFGAlgorithms();
        gica.addTerminal('a');
        gica.addNonTerminal('A');
        gica.setStartSymbol('A');
        gica.getStartSymbol();
        assertTrue(gica.getStartSymbol() == 'A');
    }
    
    @Test
    public void comprobarRecuperarProducciones() throws CFGAlgorithmsException {
        gica = new CFGAlgorithms();
        gica.addNonTerminal('S');
        gica.addNonTerminal('A');
        gica.addNonTerminal('B');
        gica.addNonTerminal('C');

        gica.addTerminal('a');
        gica.addTerminal('b');

        gica.setStartSymbol('S');

        gica.addProduction('S', "AB");
        gica.addProduction('S', "BC");

        gica.addProduction('A', "BA");
        gica.addProduction('A', "a");

        gica.addProduction('B', "CC");
        gica.addProduction('B', "b");

        gica.addProduction('C', "AB");
        gica.addProduction('C', "a");

        assertEquals("S::=AB|BC", gica.getProductionsToString('S'));
        assertEquals("A::=BA|a", gica.getProductionsToString('A'));
        assertEquals("B::=CC|b", gica.getProductionsToString('B'));
        assertEquals("C::=AB|a", gica.getProductionsToString('C'));

    }



    @Test
    public void comprobarEliminarGramaticaValido() throws CFGAlgorithmsException {
        gica = new CFGAlgorithms();
        gica.addNonTerminal('S');
        gica.addNonTerminal('A');
        gica.addNonTerminal('B');
        gica.addNonTerminal('C');

        gica.addTerminal('a');
        gica.addTerminal('b');

        gica.setStartSymbol('S');

        gica.addProduction('S', "AB");
        gica.addProduction('S', "BC");

        gica.addProduction('A', "BA");
        gica.addProduction('A', "a");

        gica.addProduction('B', "CC");
        gica.addProduction('B', "b");

        gica.addProduction('C', "AB");
        gica.addProduction('C', "a");

        gica.deleteGrammar();

        assertEquals("S::=", gica.getProductionsToString('S'));
        assertEquals("A::=", gica.getProductionsToString('A'));
        assertEquals("B::=", gica.getProductionsToString('B'));
        assertEquals("C::=", gica.getProductionsToString('C'));

        gica.addNonTerminal('S');
        gica.addNonTerminal('A');
        gica.addNonTerminal('B');
        gica.addNonTerminal('C');

        gica.addTerminal('a');
        gica.addTerminal('b');
    }



    @Test
    public void comprobarEsGICValido1() throws CFGAlgorithmsException {
        gica = new CFGAlgorithms();
        gica.addNonTerminal('S');
        gica.addNonTerminal('A');
        gica.addNonTerminal('B');
        gica.addNonTerminal('C');
        gica.addNonTerminal('D');

        gica.addTerminal('a');
        gica.addTerminal('b');
        gica.addTerminal('c');

        gica.setStartSymbol('S');

        gica.addProduction('S', "aAbB");
        gica.addProduction('S', "C");

        gica.addProduction('A', "A");
        gica.addProduction('A', "abab");

        gica.addProduction('B', "CC");
        gica.addProduction('B', "l");

        gica.addProduction('C', "Baaa");
        gica.addProduction('C', "CaD");

        assertTrue(gica.isCFG());
    }
    
    @Test
    public void comprobarHasUselessProductions() throws CFGAlgorithmsException {
    gica = new CFGAlgorithms();
    gica.addNonTerminal('A');
    gica.addProduction('A', "A");
    assertTrue(gica.hasUselessProductions());   
    }
    
    @Test
    public void comprobarEliminarUselessProductions() throws CFGAlgorithmsException {
    gica = new CFGAlgorithms(); 
    gica.addNonTerminal('A');     
    gica.addProduction('A', "A");
        List<String> expected = new ArrayList<>();
        expected.add("A::=A");
    assertEquals(expected, gica.removeUselessProductions()); 
    }
    
    /*
    @Test
    public void testRemoveLambdaProductions() throws CFGAlgorithmsException {
        gica = new CFGAlgorithms();  
        
        gica.addNonTerminal('A');
        gica.addNonTerminal('B');
        gica.addNonTerminal('C');
        gica.addTerminal('a');
        gica.addTerminal('b');
 
        gica.addNonTerminal('S');
        gica.setStartSymbol('S');
 
        gica.addProduction('S', "aA");
        gica.addProduction('S', "l");
        gica.addProduction('A', "bB");
        gica.addProduction('B', "l");
        gica.addProduction('C', "S");
        

        List<Character> noTerminalesTratados = gica.removeLambdaProductions();

        assertTrue(noTerminalesTratados.contains('B'));
        assertTrue(noTerminalesTratados.contains('S')); 
    }
    */
    
    @Test 
    public void comprobarGramaticaBienFormada() throws CFGAlgorithmsException{
    //hasUselessProductions
    gica = new CFGAlgorithms();
    gica.addNonTerminal('A');
    gica.addProduction('A', "A");
    assertTrue(gica.hasUselessProductions());
    
    //removeLambdaProductions();
    
    //removeUnitProductions();

    //removeUselessSymbols();
    
    }
}
