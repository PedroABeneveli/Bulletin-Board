package br.unb.cic.wc16

import org.scalatest.funsuite.AnyFunSuite
import scala.collection.mutable.HashMap
import java.io.ByteArrayOutputStream

class WordFrequencyCounterTest extends AnyFunSuite {
    test("Testando inserir uma palavra no contador pela primeira vez") {
        var em = new EventManager
        var wfc = new WordFrequencyCounter(em)
        em.publish(Array("valid_word", "exemplo"))

        var expected = HashMap("exemplo" -> 1)
        
        assert(expected == wfc.contador_de_palavras)
    }


    test("Testando inserir palavras no contador mais de uma vez") {
        var em = new EventManager
        var wfc = new WordFrequencyCounter(em)       
        em.publish(Array("valid_word", "exemplo1"))
        em.publish(Array("valid_word", "exemplo2"))
        em.publish(Array("valid_word", "exemplo1"))
        em.publish(Array("valid_word", "exemplo2"))

        var expected = HashMap("exemplo1" -> 2, "exemplo2" -> 2)

        assert(expected == wfc.contador_de_palavras)
    }
    
}