---
layout: post
title: Silently Redirecting to the Vim Clipboard
tags:
- vim
---
In an upcoming video tutorial, I'll be doing a bit of work with the [:redir](http://vimdoc.sourceforge.net/htmldoc/various.html#:redir) command. This is a great Vim facility but it can be helped with a function wrapper. I'm including that wrapper here:

``` vim
function! RedirToClipboardFunction(cmd, ...)
    let cmd = a:cmd . " " . join(a:000, " ")
    redir @*>
    exe cmd
    redir END
endfunction

command! -complete=command -nargs=+ RedirToClipboard
    \ silent! call RedirToClipbaordFunction(<f -args>)
```

This function allows you to run a command (such as [:history](http://vimdoc.sourceforge.net/htmldoc/cmdline.html#:history), hint hint) and have the contents placed to the clipboard that you can then paste somewhere else. The command that is created just makes it easier to use. For example:

``` vim
:RedirToClipboard history : -50,
```
