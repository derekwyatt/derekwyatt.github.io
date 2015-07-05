---
layout: post
title: Going to the End of a Search Highlight
header-img: img/amsterdam-main-train-station.png
abstract: How to get to the end of the search highlight in Vim.
tags:
- vim
---
I saw this on Twitter:

> From: @jyurek Sent: 27 Aug 2009 10:35
>
> Is there a vim command to go to the end of the current search highlight? Like e for words, but for search matches. \#vim \#lazytweet

I found it quite interesting and didn't have an "out of the box" Vim answer to this. So I crafted the following answer:

    :nmap <silent> <c-e> /<c-r>//e<cr>:let @/='<c-r>/'<cr>

And that seems to work. Essentially you hit CTRL-e and that starts another search exactly like your previous search but puts the cursor at the end of it, then puts the original search back in the search register so that 'n' and 'N' work as expected.
