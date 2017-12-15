---
layout: page
title: "HMT libraries"
---

`hmt-twiddle` loads the `hmt-textmodel` library for working with HMT text contents.  It in turn relies on the generic `ohco2` library for two important  constructs:

1. a `Corpus` of texts, made from a Vector of `CitableNode`s.
2. the `CitableNode` object, a single citable passage of text with URN and text content


The top-level concept of the `hmt-textmodel` is the `TokenAnalysis`.  A `TokenAnalysis` has two members: a CTS URN identifying the text, and a complex `HmtToken` with analytical data. The `HmtToken` captures everything expressed about a token by HMT project XML editions, but the `TokenAnalysis`  has high-level functions (illustrated here) for working with its contents.  These functions either:

- *filter* a Vector of analyses to create a new Vector of analyses, or
- *transform* a Vector of analyses to a Vector of citable text nodes
