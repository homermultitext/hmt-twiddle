// To use this script, set this to value a two-column text source,
// and load this script in sbt console with `:load sliding-ngram-histo-all.sc`
val srcData = "data/byzorthoized.tsv"

// then in the console use one of these three methods:
/*

1. printHisto(n: Int, cutoff: Int = 2, noPunctuation: Boolean = true)

Example:

  printHisto(3)

prints all 3-grams occurring more than 2 times after omitting punctuation in
computing ngrams


2. printUrns(gram: String, noPunctuation: Boolean = true)

Example:

    printUrns("ὅτι Ζηνόδοτος γράφει")

prints URNs of all passages where the 3-gram "ὅτι Ζηνόδοτος γράφει" occurs after
omitting punctuation in computing ngrams.


3. printText(u: String)

Example:

    printText("urn:cts:greekLit:tlg5026.msA.hmt:9.616.comment")

prints the text content of the specified passage

*/



//////////// no need to alter anythng below here ///////////////////////////////

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

def textForUrn(twoCols: Vector[Vector[String]], u: String): String = {
  val res = twoCols.filter(_(0) == u).map(_(1))
  res.size match {
    case 1 => res(0)
    case 0 => "No match for " + u
    case _ => "Don't understand ambiguous result " + res
  }
}
def printText(u: String): Unit = {
  println(textForUrn(twoColSrc, u))
}
