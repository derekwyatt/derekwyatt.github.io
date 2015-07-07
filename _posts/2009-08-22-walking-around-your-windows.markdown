---
layout: post
title: Walking Around Your Windows
header-img: img/orion-nebula-space-galaxy.jpg
abstract: Some mappings in Vim that help you wander around your windows.
tags:
- vim
---
Vim can do an [insane number of things with windows in Vim](http://vimdoc.sourceforge.net/htmldoc/windows.html). I take a minimalist approach to windows in Vim and try to make it as simple for myself as possible. There are a number of factors that drive my configuration:

- I always use my left hand to hit the CTRL key. The main key that is used for windowing is `CTRL-W`.
- I prefer to have multiple key sequences to occur on opposing hands when possible. `CTRL-W` is two simultaneous keys on the same hand.
- It's all about reusing existing habits when I can. So `h`, `j`, `k` and `l` are important to reuse. The existing Vim solution actually already does this.

Those points lead me to have the following mappings for window management:

``` vim
" Move the cursor to the window left of the current one
noremap <silent> ,h :wincmd h<cr>

" Move the cursor to the window below the current one
noremap <silent> ,j :wincmd j<cr>

" Move the cursor to the window above the current one
noremap <silent> ,k :wincmd k<cr>

" Move the cursor to the window right of the current one
noremap <silent> ,l :wincmd l<cr>

" Close the window below this one
noremap <silent> ,cj :wincmd j<cr>:close<cr>

" Close the window above this one
noremap <silent> ,ck :wincmd k<cr>:close<cr>

" Close the window to the left of this one
noremap <silent> ,ch :wincmd h<cr>:close<cr>

" Close the window to the right of this one
noremap <silent> ,cl :wincmd l<cr>:close<cr>

" Close the current window
noremap <silent> ,cc :close<cr>

" Move the current window to the right of the main Vim window
noremap <silent> ,ml <C-W>L

" Move the current window to the top of the main Vim window
noremap <silent> ,mk <C-W>K

" Move the current window to the left of the main Vim window
noremap <silent> ,mh <C-W>H

" Move the current window to the bottom of the main Vim window
noremap <silent> ,mj <C-W>J
```
