
import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import org.homermultitext.edmodel._


val corpus = CorpusSource.fromFile("data/hmt_2cols.tsv")
val mainScholia = corpus ~~ CtsUrn("urn:cts:greekLit:tlg5026.msA:")

val tokens = TeiReader.fromCorpus(mainScholia)
val badtokens = tokens.filter(_.analysis.errors.size > 0)
val report = badtokens.map(_.analysis.errorReport("\t")).mkString("\n")

// Quick dump to file:
import java.io._
new PrintWriter("hmt-errors.tsv") { write(report); close }
