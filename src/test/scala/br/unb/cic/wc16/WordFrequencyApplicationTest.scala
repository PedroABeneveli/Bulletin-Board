package br.unb.cic.wc16

import org.scalatest.funsuite.AnyFunSuite

class WordFrequencyApplicationTest extends AnyFunSuite {

    test("Testando a reacao ao evento 'run' e verificando o publish do metodo run") {
        var em = new EventManager
        var wfa = new WordFrequencyApplication(em)
        val f1 = (n: Array[String]) => {
            val f2 = (n: Array[String]) => println(n(1))
            em.subscribe(n(1) ++ n(2), f2)
        }
        val f3 = (n: Array[String]) => {
            val f4 = (n: Array[String]) => println("Hello World")
            em.subscribe("test2", f4)
        }
        em.subscribe("load", f1)
        em.subscribe("start", f3)

        em.publish(Array("run", "te", "st"))

        assert(em.inscricoes.contains("test") && em.inscricoes.contains("test2"))
    }

    test("Testando a reacao do metodo stop ao evento 'eof'") {
        var em = new EventManager
        var wfa = new WordFrequencyApplication(em)
        val f1 = (n: Array[String]) => {
            val f2 = (n: Array[String]) => println("Hello World")
            em.subscribe("test", f2)
        }
        em.subscribe("print", f1)

        em.publish(Array("eof"))
        assert(em.inscricoes.contains("test"))
    }

}