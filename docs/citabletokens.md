---
layout: page
title: "How to create a corpus of citable tokens"
---


## Create a Vector of analyzes

A single line of code can analyze any corpus of texts that follows HMT markup conventions. The result is a Vector of `TokenAnalysis` objects.

    val tokens = TeiReader.fromCorpus(mainScholia)



## Convert a Vector of analyses into a new text corpus citable by token

From a vector of analyses, you can create a new corpus of texts citable by token.  The token analysis object has methods for creating text nodes using alternate readings, where they exist, or using purely diplomatic readings.  This two-liner creates a corpus of normalized readings:

    val citableNodes = tokens.map(_.readWithAlternate)
    val normalizedCorpus = Corpus(citableNodes)

Here is the same pattern creating a text with purely diplomatic readings:

    val citableNodes = tokens.map(_.readWithDiplomatic)
    val diplomaticCorpus = Corpus(citableNodes)
