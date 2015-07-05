---
layout: post
title: Toggling and Shortening
header-img: img/female-sitting-in-library.png
abstract: An example of some mappings that I've got for toggling some settings in Vim
tags:
- vim
---
There are a number of [options / settings](http://vimdoc.sourceforge.net/htmldoc/options.html) in Vim, of course, and sometimes they're not so easy to set. There are also a lot of things we tend to do over and over that take more than a couple of characters to write and you want to do them faster. This is where mappings come in:

``` vim
" Toggle paste mode
nmap  ,p :set invpaste:set paste?

" Turn off that stupid highlight search
nmap  ,n :set invhls:set hls?

" Set text wrapping toggles
nmap  ,w :set invwrap:set wrap?

" Set up retabbing on a source file
nmap  ,rr :1,$retab

" cd to the directory containing the file in the buffer
nmap  ,cd :lcd %:h

" Make the directory that contains the file in the current buffer.
" This is useful when you edit a file in a directory that doesn't
" (yet) exist
nmap  ,md :!mkdir -p %:p:h
```
