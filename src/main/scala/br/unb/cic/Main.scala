package br.unb.cic

import br.unb.cic.wc16.{EventManager, DataStorage, StopWordFilter, WordFrequencyApplication, WordFrequencyCounter}
import org.backuity.clist._

object Main extends CliMain[Unit] (
    name="Word Count",
    description="uma implementacao simples do problema word count, usando o estilo de programacao \"Bulletin Board\"") {

    var filePath = arg[String](description = "caminho para o arquivo que tera a frequencia contada")
    var stopWordsPath = arg[String](description = "caminho para o arquivo das stop words, as palavras que nao serao contadas")
    
    def run: Unit = {
        var em = new EventManager
        var swf = new StopWordFilter(em)
        var wfc = new WordFrequencyCounter(em)
        var ds = new DataStorage(em)
        var wfa = new WordFrequencyApplication(em)

        em.publish(Array("run", filePath, stopWordsPath))
    }
}