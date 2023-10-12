package br.unb.cic.wc16

import org.scalatest.funsuite.AnyFunSuite

class EventManagerTest extends AnyFunSuite {

    test("Testando adicionar uma inscricao nova") {
        val func = (n: Array[String]) => println("Hello World")
        val em = new EventManager
        em.subscribe("test", func)
        val expected = Set[(Array[String]) => Unit](func)

        assert(expected == em.inscricoes("test"))
    }

    test("Testando adicionar uma inscricao em um evento existente") {
        val func1 = (n: Array[String]) => println("Hello World")
        val func2 = (n: Array[String]) => println(n(0))
        val em = new EventManager
        em.subscribe("test", func1)
        em.subscribe("test", func2)
        val expected = Set[(Array[String]) => Unit](func1, func2)

        assert(expected == em.inscricoes("test"))
    }

    test("Teste de inscrever a mesma funcao em eventos distintos") {
        val func = (n: Array[String]) => println("Hello World")
        val em = new EventManager
        em.subscribe("test", func)
        em.subscribe("test2", func)
        val expected = Set[(Array[String]) => Unit](func)

        assert(expected == em.inscricoes("test") && expected == em.inscricoes("test2"))
    }

    test("Testando o publish, vendo se um evento eh inscrito ao executar a funcao do evento 'test'") {
        val em = new EventManager
        val func = (n: Array[String]) => {
            val func2 = (n: Array[String]) => println("Hello World")
            em.subscribe("test2", func2)
        }

        em.subscribe("test", func)
        em.publish(Array("test"))

        assert(em.inscricoes.contains("test2"))
    }

    test("Verificando se varias funcoes de um mesmo evento sao chamadas no publish") {
        val em = new EventManager
        val f1 = (n: Array[String]) => {
            val f2 = (n: Array[String]) => println(n(1))
            em.subscribe(n(1), f2)
        }
        val f3 = (n: Array[String]) => {
            val f4 = (n: Array[String]) => println("Hello World")
            em.subscribe("test2", f4)
        }
        em.subscribe("test", f1)
        em.subscribe("test", f3)
        em.publish(Array("test", "tp2"))

        assert(em.inscricoes.contains("test2") && em.inscricoes.contains("tp2"))
    }

    test("Verificando se uma funcao em um evento diferente nao eh chamada quando ocorre outro evento") {
        val em = new EventManager
        val f1 = (n: Array[String]) => {
            val f2 = (n: Array[String]) => println(n(1))
            em.subscribe(n(1), f2)
        }
        val f3 = (n: Array[String]) => {
            val f4 = (n: Array[String]) => println("Hello World")
            em.subscribe("test2", f4)
        }
        em.subscribe("test", f1)
        em.subscribe("test3", f3)
        em.publish(Array("test", "tp2"))

        assert(!em.inscricoes.contains("test2") && em.inscricoes.contains("tp2"))
    }

}