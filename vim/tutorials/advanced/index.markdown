---
layout: post
title: Vim Advanced Videos
---
### Video Tutorials for the Advanced Vimmer

I don't claim to be the most advanced Vimmer on the planet, but I think I know a few advanced techniques. This section is designed to help the intermediate move into the more advanced stage.

- [The Vim Expression Register](#expression-register)
- [Vim Autocommands](#autocommands)
- [Find Command and the Path](#find-path)
- [Globals, Commands and Functions](#globals-commands-functions)

* * * * *

<a name="expression-register"/>
The Vim Expression Register
===========================

See it straight from [Vimeo](http://vimeo.com/user1690209) at [The Vim Expression Register](http://vimeo.com/4446843).

<object width="720" height="540">
    <param name="allowfullscreen" value="true" />
    <param name="allowscriptaccess" value="always" />
    <param name="movie" value="http://vimeo.com/moogaloop.swf?clip_id=4446843&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=0&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" />
    <embed src="http://vimeo.com/moogaloop.swf?clip_id=4446843&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=0&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" width="720" height="540"></embed>
</object>

What we'll cover
----------------

The Vim expression register is a *really* nifty little aspect of Vim that allows for very easy integration of external commands (or even internally defined functions) into the standard text editing process. In this video we take a very simple look at how it can be used to automatically generate and insert a [Universally Unique Identifier](http://en.wikipedia.org/wiki/Uuid) into your buffer.

-   [:help i\_CTRL-R\_=](http://vimdoc.sourceforge.net/htmldoc/insert.html#i_CTRL-R_=) is the documentation on how to use the Vim expression register.

* * * * *

<a name="autocommands"/>
Vim Autocommands
================

See it straight from [Vimeo](http://vimeo.com/user1690209) at [Vim Autocommands](http://vimeo.com/4454614).

<object width="720" height="540">
    <param name="allowfullscreen" value="true" />
    <param name="allowscriptaccess" value="always" />
    <param name="movie" value="http://vimeo.com/moogaloop.swf?clip_id=4454614&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=0&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" />
    <embed src="http://vimeo.com/moogaloop.swf?clip_id=4454614&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=0&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" width="720" height="540"></embed>
</object>

What we'll cover
----------------

Vim autocommands are one of the stronger aspects of Vim's extensibility. Vim allows you to hook in to events that occur in the system, such as [just before a buffer is written (:help BufWritePre)](http://vimdoc.sourceforge.net/htmldoc/autocmd.html#BufWritePre) or [when the cursor is moved (:help CursorMoved)](http://vimdoc.sourceforge.net/htmldoc/autocmd.html#CursorMoved). You can hook in to these events for particular file types or file extensions and have code run that does whatever sexy bit of work you need done.

-   [:help :autocmd](http://vimdoc.sourceforge.net/htmldoc/autocmd.html#:autocmd) is the documentation for the autocommand feature of Vim.
-   [:help :augroup](http://vimdoc.sourceforge.net/htmldoc/autocmd.html#:augroup) details the structure you can use to group autocommands together. I generally recommend that you always do this as deleting autocommands isn't necessarily possible but is very easy using groups.
-   [:help :function](http://vimdoc.sourceforge.net/htmldoc/eval.html#:function) teaches the important bits you need to know to start writing a function.

* * * * *

<a name="find-path"/>
Find Command and the Path
=========================

See it straight from [Vimeo](http://vimeo.com/user1690209) at [Find Command and the Path](http://vimeo.com/6154082).

<object width="720" height="540">
    <param name="allowfullscreen" value="true" />
    <param name="allowscriptaccess" value="always" />
    <param name="movie" value="http://vimeo.com/moogaloop.swf?clip_id=6154082&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=0&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" />
    <embed src="http://vimeo.com/moogaloop.swf?clip_id=6154082&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=0&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" width="720" height="540"></embed>
</object>

What we'll cover
----------------

Vim gives the ability to load up a file from locations that you specify in a search path. Much like the `PATH` variable in Windows or the Unix command shell, Vim has a path system that can do the same sort of thing, but for files instead of exectuables.

-   [:help :find](http://vimdoc.sourceforge.net/htmldoc/editing.html#:find) discusses the find command and what it does.
-   [:help 'path'](http://vimdoc.sourceforge.net/htmldoc/options.html#'path') is the option that allows you to specify where Vim should look for your files.
-   [:help :autocmd](http://vimdoc.sourceforge.net/htmldoc/autocmd.html#:autocmd) is the documentation for the autocommand feature of Vim that we use to run a function that sets the 'path' for us.
-   [:help :augroup](http://vimdoc.sourceforge.net/htmldoc/autocmd.html#:augroup) holds our autocommands.
-   [:help expand()](http://vimdoc.sourceforge.net/htmldoc/eval.html#expand()) is extremely useful for expanding special filenames and manipulating path strings.

* * * * *

<a name="globals-commands-functions"/>
Globals, Commands and Functions
===============================

See it straight from [Vimeo](http://vimeo.com/user1690209) at [Globals, Commands and Functions](http://vimeo.com/15443936).

<iframe src="http://player.vimeo.com/video/15443936?byline=0&amp;portrait=0" width="720" height="540" frameborder="0"></iframe>

What we'll cover
----------------

This is all command-line mode stuff; even the normal mode commands are going to be issued via the command-line. What we're going to do is take an XML document and strip away the XMLiness of it and make it a standard text file that we can deal with. And we're going to do it without using a single insert-mode command. Rock 'n Roll!

-   [:help :g](http://vimdoc.sourceforge.net/htmldoc/repeat.html#:g) tells you all about the :global command, which is an extremely powerful command-line tool.
-   [:help :v](http://vimdoc.sourceforge.net/htmldoc/repeat.html#:v) tells you that it's the inverse of the :g command.
-   [:help :s](http://vimdoc.sourceforge.net/htmldoc/change.html#:s) talks about the substitute command... oh so powerful.
-   [:help :/\\(](http://vimdoc.sourceforge.net/htmldoc/pattern.html#/\%28) is the aspect of regular expressions we use to create subgroups that are remembered.
-   [:help /\^](http://vimdoc.sourceforge.net/htmldoc/pattern.html#/^) is the way we anchor a regular expression to the beginning of a line.
-   [:help /\\{](http://vimdoc.sourceforge.net/htmldoc/pattern.html#/\{) describes how to put "counts" into regular expressions so we don't have to repeatedly enter characters; we just say how many times they repeat.
-   [:help /\[\]](http://vimdoc.sourceforge.net/htmldoc/pattern.html#/[]) lets us specify a collection of characters. Sometimes that collection is merely a collection of things that are "not" something else, which is denoted by `[^`.
-   [:help /\\zs](http://vimdoc.sourceforge.net/htmldoc/pattern.html#/\zs) lets us make a more specific "start" to a regular expression with respect to its substitution - very cool.
-   [:help :normal](http://vimdoc.sourceforge.net/htmldoc/various.html#:normal) lets you execute normal-mode commands from the command-line.
-   [:help :t](http://vimdoc.sourceforge.net/htmldoc/change.html#:t) tells you about the synonym for the "copy" command.
-   [:help search-pattern](http://vimdoc.sourceforge.net/htmldoc/pattern.html#search-pattern) is a very long and very important section on how to use regular expressions in Vim. Study.

