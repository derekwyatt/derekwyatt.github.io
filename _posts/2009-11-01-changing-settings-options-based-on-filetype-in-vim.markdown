---
layout: post
title: Changing settings / options based on filetype in Vim
---
If you want to change settings / options based on the [filetype](http://vimdoc.sourceforge.net/htmldoc/filetype.html#filetype.txt) there are a couple of ways you can do it.

Using an Autocommand
--------------------

You can do this with an [autocmd](http://vimdoc.sourceforge.net/htmldoc/autocmd.html#autocmd.txt) in your [vimrc](http://vimdoc.sourceforge.net/htmldoc/starting.html#vimrc). Let's assume you want to change some of the [indenting](http://vimdoc.sourceforge.net/htmldoc/indent.html#indent.txt) rules for [perl](http://www.perl.org/) and [html](http://en.wikipedia.org/wiki/HTML).

``` vim
augroup indent_settings
    au!
    au BufEnter *.pl setl autoindent smartindent
    au BufEnter *.html setl noautoindent nosmartindent
augroup END
```

The above will do a [setlocal](http://vimdoc.sourceforge.net/htmldoc/options.html#:setlocal) when entering a perl or html file. It will turn [autoindent](http://vimdoc.sourceforge.net/htmldoc/options.html#'autoindent') to 'on' and [smartindent](http://vimdoc.sourceforge.net/htmldoc/options.html#'smartindent') to 'on' when you enter a [buffer](http://vimdoc.sourceforge.net/htmldoc/windows.html#buffers) containing a perl file, and will turn them off when entering a buffer containing an html file.

Dropping the commands in a filetype file
----------------------------------------

You can also choose to organize things into separate [ftplugin](http://vimdoc.sourceforge.net/htmldoc/usr_41.html#ftplugin) files in your [runtime directory](http://vimdoc.sourceforge.net/htmldoc/options.html#'runtimepath'). If we want to continue with the perl and html examples above, you would do the following: In `ftplugin/perl.vim`:

``` vim
setl autoindent
setl smartindent
```

In `ftplugin/html.vim`:

``` vim
setl noautoindent
setl nosmartindent
```
