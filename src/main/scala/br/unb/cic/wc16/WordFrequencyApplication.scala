package br.unb.cic.wc16

class WordFrequencyApplication(em: EventManager) {
    em.subscribe("run", run)
    em.subscribe("eof", stop)

    def run(evento: Array[String]): Unit = {
        var pathTexto = evento(1)
        var pathStopWords = evento(2)
        em.publish(Array("load", pathTexto, pathStopWords))
        em.publish(Array("start"))
    }

    def stop(evento: Array[String]): Unit = {
        em.publish(Array("print"))
    }

}