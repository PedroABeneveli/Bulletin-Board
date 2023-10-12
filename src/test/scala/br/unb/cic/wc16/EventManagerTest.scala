package br.unb.cic.wc16

import org.scalatest.funsuite.AnyFunSuite

class EventManagerTest extends AnyFunSuite {

    test("Testando adicionar uma inscricao nova") {
        val func = (n: Array[String]) => println("Hello World")
        val em = new EventManager()
        em.subscribe("test", func)
        val expected = Set[(Array[String]) => Unit](func)

        assert(expected == em.inscricoes("test"))
    }

    test("Testando adicionar uma inscricao em um evento existente") {
        val func1 = (n: Array[String]) => println("Hello World")
        val func2 = (n: Array[String]) => println(n(0))
        val em = new EventManager()
        em.subscribe("test", func1)
        em.subscribe("test", func2)
        val expected = Set[(Array[String]) => Unit](func1, func2)

        assert(expected == em.inscricoes("test"))
    }

}