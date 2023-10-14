package br.unb.cic.wc16

import org.scalatest.funsuite.AnyFunSuite
import scala.collection.mutable.HashMap

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

}