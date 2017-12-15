---
layout: page
title: "Using hmt-twiddle"
---


## Prerequisites

The `hmt-twiddle` repository sets up a Scala build environment for interactive work with HMT data sets in an sbt console.  You need both [Scala](https://www.scala-lang.org/) and the interactive build tool, [sbt](https://www.scala-sbt.org/), to use it.


## Starting `hmt-twiddle`

You can load the HMT project code libraries and the data sets including in this repository by:

1.  starting an sbt console (run `sbt console` from a terminal)
2.  at the console prompt, entering `loadhmt.sc`


This loads all the code libraries you need, and creates two data objects:

1. `corpus` : the complete HMT Greek text corpus.
2. `orca` : an alignment of all critical signs with Iliadic lines

This user guide illustrates a few ways you can work with these objects.
