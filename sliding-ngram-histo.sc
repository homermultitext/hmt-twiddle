val srcData = "data/scholia-normalized.tsv"

import scala.io.Source
val scholiaSrc = Source.fromFile(srcData).getLines.toVector.map(_.split("\t").toVector)



// Given a vector of strings and a value n, extract all ngrams containing 'hoti'
def ngramsWHoti(v: Vector[String], n: Int): Vector[String] = {
  v.sliding(n,1).toVector.map(_.mkString(" ")).filter(_.contains("ὅτι"))
}

//scholiaSrc.map(v => ngramsWHoti(v(1).split("\\s+").toVector,2)).filter(_.size > 0).flatten



def ngramHisto(scholia: Vector[Vector[String]], n: Int) = {
  // Vector of Vector of strings
  val textVect = scholia.map(_(1).trim.split("\\s+").toVector)
  val grams = textVect.map(v => ngramsWHoti(v,n)).filterNot(_.isEmpty).flatten
  val hist = grams.groupBy(phr => phr).map{ case (k,v) => (k,v.size) }.toSeq.sortBy(_._2).reverse
  hist.toVector
}



def printHisto(n: Int) : Unit = {
  val ngHisto = ngramHisto(scholiaSrc, n)
  val multi = ngHisto.filter{ case (str,i) => i > 1 }
  for (phrase <- multi) {
    println(phrase._1 + " " + phrase._2)
  }
}
