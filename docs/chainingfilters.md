---
layout: page
title: "Combined analyses"
---

## Chaining filters

Filters can be chained.  The end of a chain can be combined with a mapping to create a vector of citable text nodes.


## Example: create citable nodes for selected analyses

This example finds all tokens analysed as citations, and creates a vector of citable text nodes, using the expanded (alternate) readings, rather than a diplomatic view, for the text contents of the node:

    val citations = tokens.filter(_.isCitation)
    val citationPassages = citations.map(_.readWithAlternate)

Since `citationPassages` is just a Vector of citable nodes, you can do normal Scala magic with it, e.g., create a string listing the text of each token on a single line:

    val citedList = citationPassages.map(_.text).mkString("\n")
