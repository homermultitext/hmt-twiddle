
import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.greek._
import edu.holycross.shot.gsphone._

val corpus = CorpusSource.fromFile("data/flag/iliad-oct.tsv")
val wordVect = corpus.nodes.map(cn => cn.text.split(" "))
val urns = corpus.nodes.map(_.urn)
val greekWords = wordVect.map(v => v.map { LiteraryGreekString(_) } )
val gwVects = greekWords.map( _.toVector)
val syllableVects = gwVects.map(LGSyllable.syllabify(_))
val pitchPatterns= syllableVects.map(v => v.map(_.accent.getOrElse("_")).mkString)


// print the whole thing:  println(pitchPatternVect.mkString("\n"))
// look up by passage:

def pitchForPassage(u: CtsUrn): String = {
  try {
    val strings = citablePitch.filter(_._1 == u).map(_._2)
    strings
  } catch {
    case _ : Throwable => "Could not find a passage for " + s
  }
}

def printPitch(s: String) {
  val u = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.omar:" + s)
  val cnVect = corpus.nodes.filter(_.urn == u)
  for (cn <- cnVect) {
    val count = urns.indexOf(cn.urn)
    val sylls = syllableVects(count)
    println(sylls.map(_.ucode).mkString("-"))
    println(pitchPatterns(count).mkString + "\n")
  }
}
