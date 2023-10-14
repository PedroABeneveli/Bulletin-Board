package br.unb.cic.wc16

import scala.collection.mutable.HashMap
import scala.collection.mutable.Set

class EventManager {

    var inscricoes = HashMap[String, Set[(Array[String]) => Unit]]()

    // funcao que vai receber o evento que quer se inscrever, e a funcao, que recebe um array e retorna nada, ja que qualquer retorno seria feito com o publish
    def subscribe(tipoEvento: String, handler: (Array[String]) => Unit): Unit = {
        if (inscricoes.contains(tipoEvento))
            inscricoes(tipoEvento) += handler
        else 
            inscricoes += (tipoEvento -> Set[Array[String] => Unit](handler))
    }

    // o evento eh uma array de strings porque vai ser o nome do evento + os argumentos daquele evento
    def publish(evento: Array[String]): Unit = {
        var tipoEvento = evento(0)
        if (inscricoes.contains(tipoEvento))
            for (func <- inscricoes(tipoEvento))
                func(evento)
    }

}