package br.unb.cic.wc16

import scala.collection.mutable.Set

class StopWordFilter(em: EventManager) {
    private var stopWords = Set[String]()           // pensei em usar set pra um acesso mais rapido que array
    em.subscribe("load", load)
    em.subscribe("word", isStopWord)

    def isStopWord(evento: Array[String]): Unit = {
        var palavra = evento(1)
        if (!stopWords.contains(palavra))
            em.publish(Array("valid_word", palavra))
    }

    def load(evento: Array[String]): Unit = {
        var temp = 2
    }

}