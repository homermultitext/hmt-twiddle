---
layout: page
title: "How to filter analyses by discourse analysis"
---



Use one of these filters to select tokens by category of discourse:

    val directVoice = tokens.filter(_.isDirectVoice)
    val citations = tokens.filter(_.isCitation)
    val quotedText = tokens.filter(_.isQuotedText)
    val quotedLiteral = tokens.filter(_.isQuotedLiteral)
    val quotedLanguage = tokens.filter(_.isQuotedLanguage)
    val notDirect = tokens.filter(_.notDirectVoice)
