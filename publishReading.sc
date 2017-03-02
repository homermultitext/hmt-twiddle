/*
the `publish` functions takes 2 arguments:

1. String identifier for a scholion group (e.g., 'msA', 'msAim' ...)
2. String for type of publication, either "diplomatic" or "normalized"
*/

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.orca._
import edu.holycross.shot.greek._
import edu.holycross.shot.gsphone._
import org.homermultitext.edmodel._

val urnLevel = 3 // e.g., book.scholion.part;  set to 2 to get book.scholion.
val corpus = CorpusSource.fromFile("data/hmt_2cols.tsv")
val orca = OrcaSource.fromFile("data/critsignorca.tsv")

case class TextTriple (urn: CtsUrn, reading: String, seq: Int)

def tokensForDocument(scholGroupId:String, pubType: String): Vector[(CtsUrn, String)] = {
  // create a new, subset Corpus with URN twiddling, e.g.,
  val scholia = corpus ~~ CtsUrn("urn:cts:greekLit:tlg5026.msA:")
  // analyze a corpus and create a sequence of TokenAnalysis objects:
  println("\nAnalyzing corpus for " + scholGroupId + "...\n")
  val tokens = TeiReader.fromCorpus(scholia)

  pubType match {
    case "diplomatic" =>   tokens.map(ta => (ta.textNode.collapseTo(urnLevel),ta.analysis.readWithDiplomatic))
    case "normalized" =>   tokens.map(ta => (ta.textNode.collapseTo(urnLevel),ta.analysis.readWithAlternate))
  }

}

def passagesFromTokens(readingPairs: Vector[(CtsUrn, String)]) = {
  val triples = readingPairs.zipWithIndex.map( v  => TextTriple(v._1._1, v._1._2, v._2))
  val trVect = triples.groupBy(_.urn).toSeq.toVector
  trVect.map{ case (k,v) =>(k, v.sortBy(_.seq).map(_.reading).mkString(" ") ) }
}

def idxForUrn(u: CtsUrn, urnSeq: Vector[(CtsUrn, Int)]) = {
  urnSeq.filter(_._1 == u)(0)._2
}

def publishable(scholionGroup: String, publType: String) : Vector[CitableNode] = {
  val tkns =tokensForDocument(scholionGroup, publType)
  val passages = passagesFromTokens(tkns)
  // For final sort:
  val urnSeq = passages.map(_._1).distinct.zipWithIndex

  val sortedFinal = passages.sortBy{ case (k,v) => idxForUrn(k, urnSeq) }
  sortedFinal.map { case (k,v) => CitableNode(k,v)}
}

def publish(scholionGroup: String, pubType: String): Unit = {
  def citableText = publishable(scholionGroup,pubType)
  def s = citableText.map(cn => s."${cn.urn}\t${cn.text}").mkString("\n")
}
