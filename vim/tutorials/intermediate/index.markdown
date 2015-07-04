---
layout: post
title: Vim Intermediate Videos
---
### Video Tutorials for the Intermediate Vimmer

In truth, categorizing things into Novice, Intermediate and Advanced is kinda tough... Intermediate is the worst one :) These are just the things that aren't necessarily Novice and aren't necessarily Advanced.

- [The vimrc File and Vim Runtime Directories](#vimrc)
- [Vim Modes Introduction](#modes-intro)
- [Insert Mode](#insert-mode)
- [One Vim... Just One](#onevim)
- [Destruction is Good](#destruction)
- [Using a Vim Macro to Edit Many Files](#manyfilemacro)
- [Vim Macros and Global Commands (one)](#macros-and-global)

* * * * *

<a name="vimrc"/>
The vimrc File and Vim Runtime Directories
==========================================

See it straight from [Vimeo](http://vimeo.com/user1690209) at [The vimrc File and Vim Runtime Directories](http://vimeo.com/7096383).

<object width="720" height="540">
    <param name="allowfullscreen" value="true" />
    <param name="allowscriptaccess" value="always" />
    <param name="movie" value="http://vimeo.com/moogaloop.swf?clip_id=7096383&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=0&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" />
    <embed src="http://vimeo.com/moogaloop.swf?clip_id=7096383&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=0&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" width="720" height="540"></embed>
</object>

What we'll cover
----------------

We're not really covering keystrokes or commands in this one - we're covering concepts behind Vim's configuration and extensibility. This is important stuff to understand in order to truly make Vim your own personal editor. The topics you should read up on are:

-   [:help vimrc](http://vimdoc.sourceforge.net/htmldoc/starting.html#vimrc)
-   [:help 'runtimepath'](http://vimdoc.sourceforge.net/htmldoc/options.html#'runtimepath')
-   [:help \$VIMRUNTIME](http://vimdoc.sourceforge.net/htmldoc/starting.html#$VIMRUNTIME)
-   [:help \$VIM](http://vimdoc.sourceforge.net/htmldoc/starting.html#$VIM)
-   [:help :helptags](http://vimdoc.sourceforge.net/htmldoc/various.html#:helptags)

* * * * *

<a name="modes-intro"/>
Vim Modes Introduction
======================

See it straight from [Vimeo](http://vimeo.com/user1690209) at [Vim Modes Introduction](http://vimeo.com/7129986).

<object width="720" height="540">
    <param name="allowfullscreen" value="true" />
    <param name="allowscriptaccess" value="always" />
    <param name="movie" value="http://vimeo.com/moogaloop.swf?clip_id=7129986&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=0&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" />
    <embed src="http://vimeo.com/moogaloop.swf?clip_id=7129986&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=0&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" width="720" height="540"></embed>
</object>

What we'll cover
----------------

Modes are what set Vim apart from [everything](http://www.gnu.org/software/emacs/) [else](http://www.microsoft.com/resources/documentation/windows/xp/all/proddocs/en-us/win_notepad_whatis_intro.mspx?mfr=true) so it's about damn time we started looking at them. This video merely introduces them and ensures we have the right terminology laid down.

-   [:help vim-modes](http://vimdoc.sourceforge.net/htmldoc/intro.html#vim-modes)
-   [:help Normal-mode](http://vimdoc.sourceforge.net/htmldoc/intro.html#Normal-mode)
-   [:help Visual-mode](http://vimdoc.sourceforge.net/htmldoc/visual.html#Visual-mode)
-   [:help Insert-mode](http://vimdoc.sourceforge.net/htmldoc/insert.html#Insert-mode)
-   [:help Command-line](http://vimdoc.sourceforge.net/htmldoc/cmdline.html#Command-line)
-   [:help Ex-mode](http://vimdoc.sourceforge.net/htmldoc/intro.html#Ex-mode)

* * * * *

<a name="insert-mode"/>
Insert Mode
===========

See it straight from [Vimeo](http://vimeo.com/user1690209) at [Insert Mode](http://vimeo.com/7133419).

<object width="720" height="540">
    <param name="allowfullscreen" value="true" />
    <param name="allowscriptaccess" value="always" />
    <param name="movie" value="http://vimeo.com/moogaloop.swf?clip_id=7133419&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=0&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" />
    <embed src="http://vimeo.com/moogaloop.swf?clip_id=7133419&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=0&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" width="720" height="540"></embed>
</object>

What we'll cover
----------------

Insert Mode is one of those often-used modes. You use it all the time to input text - althought one should probably be trying to figure out how to use Insert Mode as little as possible since entering text is inefficient. Insert mode provides us with a few tricks to help us insert more efficiently and we'll be covering a few of them now. As always it's simply not possible to cover all the possibilities, so you're more than encouraged to read through the documentation.

-   [:help insert.txt](http://vimdoc.sourceforge.net/htmldoc/insert.html#insert.txt)
-   [:help usr\_24.txt](http://vimdoc.sourceforge.net/htmldoc/usr_24.html)
-   [:help 'textwidth'](http://vimdoc.sourceforge.net/htmldoc/options.html#'textwidth')
-   [:help i\_CTRL-T](http://vimdoc.sourceforge.net/htmldoc/insert.html#i_CTRL-T)
-   [:help i\_CTRL-D](http://vimdoc.sourceforge.net/htmldoc/insert.html#i_CTRL-D)
-   [:help i\_CTRL-W](http://vimdoc.sourceforge.net/htmldoc/insert.html#i_CTRL-W)
-   [:help i\_CTRL-V](http://vimdoc.sourceforge.net/htmldoc/insert.html#i_CTRL-V)
-   [:help ins-special-special](http://vimdoc.sourceforge.net/htmldoc/insert.html#ins-special-special)
-   [:help ins-completion](http://vimdoc.sourceforge.net/htmldoc/insert.html#ins-completion)
-   [:help 'complete'](http://vimdoc.sourceforge.net/htmldoc/options.html#'complete')
-   [:help i\_CTRL-N](http://vimdoc.sourceforge.net/htmldoc/insert.html#i_CTRL-N)
-   [:help i\_CTRL-P](http://vimdoc.sourceforge.net/htmldoc/insert.html#i_CTRL-P)

* * * * *

<a name="onevim"/>
One Vim... Just One
===================

See it straight from [Vimeo](http://vimeo.com/user1690209) at [One Vim... Just One](http://vimeo.com/4446112).

<object width="720" height="540">
    <param name="allowfullscreen" value="true" />
    <param name="allowscriptaccess" value="always" />
    <param name="movie" value="http://vimeo.com/moogaloop.swf?clip_id=4446112&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=0&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" />
    <embed src="http://vimeo.com/moogaloop.swf?clip_id=4446112&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=0&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" width="720" height="540"></embed>
</object>

What we'll cover
----------------

This one's about running one GUI Vim session and commanding it from an external command shell using the command "`gvim --remote-silent`" from that external command shell.

-   [:help --remote-silent](http://vimdoc.sourceforge.net/htmldoc/remote.html#--remote-silent)

* * * * *

<a name="destruction"/>
Destruction is Good
===================

See it straight from [Vimeo](http://vimeo.com/user1690209) at [Destruction is Good](http://vimeo.com/6110008).

<object width="720" height="540">
    <param name="allowfullscreen" value="true" />
    <param name="allowscriptaccess" value="always" />
    <param name="movie" value="http://vimeo.com/moogaloop.swf?clip_id=6110008&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=0&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" />
    <embed src="http://vimeo.com/moogaloop.swf?clip_id=6110008&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=0&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" width="720" height="540"></embed>
</object>

What we'll cover
----------------

In this video we're going to look at an editing technique that involves getting to where you want to be by destroying contents, not by creating them. The idea here is that we want to "copy" a few lines from a file and "paste" them somewhere else so we can work with them. It turns out that doing such a thing is usually very annoying and not very productive, so instead of doing that we destroy the contents of the file that we don't want and keep the rest.

-   [:help :vglobal](http://vimdoc.sourceforge.net/htmldoc/repeat.html#:vglobal) shows us how to perform an operation on all lines that *don't match the given pattern*. In this case we delete lines that don't match what we're looking for.
-   [:help :substitute](http://vimdoc.sourceforge.net/htmldoc/change.html#:substitute) shows us how to substitute one piece of text for another. In this case we've given it a range of '%' that is synonymous with a range of "1,\$" ([:help :%](http://vimdoc.sourceforge.net/htmldoc/cmdline.html#:%)).
-   [:help regexp](http://vimdoc.sourceforge.net/htmldoc/pattern.html#regexp) is a reference to regular expressions. These are very complex things but considering that I used them, I figured I'd point you at them. If you aren't familiar with regular expressions, stay tuned... we'll be covering them eventually.

* * * * *

<a name="manyfilemacro"/>
Using a Vim Macro to Edit Many Files
====================================

See it straight from [Vimeo](http://vimeo.com/user1690209) at [Using a Vim Macro to Edit Many Files](http://vimeo.com/4456458).

<object width="720" height="540">
    <param name="allowfullscreen" value="true" />
    <param name="allowscriptaccess" value="always" />
    <param name="movie" value="http://vimeo.com/moogaloop.swf?clip_id=4456458&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=0&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" />
    <embed src="http://vimeo.com/moogaloop.swf?clip_id=4456458&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=0&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" width="720" height="540"></embed>
</object>

What we'll cover
----------------

In this video we show how to use a cleverly crafted Vim macro to do a pretty cool edit to some C++ source code across a lot of different header files. The general trick is to avoid "position oriented" kind of things, such as "move down to the next line 'cuz that's where the function is" because in the next file, the function may be two lines down.

-   [:help q](http://vimdoc.sourceforge.net/htmldoc/repeat.html#q) is how we record a macro. It's used for both starting and stopping the recording.
-   [:help yank](http://vimdoc.sourceforge.net/htmldoc/change.html#yank) is somewhat synonymous with (crappy) Notepad's `File->Copy` "feature".
-   [:help %](http://vimdoc.sourceforge.net/htmldoc/motion.html#%) is the operator we use to move the cursor to the brace that is the partner of the one that the cursor is currently on.
-   [:help /](http://vimdoc.sourceforge.net/htmldoc/pattern.html#/) is what let's us search for things in a file.
-   [:help O](http://vimdoc.sourceforge.net/htmldoc/insert.html#O) opens a line above the cursor.
-   [:help p](http://vimdoc.sourceforge.net/htmldoc/change.html#p) let's us put (paste) the text that we previously yanked.
-   [:help :wnext](http://vimdoc.sourceforge.net/htmldoc/editing.html#:wnext) is the command we use to both write the current file and move on to the next one in the [:args](http://vimdoc.sourceforge.net/htmldoc/editing.html#:args) list.
-   [:help @](http://vimdoc.sourceforge.net/htmldoc/repeat.html#@) lets us run the macro that we recorded.

* * * * *

<a name="macros-and-global"/>
Vim Macros and Global Commands (one)
====================================

See it straight from [Vimeo](http://vimeo.com/user1690209) at [Vim Macros and Global Commands (one)](http://vimeo.com/4448635).

<object width="720" height="540">
    <param name="allowfullscreen" value="true" />
    <param name="allowscriptaccess" value="always" />
    <param name="movie" value="http://vimeo.com/moogaloop.swf?clip_id=4448635&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=0&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" />
    <embed src="http://vimeo.com/moogaloop.swf?clip_id=4448635&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=0&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" width="720" height="540"></embed>
</object>

What we'll cover
----------------

Here we edit a file using two different methods - a recorded macro and a global command.

-   [:help CTRL-A](http://vimdoc.sourceforge.net/htmldoc/change.html#CTRL-A) increments the first number it can find on the line (by one or an optional argument).
-   [:help q](http://vimdoc.sourceforge.net/htmldoc/repeat.html#q) is how we record a macro. It's used for both starting and stopping the recording.
-   [:help @](http://vimdoc.sourceforge.net/htmldoc/repeat.html#@) lets us run the macro that we recorded.
-   [:help :global](http://vimdoc.sourceforge.net/htmldoc/repeat.html#:global) is how we can run a given command (in this case a [:normal](http://vimdoc.sourceforge.net/htmldoc/various.html#:normal) command) for each line that matches a pattern.

