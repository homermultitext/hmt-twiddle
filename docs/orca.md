---
layout: page
title: "Working with ORCA alignments"
---



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


Order is not significant since each filter creates a subset of the previously defined OrcaCollection.
