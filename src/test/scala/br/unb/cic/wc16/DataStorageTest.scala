package br.unb.cic.wc16

import org.scalatest.funsuite.AnyFunSuite

class DataStorageTest extends AnyFunSuite {

    test("Testando uma palavra eh publicada, e se o evento 'eof' eh publicado") {
        var em = new EventManager
        var ds = new DataStorage(em)
        val f1 = (n: Array[String]) => {
            val f2 = (n: Array[String]) => println(n(1))
            em.subscribe(n(1), f2)
        }
        val f3 = (n: Array[String]) => {
            val f4 = (n: Array[String]) => println("Hello World")
            em.subscribe("test2", f4)
        }
        em.subscribe("word", f1)
        em.subscribe("eof", f3)

        ds.palavras = "cic"
        em.publish(Array("start"))

        assert(em.inscricoes.contains("cic") && em.inscricoes.contains("test2"))
    }

    test("Testando o load de um arquivo e sua padronizacao") {
        var em = new EventManager
        var ds = new DataStorage(em)
        em.publish(Array("load", "target/scala-2.13/test-classes/small-text.txt", ""))

        val expected = "long before time had a name the first spinjitzu master created ninjago using four elemental weapons hello my name is pedro "
        assert(ds.palavras == expected)
    }

    test("Testando se as palavras apos o 'load' sao publicadas corretamente no evento 'start'") {
        var em = new EventManager
        var ds = new DataStorage(em)
        val f1 = (n: Array[String]) => {
            val f2 = (n: Array[String]) => println(n(1))
            em.subscribe(n(1), f2)
        }
        em.subscribe("word", f1)
        em.publish(Array("load", "target/scala-2.13/test-classes/small-text.txt", ""))
        em.publish(Array("start"))

        var temTodasPalavras = true
        var palavras = "long before time had a name the first spinjitzu master created ninjago using four elemental weapons hello my name is pedro "

        // se nao encontrar uma, entao a variavel fica falsa e tornara o teste errado
        palavras.split(" ").foreach(elem => temTodasPalavras &= em.inscricoes.contains(elem))
        
        assert(temTodasPalavras)
    }

}
