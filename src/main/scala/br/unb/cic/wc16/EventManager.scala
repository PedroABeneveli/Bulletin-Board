package br.unb.cic.wc16

import scala.collection.mutable.HashMap
import scala.collection.mutable.Set

class EventManager {

    var inscricoes = HashMap[String, Set[(Array[String]) => Unit]]()

    // funcao que vai receber o evento que quer se inscrever, e a funcao, que recebe um array e retorna nada, ja que qualquer retorno seria feito com o publish
    def subscribe(evento: String, handler: (Array[String]) => Unit): Unit = {
        if (inscricoes.contains(evento))
            inscricoes(evento) += handler
        else 
            inscricoes.addOne(evento -> Set[(Array[String]) => Unit](handler))
    }

}