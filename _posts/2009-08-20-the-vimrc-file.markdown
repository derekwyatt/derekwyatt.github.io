---
layout: post
title: The vimrc File
---
The Heart of Your Vim Setup
---------------------------

Your [`vimrc`](http://vimdoc.sourceforge.net/htmldoc/starting.html#.vimrc) file is an incredibly powerful, incredibly useful thing. Anything you can do in [command-line mode](http://vimdoc.sourceforge.net/htmldoc/cmdline.html#Command-line) you can do in your [`vimrc`](http://vimdoc.sourceforge.net/htmldoc/starting.html#.vimrc) file. This makes it possible to make settings (e.g. `:set textwidth=80`) in the [command-line mode](http://vimdoc.sourceforge.net/htmldoc/cmdline.html#Command-line) and then make them permanent in your [`vimrc`](http://vimdoc.sourceforge.net/htmldoc/starting.html#.vimrc) file by just doing the exact same thing but in a file instead of in the running Vim session.

Where it is
-----------

Depending on what operating system you're running on the [`vimrc`](http://vimdoc.sourceforge.net/htmldoc/starting.html#.vimrc) file will be in a slightly different place. However, no matter where it actually is you can reference it from within Vim using the [`$MYVIMRC`](http://vimdoc.sourceforge.net/htmldoc/starting.html#$MYVIMRC) variable.

**On [Unix](http://en.wikipedia.org/wiki/Unix) ([Linux](http://www.linux.org), [OS X](http://www.apple.com/macosx/), whatever...)**

On these operating systems, the [`vimrc`](http://vimdoc.sourceforge.net/htmldoc/starting.html#.vimrc) file defaults to being in your home directory at `~/.vimrc`. If it's not there then just create it by editing it and saving it.

**On [Microsoft Windows](http://www.windows.com)**

On all of the MS Windows variants you'll also find this in your [HOME Directory](http://en.wikipedia.org/wiki/Home_directory#Default_Home_Directory_per_Operating_System) but most people don't know where that is ;). I'm not going to try and tell you where it is, specifically and leave that up to [Wikipedia's Home Directory Page](http://en.wikipedia.org/wiki/Home_directory#Default_Home_Directory_per_Operating_System). In general however it is one directory up from the directory that contains your "Documents" (e.g. "My Documents" in [Windows XP](http://www.microsoft.com/windows/windows-XP/)). The major difference is that it's *named slightly differently* than it is in Unix: it's named [\_vimrc](http://vimdoc.sourceforge.net/htmldoc/starting.html#_vimrc).

What's it for?
--------------

*Everything*. Ok, that's not strictly true but at this stage in the game it's more than sufficient to look at it that way. In practice you'll find that [plugins](http://vimdoc.sourceforge.net/htmldoc/usr_05.html#plugin) and many other types of [file type specific](http://vimdoc.sourceforge.net/htmldoc/filetype.html#:filetype) configuration will make use of Vim's extensive capabilities in its [runtime](http://vimdoc.sourceforge.net/htmldoc/options.html#vimfiles) structure, outside of the [`vimrc`](http://vimdoc.sourceforge.net/htmldoc/starting.html#.vimrc) file but that sort of thing comes... later.

Generally you should think of your [`vimrc`](http://vimdoc.sourceforge.net/htmldoc/starting.html#.vimrc) file as holding your own personal [option settings](http://vimdoc.sourceforge.net/htmldoc/options.html#options.txt), [mappings](http://vimdoc.sourceforge.net/htmldoc/map.html#map.txt), [functions](http://vimdoc.sourceforge.net/htmldoc/eval.html#user-functions), [commands](http://vimdoc.sourceforge.net/htmldoc/map.html#user-commands) and whatever else you need to customize Vim for your personal use.

Specific Examples
-----------------

Here we'll get more specific and actually look at some real contents of a [`vimrc`](http://vimdoc.sourceforge.net/htmldoc/starting.html#.vimrc) file.

- [The Absolute Bare Minimum](the-absolute-bare-minimum)
- [Toggling and Shortening](toggling-and-shortening)
- [Walking Around Your Windows](walking-around-your-windows)
- ["Better" Settings](better-settings)
