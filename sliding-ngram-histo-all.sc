// a two-column text source
val srcData = "data/byzorthoized.tsv"

import scala.io.Source

// A vector of urn+text pairs. Each pair is itself a vector.
val twoColSrc = Source.fromFile(srcData).getLines.toVector.map(_.split("\t").toVector)
// Extract component parts into parallel sequences:
val urns = twoColSrc.map(_(0))
val strings = twoColSrc.map(_(1).trim)




/** Create a sequence of ngrams for a sequence of strings.
* @param v Strings to make ngrams from
* @param n size of ngram
* @return vector of
*/
def ngrams(v: Vector[String], n: Int): Vector[String] = {
  v.sliding(n,1).toVector.map(_.mkString(" "))
}

// This is specific to HMT definition of allowable chars
/** convert strings to vectors of words
* @param passages sequence of strings
* @param skipPunct true if punctuation should be omitted
* @param return sequence of word vectors
*/
def passageToWords(passages: Vector[String], skipPunct: Boolean): Vector[Vector[String]] = {
  val punctList = "·.,:⁚‡".toList.map(_.toString)
  if (skipPunct) {
    passages.map(_.split("\\s+").toVector.filterNot(punctList.contains(_)))
  } else {
    passages.map(_.split("\\s+").toVector)
  }
}

/**
* @param strings Vector  of strings
* @param n size of ngram desired
* @param dropPunctuation true if punctuation should be omitted from ngrams
* @return a vector of word+count pairs sorted from high to low
*/
def ngramHisto(strings: Vector[String], n: Int, dropPunctuation: Boolean = true): Vector[(String, Int)] = {
  val words = passageToWords(strings,dropPunctuation)
  val allGrams = words.map(v => ngrams(v,n)).filterNot(_.isEmpty).flatten
  // guarantee length after filtering empties:
  val grams = allGrams.filter(_.split(" ").size == n)

  val histogram = grams.groupBy(phr => phr).map{ case (k,v) => (k,v.size) }.toSeq.sortBy(_._2).reverse
  histogram.toVector
}

def urnsForNGram(strings: Vector[String], gram: String, dropPunctuation: Boolean = true): Vector[String] = {
  val n = gram.split(" ").size
  val words = passageToWords(strings,dropPunctuation)
  val allGrams = words.map(v => ngrams(v,n))
  val gramPairs = urns.zip(allGrams)
  gramPairs.filter(_._2.contains(gram)).map(_._1)
}


def printHisto(n: Int, cutoff: Int = 2, noPunctuation: Boolean = true) : Unit = {
  val ngHisto = ngramHisto(strings, n, noPunctuation)
  val multi = ngHisto.filter{ case (str,i) => i > cutoff }
  for (phrase <- multi) {
    println(phrase._1 + " " + phrase._2)
  }
}

def printUrns(gram: String, noPunctuation: Boolean = true): Unit = {
  val n = gram.split(" ").size
  val urnList = urnsForNGram(strings,gram,noPunctuation)
  println(s"\nThe ${n}-gram ${gram} occurs ${urnList.size} time(s).\n")
  for (u <- urnList) {
    println(u)
  }
}
