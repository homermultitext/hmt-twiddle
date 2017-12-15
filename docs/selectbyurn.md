---
layout: page
title: "Using hmt-twiddle"
---



## Analyze a corpus of texts

If you look at the `loadhmt.sc` script, you'll see that it simply uses the `ohco2` library to create a corpus of the whole HMT project from a delimimted text file:

    val corpus = CorpusSource.fromFile("data/hmtarchive_2cols.tsv")

You can use URN twiddling to select some subset of texts as a corpus:

    val mainScholia = corpus ~~ CtsUrn("urn:cts:greekLit:tlg5026.msA:")
