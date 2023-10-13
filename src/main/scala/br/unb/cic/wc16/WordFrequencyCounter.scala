package br.unb.cic.wc16

import scala.collection.mutable.HashMap
import scala.collection.mutable.Set
import scala.compiletime.ops.int
import scala.collection.mutable.ListMap

class WordFrequencyCounter {

    var contador_de_palavras = HashMap[String, Int]()
    var em = new EventManager
    em.subscribe("palavra_valida", incrementarContagem)
    em.subscribe("print", mostraFrequencias)

    def incrementarContagem(evento: Array[String]): Unit = {
        var palavra = evento(1)
        if (contador_de_palavras.contains(palavra)) 
            contador_de_palavras(palavra) += 1
        else    
            contador_de_palavras(palavra) = 1
    }

    def mostraFrequencias(evento: Array[String]): Unit = {
        var contador_de_palavras_ordenada = ListMap(contador_de_palavras.toSeq().sortWith(_._2 > _._2):_*)
        contador_de_palavras_ordenada.foreach(println)
    }   

}