package br.unb.cic.wc16

import scala.io.Source
import scala.util.matching.Regex

class DataStorage(eventManager: EventManager) {
    var event_manager = eventManager;
    var palavras: String

    event_manager.subscribe(("load", load))
    event_manager.subscribe("start", produce_words)

    // carrega as palavras do arquivo e as salva no atributo 'palavras'
    def load(evento: Array[String]): Unit = {
        val path = evento(1);
        val fonte = Source.fromFile(path)

        // retorna uma string com as palavras separadas por espaço
        palavras = fonte.mkString(" ") 

        val pattern = new Regex("[\\W_]+")
        palavras = pattern.replaceAllIn(palavras, " ").toLowerCase()

        fonte.close() // fecha a fonte
    }

    // vai pedir para publicar todas as palavras do atributo 'palavras'
    def produce_words(evento: Array[String]): Unit = {
        val lista_palavras = palavras.split(" ")

        // publica cada palavra do atributo 'palavras' pelo event manager
        lista_palavras.foreach(elem => event_manager.publish("word", elem))
        event_manager.publish("eof", null)
    }
}