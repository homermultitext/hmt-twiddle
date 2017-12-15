---
layout: page
title: "How to filter analyses by alternate readings"
---





You can filter a vector of analyses by the various categories of alternate reading represented in the HMT model.

To select all the analyses that include *any* category of alternate reading differing from the diplomatic reading:

    val altTokens = tokens.filter(_.hasAlternate)

Select all analyses that include an expanded abbrevation:

    val abbrTokens = tokens.filter(_.isAbbreviation)

Select all analyses that include a scribal correction:

    val corrTokens = tokens.filter(_.hasScribalCorrection)

Select all analyses that include a scribal multiform:

    val multiformTokens = tokens.filter(_.hasMultiform)
