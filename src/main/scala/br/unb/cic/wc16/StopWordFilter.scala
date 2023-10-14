package br.unb.cic.wc16

import scala.collection.mutable.Set
import scala.io.Source

class StopWordFilter(em: EventManager) {
    var stopWords = Set[String]()           // pensei em usar set pra um acesso mais rapido que array
    em.subscribe("load", load)
    em.subscribe("word", isStopWord)

    def isStopWord(evento: Array[String]): Unit = {
        var palavra = evento(1)
        if (!stopWords.contains(palavra))
            em.publish(Array("valid_word", palavra))
    }

    def load(evento: Array[String]): Unit = {
        var path = evento(2)
        Source.fromFile(path).getLines().toList.foreach(s => stopWords += s)
    }

}