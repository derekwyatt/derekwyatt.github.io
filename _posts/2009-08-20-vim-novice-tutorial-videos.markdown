---
layout: post
title: Vim Novice Tutorial Videos
---
In these videos we cover the basics. If you're just getting started or think you may be missing some of the basic ideas behind Vim, these videos are for you. They're hosted at [Vimeo](http://vimeo.com/user1690209) but you can also watch them here if you wish. The quality may be a bit better over at [Vimeo](http://vimeo.com/user1690209), though.

On this page, you should find that there is some better organization and a decent set of notes you can pick up afterwards.

-   [Welcome to Vim](#Welcome)
-   [Basic Movement (Screencast 1)](#Basic_Movement_1)
-   [Basic Movement (Screencast 2)](#Basic_Movement_2)
-   [Basic Movement (Screencast 3)](#Basic_Movement_3)
-   [Basic Editing (Screencast 1)](#Basic_Editing_1)
-   [Basic Editing (Screencast 2)](#Basic_Editing_2)
-   [Working with Many Files (Screencast 1)](#Many_Files_1)
-   [Working with Many Files (Screencast 2)](#Many_Files_2)
-   [Working with Many Files (Screencast 3)](#Many_Files_3)
-   [How to use the Help System](#Help)

* * * * *

<a name="Welcome"/>
Welcome to Vim
==============

**NOTE:** You should really see it straight from [Vimeo](http://vimeo.com/user1690209) at [Welcome to Vim](http://vimeo.com/6999927) as the embedded video doesn't look all that great and at Vimeo there is a decent set of hyperlinks that let you index the video and fast-forward to the major topics.

<object width="480" height="360">
    <param name="allowfullscreen" value="true" />
    <param name="allowscriptaccess" value="always" />
    <param name="movie" value="http://vimeo.com/moogaloop.swf?clip_id=6999927&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=0&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" />
    <embed src="http://vimeo.com/moogaloop.swf?clip_id=6999927&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=0&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" width="480" height="360"></embed>
</object>

What we'll cover
----------------

Not much :) This is a Vim showcase to run through some of the most very basic, course, obvious Vim stuff just to show it off and let complete newbies see what's what.

* * * * *

<a name="Basic_Movement_1"/>
Basic Movement (Screencast 1)
=============================

See it straight from [Vimeo](http://vimeo.com/user1690209) at [Basic Movement (Screencast 1)](http://vimeo.com/6170479).

<object width="400" height="300">
    <param name="allowfullscreen" value="true" />
    <param name="allowscriptaccess" value="always" />
    <param name="movie" value="http://vimeo.com/moogaloop.swf?clip_id=6170479&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=1&amp;show_portrait=0&amp;color=&amp;fullscreen=1" />
    <embed src="http://vimeo.com/moogaloop.swf?clip_id=6170479&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=1&amp;show_portrait=0&amp;color=&amp;fullscreen=1" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" width="400" height="300"></embed>
</object>

What we'll cover
----------------

**Slow movement** - Character-wise movements with the home keys: `h`, `j`, `k` and `l`. The lesson here: ***DON'T use the arrow keys***.

**Line terminus** - Beginning of line and end of line movements: `0` and `$`.

**The different types of "words"**

-   `word`s - represent a sequence of characters in the `'iskeyword'` class.
-   `WORD`s - represent a sequence of characters separated by whitespace.
-   Run `:help word` and `:help WORD`

**Forward word movement** - We learn to move foward to the next `WORD` and `word` both to the beginning of words and the end of words. Commands are `w`, `W`, `e` and `E`.

**Backward word movement** - And we learn to move backward to the previous `WORD` and `word` both to the beginning of words and the end of words. Commands are `b`, `B`, `ge` and `gE`.

**"To the Character" movement** - The great, super great commands `f`, `F`, `t`, `T` and `;` that let you move to specific characters *within a line*.

* * * * *

<a name="Basic_Movement_2"/>
Basic Movement (Screencast 2)
=============================

See it straight from [Vimeo](http://vimeo.com/user1690209) at [Basic Movement (Screencast 2)](http://vimeo.com/6185584).

<object width="400" height="320">
    <param name="allowfullscreen" value="true" />
    <param name="allowscriptaccess" value="always" />
    <param name="movie" value="http://vimeo.com/moogaloop.swf?clip_id=6185584&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=1&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" />
    <embed src="http://vimeo.com/moogaloop.swf?clip_id=6185584&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=1&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" width="400" height="320"></embed>
</object>

What we'll cover
----------------

**Paging** - Moving the page up and down by *full pages* with `CTRL-f` and `CTRL-b` and by *half pages* with `CTRL-u` and `CTRL-d`.

**Cursor jumping to screen parts** - Moving to the head, middle and last line of a screen with `H`, `M` and `L` respectively.

**Top and Bottom of the buffer** - Jumping to the top line of the entire buffer with `gg` and the bottom of the entire buffer with `G`.

**Jumping to a particular line** - Get to a specific line number with `<number>G`.

**Easy regular expression searching** - The famous '`*`' and '`#`' keys for jumping by bounded regular expression.

**Manual regular expression searching** - Using '`/`' and '`?`' to manually search.

* * * * *

<a name="Basic_Movement_3"/>
Basic Movement (Screencast 3)
=============================

See it straight from [Vimeo](http://vimeo.com/user1690209) at [Basic Movement (Screencast 3)](http://vimeo.com/6216655).

<object width="400" height="306">
    <param name="allowfullscreen" value="true" />
    <param name="allowscriptaccess" value="always" />
    <param name="movie" value="http://vimeo.com/moogaloop.swf?clip_id=6216655&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=1&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" />
    <embed src="http://vimeo.com/moogaloop.swf?clip_id=6216655&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=1&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" width="400" height="306"></embed>
</object>

What we'll cover
----------------

**Start of Function or Class Jumping** - Moving to the beginning of functions and classes backwards through the buffer with `[[` and the beginning of functions and classes forwards through the buffer with `]]` (assuming you code properly and put braces for these things in column 0 (hanging brace?? Come on! :D).

**End of Function or Class Jumping** - Forwards to the end of a function or class definition with `][` and backwards to the end of a function or class definition with `[]`.

**Jumping to Matching Braces** - The fantastic `%` characters.

**Marks** - Basic mark functionality and how it works with `m`, `'` and `` ` ``.

* * * * *

<a name="Basic_Editing_1"/>
Basic Editing (Screencast 1)
============================

See it straight from [Vimeo](http://vimeo.com/user1690209) at [Basic Editing (Screencast 1)](http://vimeo.com/6329762).

<object width="400" height="316">
    <param name="allowfullscreen" value="true" />
    <param name="allowscriptaccess" value="always" />
    <param name="movie" value="http://vimeo.com/moogaloop.swf?clip_id=6329762&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=1&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" />
    <embed src="http://vimeo.com/moogaloop.swf?clip_id=6329762&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=1&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" width="400" height="316"></embed>
</object>

What we'll cover
----------------

**Starting an Insert** - The main keys for getting into [Insert Mode](http://vimdoc.sourceforge.net/htmldoc/insert.html#insert.txt) are `i` and `I`.

**Starting an Insert with a New Line** - Another way to get into [Insert Mode](http://vimdoc.sourceforge.net/htmldoc/insert.html#insert.txt) is with `o` and `O` for creating new lines.

**Starting an Insert with Append** - You can also get into [Insert Mode](http://vimdoc.sourceforge.net/htmldoc/insert.html#insert.txt) with `a` and `A`.

**Replacing Characters** - You can replace characters (like turning off "insert" in Notepad) with `r` and `R`.

**Changing Things** - You can change characters or motion related things using `c` and `C`.

**Deleting Characters** - Delete a single character under the cursor with `x` and before the cursor with `X`.

**Deleting Lines** - Delete a single line with `dd`.

**Repeat** - One of the absolutely core and biggest features of Vi is something very simple: the '`.`' operator. Repeat the last command by hitting '`.`'.

* * * * *

<a name="Basic_Editing_2"/>
Basic Editing (Screencast 2)
============================

See it straight from [Vimeo](http://vimeo.com/user1690209) at [Basic Editing (Screencast 2)](http://vimeo.com/6332848).

<object width="400" height="316">
    <param name="allowfullscreen" value="true" />
    <param name="allowscriptaccess" value="always" />
    <param name="movie" value="http://vimeo.com/moogaloop.swf?clip_id=6332848&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=1&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" />
    <embed src="http://vimeo.com/moogaloop.swf?clip_id=6332848&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=1&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" width="400" height="316"></embed>
</object>

What we'll cover
----------------

**Yanking** - Yanking is "copying" in the "lousy editor" vernacular and it's done with the `y` key and `Y` key.

**Putting** - Putting is the inverse of yanking, and once you've yanked, you can put with the `p` key and `P` key.

**Joining** - You can join lines with the `J` key but it will put a space at the join position, so if you don't want that to happen you need to use `gJ`.

**Visual Mode** - We cover the three [Visual Modes](http://vimdoc.sourceforge.net/htmldoc/visual.html#visual-mode) using the `v` key for character-wise visual selection, `V` for line-wise selection and `CTRL-v` for block-mode selection. The all-important `gv` sequence is also covered to help you re-select an area you just selected.

* * * * *

<a name="Many_Files_1"/>
Working with Many Files (Screencast 1)
======================================

See it straight from [Vimeo](http://vimeo.com/user1690209) at [Working with Many Files (Screencast 1)](http://vimeo.com/6306508).

<object width="400" height="306">
    <param name="allowfullscreen" value="true" />
    <param name="allowscriptaccess" value="always" />
    <param name="movie" value="http://vimeo.com/moogaloop.swf?clip_id=6306508&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=1&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" />
    <embed src="http://vimeo.com/moogaloop.swf?clip_id=6306508&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=1&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" width="400" height="306"></embed>
</object>

What we'll cover
----------------

**Listing Buffers** - Good ol' `ls` is your friend here.

**Switching Buffers** - The `buffer` command gets you there and it can be used a ton of different ways.

**Buffer deletion** - The `bdelete` command is used to delete buffers and it can be used in an equally vast number of ways.

* * * * *

<a name="Many_Files_2"/>
Working with Many Files (Screencast 2)
======================================

See it straight from [Vimeo](http://vimeo.com/user1690209) at [Working with Many Files (Screencast 2)](http://vimeo.com/6307101).

<object width="400" height="306">
    <param name="allowfullscreen" value="true" />
    <param name="allowscriptaccess" value="always" />
    <param name="movie" value="http://vimeo.com/moogaloop.swf?clip_id=6307101&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=1&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" />
    <embed src="http://vimeo.com/moogaloop.swf?clip_id=6307101&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=1&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" width="400" height="306"></embed>
</object>

What we'll cover
----------------

**Args List** - The list of loaded files (by default) is put in the `:args` list, and we will learn how to move through it.

**The Buffer List** - What it is and how we can move through it.

**Bufdo** - The `bufdo` command lets us run a command over all of our loaded buffers. Keep this in the front of your brain!

* * * * *

<a name="Many_Files_3"/>
Working with Many Files (Screencast 3)
======================================

See it straight from [Vimeo](http://vimeo.com/user1690209) at [Working with Many Files (Screencast 3)](http://vimeo.com/6342264).

<object width="400" height="360">
    <param name="allowfullscreen" value="true" />
    <param name="allowscriptaccess" value="always" />
    <param name="movie" value="http://vimeo.com/moogaloop.swf?clip_id=6342264&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=1&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" />
    <embed src="http://vimeo.com/moogaloop.swf?clip_id=6342264&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=1&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" width="400" height="360"></embed>
</object>

What we'll cover
----------------

**Splitting Windows** - There's vertical splits and horizontal splits. Split horizontally with `:split` or `CTRL-w s` and vertically with `:vsplit` or `CTRL-w v`.

**Closing Windows** - You can close with `:close` or `CTRL-w c`.

**Switching Windows** - Change windows with `CTRL-w h`, `CTRL-w j`, `CTRL-w k` or `CTRL-w l`.

**Switching Windows (continued)** - You can also switch to the "previous" window with `CTRL-w p`.

**Switching Windows (continued again)** - If you want to jump around windows a bit quicker then you can pass a numeric argument to the `CTRL-w {motion}` command such as `5CTRL-w k` to move up `5` windows.

**Moving Windows** - If you want to reposition a window to another spot, you can use the `CTRL-w H`, `CTRL-w J`, `CTRL-w K` or `CTRL-w L` commands.

**Focusing a Window** - If you want to de-clutter your workspace and make the current window the only visible window then you can hit `CTRL-w o`.

You can check out my mappings for dealing with windows in [the vimrc section](/vim/the-vimrc-file/walking-around-your-windows/).

* * * * *

<a name="Help"/>
How to use the Help System
==========================

See it straight from [Vimeo](http://vimeo.com/user1690209) at [How to use the Help System](http://vimeo.com/7035132).

<object width="480" height="360">
    <param name="allowfullscreen" value="true" />
    <param name="allowscriptaccess" value="always" />
    <param name="movie" value="http://vimeo.com/moogaloop.swf?clip_id=7035132&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=0&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" />
    <embed src="http://vimeo.com/moogaloop.swf?clip_id=7035132&amp;server=vimeo.com&amp;show_title=1&amp;show_byline=0&amp;show_portrait=0&amp;color=00ADEF&amp;fullscreen=1" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" width="480" height="360"></embed>
</object>

What we'll cover
----------------

**How to get in to help** - `:help`, `:h` or `F1`.

**How to navigate** - `CTRL-]` moves you into a hyperlink and `CTRL-T` moves you back in your hyperlink history.

**How to jump to a topic** - `:h <topic-name>` jumps you straight to the topic.

**Tab completion for help** - Using [`:set 'wildmenu'`](http://vimdoc.sourceforge.net/htmldoc/options.html#'wildmenu') to help you complete on the help command.

**Grepping through the help** - `:helpgrep` searches through the help documentation and `:cwindow` shows you the results in a very cool way.

Of course, use `:help` on any of the above topics for more information on that topic.
