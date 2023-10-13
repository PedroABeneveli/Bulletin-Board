package br.unb.cic.wc16

import org.scalatest.funsuite.AnyFunSuite

class StopWordFilterTest extends AnyFunSuite {

    test("Testando se uma stop word eh detectada") {
        var em = new EventManager
        var swf = new StopWordFilter(em)
        val f1 = (n: Array[String]) => {
            val f2 = (n: Array[String]) => println("Hello World")
            em.subscribe("teste", f2)
        }
        em.subscribe("valid_word", f1)
        swf.stopWords += "banana"
        
        em.publish(Array("word", "banana"))
        assert(!em.inscricoes.contains("teste"))
    }

    test("Testando se uma palavra que nao eh stopword publica o evento valid_word") {
        var em = new EventManager
        var swf = new StopWordFilter(em)
        val f1 = (n: Array[String]) => {
            val f2 = (n: Array[String]) => println("Hello World")
            em.subscribe("teste", f2)
        }
        em.subscribe("valid_word", f1)
        swf.stopWords += "maca"
        
        em.publish(Array("word", "banana"))
        assert(em.inscricoes.contains("teste"))
    }

}