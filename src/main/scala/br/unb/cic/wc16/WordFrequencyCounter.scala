package br.unb.cic.wc16

import scala.collection.mutable.HashMap
import scala.collection.mutable.Set
import scala.collection.mutable.ListMap

class WordFrequencyCounter(em: EventManager) {
    
    var contador_de_palavras = HashMap[String, Int]()
    em.subscribe("valid_word", incrementarContagem)
    em.subscribe("print", mostraFrequencias)

    def incrementarContagem(evento: Array[String]): Unit = {
        var palavra = evento(1)
        if (contador_de_palavras.contains(palavra)) 
            contador_de_palavras(palavra) += 1
        else    
            contador_de_palavras(palavra) = 1
    }

    def mostraFrequencias(evento: Array[String]): Unit = {
        var contador_de_palavras_ordenada = ListMap(contador_de_palavras.toSeq.sortWith(_._2 > _._2):_*)
        contador_de_palavras_ordenada.foreach(println)
    }   

}