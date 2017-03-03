val srcData = "data/scholia-normalized.tsv"

import scala.io.Source
val scholiaSrc = Source.fromFile(srcData).getLines.toVector.map(_.split("\t").toVector)

def hotiNGrams(scholia: Vector[Vector[String]], n: Int) : Vector[Vector[String]]= {
  val reduced = scholia.map( v => Vector(v(0), v(1).trim.split("\\s+").slice(0,n).mkString(" ")).toVector)
  reduced.filter(_(1).contains("ὅτι"))
}


def ngramHisto(scholia: Vector[Vector[String]], n: Int) = {
  val grams = hotiNGrams(scholia,n)
  val phrases = grams.map(_(1))
  val hist = phrases.groupBy(phr => phr).map{ case (k,v) => (k,v.size) }.toSeq.sortBy(_._2).reverse
  hist.toVector
}


def printHisto(n: Int) : Unit = {
  val ngHisto = ngramHisto(scholiaSrc, n)
  val multi = ngHisto.filter{ case (str,i) => i > 1 }
  for (phrase <- multi) {
    println(phrase._1 + " " + phrase._2)
  }
}


//
// usage:
// val ngrams = hotiNGrams(scholiaSrc, <NVALUE>)
//
// ngrams is
