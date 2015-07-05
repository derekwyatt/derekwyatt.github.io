---
layout: post
title: No <code>namespace</code> indent for C++ with Vim
header-img: img/old-bridge-with-green-field-in-front-of-it.png
abstract: The stock C++ indent will indent namespaces, which I find annoying, so this will avoid that indent.
tags:
- c++
- vim
---
(**NOTE:** There's a more up to date version of this in the [General C++ Settings](/vim/working-with-vim-and-cpp/general-cpp-settings/) section.)

Finally! Vim's decision to add a '[shiftwidth](http://vimdoc.sourceforge.net/htmldoc/options.html#'shiftwidth')' to everything I type when I'm inside a `namespace` is thoroughly annoying and there appears to be no "standard" way to fix this in Vim aside from writing your own function for use in the '[indentexpr](http://vimdoc.sourceforge.net/htmldoc/options.html#'indentexpr')' option.

Well I finally got around to writing this up and, while extremely crude, it appears to work alright. You'll find the function definition below as well as in the [General C++ Settings](/vim/working-with-vim-and-cpp/general-cpp-settings/) section.

``` vim
" Fix up indent issues - I can't stand wasting an indent because
" I'm in a namespace.  If you don't like this then just comment
" this line out.
setlocal indentexpr=GetCppIndentNoNamespace(v:lnum)

"
" GetCppIndentNoNamespace()
"
" This little function calculates the indent level for C++ and
" treats the namespace differently than usual - we ignore it.  The
" indent level is the for a given line is the same as it would
" be were the namespace not event there.
"
" This function is rather crude but it works.
"
function! GetCppIndentNoNamespace(lnum)
  let nsLineNum = search('^\s*\\s\+\S\+', 'bnW')
  if nsLineNum == 0
    return cindent(a:lnum)
  else
    let incomment = 0
    for n in range(nsLineNum + 1, a:lnum - 1)
      let cline = getline(n)
      if cline =~ '^\s*/\*'
        let incomment = 1
      elseif cline =~ '^.*\*/'
        let incomment = 0
      elseif incomment == 0
        if cline =~ '^\s*\S\+'
          return cindent(a:lnum)
        endif
      endif
    endfor
    return cindent(nsLineNum)
  endif
endfunction
```
