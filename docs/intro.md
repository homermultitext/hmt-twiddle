---
layout: page
title: "Using hmt-twiddle"
---


## Introduction and prerequisites

The `hmt-twiddle` repository sets up a Scala build environment for interactive work with HMT data sets in an sbt console.  You need both [Scala](https://www.scala-lang.org/) and the interactive build tool, [sbt](https://www.scala-sbt.org/), to use it.

You can load the HMT project code libraries and the data sets including in this repository by:

1.  starting an sbt console (run `sbt console` from a terminal)
2.  at the console prompt, enter `loadhmt.sc`
3.

This loads all the code libraries you need, and creates two data objects:

1. `corpus` : the complete HMT Greek text corpus.
2. `orca` : an alignment of all critical signs with Iliadic lines

This guide illustrates a few ways you can work with these objects.

## Main libraries

`hmt-twiddle` loads the `hmt-textmodel` library, for working with HMT text contents.  It in turn relies on the generic `ohco2` library for two important  constructs:

1. a `Corpus` of texts, made from a Vector of `CitableNode`s.
2. the `CitableNode` object, a single citable passage of text with URN and text content


The top-level concept of the `hmt-textmodel` is the `TokenAnalysis`.  A `TokenAnalysis` has two members: a CTS URN identifying the text, and a complex `HmtToken` with analytical data. The `HmtToken` captures everything expressed about a token by HMT project XML editions, but the `TokenAnalysis`  has high-level functions (illustrated here) for working with its contents.  These functions either:

- *filter* a Vector of analyses to create a new Vector of analyses, or
- *transform* a Vector of analyses to a Vector of citable text nodes



## Overview: analyze a corpus of texts


The `loadhmt.sc` script uses the `ohco2` library to create a corpus of the whole HMT project:

    val corpus = CorpusSource.fromFile("data/hmtarchive_2cols.tsv")

You can use URN twiddling to select some subset of texts as a corpus:

    val mainScholia = corpus ~~ CtsUrn("urn:cts:greekLit:tlg5026.msA:")

A single line of code can analyze any corpus of texts that follows HMT markup conventions. The result is a Vector of `TokenAnalysis` objects.

    val tokens = TeiReader.fromCorpus(mainScholia)




## Turn a vector of analyses into a new text corpus citable by token

From a vector of analyses, you can create a new corpus of texts citable by token.  The token analysis object has methods for creating text nodes using alternate readings, where they exist, or using purely diplomatic readings.  Here is a two-liner to create a corpus of normalized readings:

    val citableNodes = tokens.map(_.readWithAlternate)
    val normalizedCorpus = Corpus(citableNodes)

The same pattern creating a text with purely diplomatic readings:

    val citableNodes = tokens.map(_.readWithDiplomatic)
    val diplomaticCorpus = Corpus(citableNodes)

## Filter analyses by alternate readings
You can filter a vector of analyses by the various categories of alternate reading in the HMT model.

To select all the analyses that include *any* category of alternate reading differing from the diplomatic reading:

    val altTokens = tokens.filter(_.hasAlternate)

Select all analyses that include an expanded abbrevation:

    val abbrTokens = tokens.filter(_.isAbbreviation)

Select all analyses that include a scribal correction:

    val corrTokens = tokens.filter(_.hasScribalCorrection)

Select all analyses that include a scribal multiform:

    val multiformTokens = tokens.filter(_.hasMultiform)


## Filter analyses by lexical disambiguation

Use URN twiddling to select all analyses with matching lexical disambiguation.  E.g., select all instances of the personal name Achilles:

    val achilles = Cite2Urn("urn:cite2:hmt:pers:pers1")
    val achillesTokens = tokens.filter(_.lexMatch(achilles))



## Filter analyses by discourse analysis

Use these filters to select tokens by category of discourse:

    val directVoice = tokens.filter(_.isDirectVoice)
    val citations = tokens.filter(_.isCitation)
    val quotedText = tokens.filter(_.isQuotedText)
    val quotedLiteral = tokens.filter(_.isQuotedLiteral)
    val quotedLanguage = tokens.filter(_.isQuotedLanguage)
    val notDirect = tokens.filter(_.notDirectVoice)

## Combinations: create citable nodes for selected analyses

Filters can be chained.  The end of a chain can be combined with a mapping to create a vector of citable text nodes.

Find all analyses of tokens as citations, and create a vector of citable text nodes using expanded (alternate) readings rather than diplomatic view:

    val citations = tokens.filter(_.isCitation)
    val citationPassages = citations.map(_.readWithAlternate)

Since `citationPassages` is just a Vector of citable nodes, you can do normal Scala magic with it, e.g., create a string listing the text of each token on a single line:

    val citedList = citationPassages.map(_.text).mkString("\n")


## Working with ORCA alignments

The heart of the ORCA structure is a pairing of a CTS URN identifying a text passage and a CITE2URN identifying an analytical value.  You can use URN matching on either type of URN.

Find only analyses of passages in *Iliad* 1:

    val bk1analyses = orca ~~ CtsUrn("urn:cts:greekLit:tlg0012.tlg001:1")

Find all passages analyzed by dotted diple marks:

    val dotted = orca ~~ Cite2Urn("urn:cite2:hmt:critsign:dotteddiple")

Since these are filtering operations, they can be chained, too.  `bk1analyses ~~  Cite2Urn("urn:cite2:hmt:critsign:dotteddiple")` would yield only passages in *Iliad* 1 analyzed with a dotted diple.

If you want to chain filters in a single line, you can use syntax like this:


    val iliad1 = CtsUrn("urn:cts:greekLit:tlg0012.tlg001:1")
    val ddiple = Cite2Urn("urn:cite2:hmt:critsign:dotteddiple")

    orca.~~(iliad1).~~(ddiple)


Oder is not significant since each filter creates a subset of the previously defined OrcaCollection.
