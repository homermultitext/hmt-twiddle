---
title:  Conceptual overview
layout: page
---

## Modelling citable texts

HMT project editions follow the generic OHCO2 model of *citable text content*.  `hmt-twiddle` uses the `ohco2` and `cite` libraries for implementions of two critical ideas of this generic model:

1.  a `Corpus` of texts, made from a Vector of `CitableNodes`.
2.  the `CitableNode` object, a single citable passage of text with URN and text content



## Modelling the contents of citable texts

The HMT project also specifies a model for the *contents of citable passages*.  The HMT project's editorial guide explains how editors express this model in TEI-compliant XML markup.
`hmt-twiddle`  uses the `hmt-textmodel` library to implement this specific model of the contents of citable text passages.

The top-level concept of the `hmt-textmodel` is the `TokenAnalysis`. A `TokenAnalysis` has two members: a CTS URN identifying the text, and a complex `HmtToken` with analytical data. The `HmtToken` captures everything expressed about a token by HMT project XML editions:  for detailed user documentation, see <https://homermultitext.github.io/hmt-textmodel/>.   The `TokenAnalysis` has high-level functions (illustrated in this guide) for working with its contents. These functions either:

-   filter a Vector of analyses to create a new Vector of analyses, or
-  transform a Vector of analyses into a Vector of citable text nodes
