package br.unb.cic.wc16

import org.scalatest.funsuite.AnyFunSuite
import scala.collection.mutable.HashMap
import scala.collection.mutable.Set

class IntermoduleTest extends AnyFunSuite {

    test("Testando a relacao entre WordFrequencyApplication e StopWordFilter, se stop-word.txt eh lido em algum momento apos 'run'") {
        var em = new EventManager
        var swf = new StopWordFilter(em)
        var wfa = new WordFrequencyApplication(em)
        em.publish(Array("run", "", "target/scala-2.13/test-classes/stop-words.txt"))

        val expected = Set("i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", 
                        "yours", "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", 
                        "hers", "herself", "it", "its", "itself", "they", "them", "their", "theirs", 
                        "themselves", "what", "which", "who", "whom", "this", "that", "these", "those", 
                        "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", 
                        "having", "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", 
                        "or", "because", "as", "until", "while", "of", "at", "by", "for", "with", "about", 
                        "against", "between", "into", "through", "during", "before", "after", "above", 
                        "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", 
                        "again", "further", "then", "once", "here", "there", "when", "where", "why", "how", 
                        "all", "any", "both", "each", "few", "more", "most", "other", "some", "such", "no", 
                        "nor", "not", "only", "own", "same", "so", "than", "too", "very", "s", "t", "can", 
                        "will", "just", "don", "should", "now")

        assert(expected == swf.stopWords)
    }

    test("Testando a passagem de palavra valida do StopWordFilter pro WordFrequencyCounter") {
        var em = new EventManager
        var swf = new StopWordFilter(em)
        var wfc = new WordFrequencyCounter(em)
        em.publish(Array("load", "", "target/scala-2.13/test-classes/stop-words.txt"))        
        em.publish(Array("word", "uva"))

        val expected = HashMap("uva" -> 1)

        assert(expected == wfc.contador_de_palavras)
    }

    test("Testando a passagem de palavra invalida do StopWordFilter pro WordFrequencyCounter") {
        var em = new EventManager
        var swf = new StopWordFilter(em)
        var wfc = new WordFrequencyCounter(em)
        em.publish(Array("load", "", "target/scala-2.13/test-classes/stop-words.txt"))        
        em.publish(Array("word", "now"))

        val expected = HashMap[String, Int]()

        assert(expected == wfc.contador_de_palavras)
    }

    test("Testando se o StopWordFilter filtra duas palavras do DataStorage, uma sendo 'valid_word' e a outra nao") {
        var em = new EventManager
        var ds = new DataStorage(em)
        var swf = new StopWordFilter(em)
        val f1 = (n: Array[String]) => {
            val f2 = (n: Array[String]) => println(n(1))
            em.subscribe(n(1), f2)
        }
        em.subscribe("valid_word", f1)
        swf.stopWords += "numero"
        ds.palavras = "numero agua"
        em.publish(Array("start"))

        assert(em.inscricoes.contains("agua"))
    }

    test("Testando o load simultaneo do StopWordFilte e DataStorage, e realizando a filtragem do arquivo") {
        var em = new EventManager
        var ds = new DataStorage(em)
        var swf = new StopWordFilter(em)
        val f1 = (n: Array[String]) => {
            val f2 = (n: Array[String]) => println(n(1))
            em.subscribe(n(1), f2)
        }
        em.subscribe("valid_word", f1)
        em.publish(Array("load", "target/scala-2.13/test-classes/small-text.txt", "target/scala-2.13/test-classes/stop-words.txt"))
        em.publish(Array("start"))

        assert(em.inscricoes.contains("long") && em.inscricoes.contains("time") && em.inscricoes.contains("name") 
                && em.inscricoes.contains("first") &&em.inscricoes.contains("spinjitzu") 
                && em.inscricoes.contains("master") && em.inscricoes.contains("created") 
                && em.inscricoes.contains("ninjago") && em.inscricoes.contains("using") 
                && em.inscricoes.contains("four") && em.inscricoes.contains("elemental") 
                && em.inscricoes.contains("weapons") && em.inscricoes.contains("hello") 
                && em.inscricoes.contains("name") && em.inscricoes.contains("pedro"))
    }

    test("Testando se as palavras do DS filtradas pelo SWF sao contadas corretamente pelo WFC") {
        var em = new EventManager
        var ds = new DataStorage(em)
        var swf = new StopWordFilter(em)
        var wfc = new WordFrequencyCounter(em)
        em.publish(Array("load", "target/scala-2.13/test-classes/small-text.txt", "target/scala-2.13/test-classes/stop-words.txt"))
        em.publish(Array("start"))

        val expected = HashMap("long" -> 1, "time" -> 1, "name" -> 2, "first" -> 1, "spinjitzu" -> 1, 
                            "master" -> 1, "created" -> 1, "ninjago" -> 1, "using" -> 1, "four" -> 1, 
                            "elemental" -> 1, "weapons" -> 1, "hello" -> 1, "pedro" -> 1)

        assert(expected == wfc.contador_de_palavras)
    }

    test("Testando se os modulos DS, WFC e SWF funcionam corretamente em conjunto apos serem ativados pelo WFA") {
        var em = new EventManager
        var ds = new DataStorage(em)
        var swf = new StopWordFilter(em)
        var wfc = new WordFrequencyCounter(em)
        var wfa = new WordFrequencyApplication(em)

        // para que o print que ocorre apos o evento 'eof' nao ocorra e atrapalhe a legibilidade dos testes, vou retirar a inscricao do wfc no evento 'print'
        em.inscricoes("print") = Set[Array[String] => Unit]()

        em.publish(Array("run", "target/scala-2.13/test-classes/small-text.txt", "target/scala-2.13/test-classes/stop-words.txt"))
        val expected = HashMap("long" -> 1, "time" -> 1, "name" -> 2, "first" -> 1, "spinjitzu" -> 1, 
                            "master" -> 1, "created" -> 1, "ninjago" -> 1, "using" -> 1, "four" -> 1, 
                            "elemental" -> 1, "weapons" -> 1, "hello" -> 1, "pedro" -> 1)
        
        assert(expected == wfc.contador_de_palavras)
    }

}