---
layout: post
title: The Absolute Bare Minimum
header-img: img/compact-discs.png
abstract: If you want to get started with Vim, you should start with the bare minimum configuration that you need.  This is it.
tags:
- vim
---
There is a bare minimum that you should have in your `vimrc` file. And let's face it, we're not interested being compatible with [Vi](http://en.wikipedia.org/wiki/Vi) here. We want the full power of [Vim](http://www.vim.org/) at our fingertips here, so let's go that way, shall we?

``` vim
" Forget being compatible with good ol' vi
set nocompatible

" Get that filetype stuff happening
filetype on
filetype plugin on
filetype indent on

" Turn on that syntax highlighting
syntax on

" Why is this not a default
set hidden

" Don't update the display while executing macros
set lazyredraw

" At least let yourself know what mode you're in
set showmode

" Enable enhanced command-line completion. Presumes you have compiled
" with +wildmenu.  See :help 'wildmenu'
set wildmenu

" Let's make it easy to edit this file (mnemonic for the key sequence is
" 'e'dit 'v'imrc)
nmap <silent> ,ev :e $MYVIMRC<cr>

" And to source this file as well (mnemonic for the key sequence is
" 's'ource 'v'imrc)
nmap <silent> ,sv :so $MYVIMRC<cr>
```

We'll leave it there for now... that should do for the absolute bare minimum, I would think. Opinions will vary, for sure :)

*A note on "hidden"* [Ben Fritz](http://vim.wikia.com/wiki/User:Fritzophrenic) (a *real* Vim guru) suggested that I explain the ['hidden'](http://vimdoc.sourceforge.net/htmldoc/options.html#'hidden') option and why I think it's such a good thing. He rightly points out, of course, that it's not the only way to do things (as can be said for nearly every feature of Vim :D), but it's definitely something I consider crucial. Hidden buffers are described pretty well right at the [buffer-hidden](http://vimdoc.sourceforge.net/htmldoc/windows.html#buffer-hidden) part of the Vim help but in short, by setting 'hidden' you're telling Vim that it's OK to have an unwritten buffer that's no longer visible. If you've got 12 buffers and 2 windows then there are 10 buffers that aren't visible and if you set 'nohidden' then that means that those 10 buffers must be written to disk. If they aren't written to disk then they would have to be visible. I prefer to have buffers that aren't visible and need not be written first. Vim keeps me safe from quitting altogether while having unwritten buffers so there's no chance of accidentally losing data. The reason I think it's such a great feature is for a few reasons:

- I don't use [tabpages](http://vimdoc.sourceforge.net/htmldoc/tabpage.html#tabpage).
- I load a ***lot*** of files and they can't all have their own [windows](http://vimdoc.sourceforge.net/htmldoc/windows.html#windows).
- I believe in having only [one Vim](http://vimeo.com/4446112) session.
- I modify a lot of buffers with single commands using [:bufdo](http://vimdoc.sourceforge.net/htmldoc/windows.html#:bufdo).
- Typing [:wa](http://vimdoc.sourceforge.net/htmldoc/editing.html#:wall) once in a while instead of [:w](http://vimdoc.sourceforge.net/htmldoc/editing.html#:w) once in a while isn't such a big deal.

Using `set hidden` allows me the kind of workflow I like, which is one that is highly flexible for the things I do - lots of modifications on a global scale or even on a sub-global scale. I've forgotten just how convenient it is. What I do notice is that when I use another person's Vim that doesn't have it set, I hit the 'nohidden' wall after about half a dozen keystrokes and say "Argh!" :)
