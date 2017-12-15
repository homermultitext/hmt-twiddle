---
layout: page
title: "Filter analyses by lexical disambiguation"
---

Use URN twiddling to select all analyses with matching lexical disambiguation.  E.g., select all instances of the personal name Achilles:

    val achilles = Cite2Urn("urn:cite2:hmt:pers:pers1")
    val achillesTokens = tokens.filter(_.lexMatch(achilles))
