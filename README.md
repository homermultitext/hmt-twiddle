# hmt-twiddle

Analyzing the HMT archive using [utwiddle](https://github.com/neelsmith/utwiddle).

## Data sets

- `data/hmt_2cols.tsv` : all HMT texts in two-column format.  Automatically generated from HMT archive on Feb. 18, 2017
- `data/critsignorca.tsv` : occurrences of critical signs. Generated from HMT archive on Feb. 18, 2017


## Quick start

    sbt console

From with the sbt console,

    > :load loadhmt.sc


This creates two objects:

1. `corpus` : the complete HMT Greek text corpus.
2. `orca` : an alignment of all critical signs with Iliadic lines
