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

    test("Testando o load_stop_words com o arquivo stop-word.txt") {
        var em = new EventManager
        var swf = new StopWordFilter(em)
        em.publish(Array("load", "", "target/scala-2.13/test-classes/stop-words.txt"))

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

    test("Testando se uma palavra eh filtrada pela stop word carregada do arquivo stop-word.txt") {
        var em = new EventManager
        var swf = new StopWordFilter(em)
        val f1 = (n: Array[String]) => {
            val f2 = (n: Array[String]) => println("Hello World")
            em.subscribe("teste", f2)
        }
        
        em.subscribe("valid_word", f1)
        em.publish(Array("load", "", "target/scala-2.13/test-classes/stop-words.txt"))
        em.publish(Array("word", "through"))

        assert(!em.inscricoes.contains("teste"))
    }

}