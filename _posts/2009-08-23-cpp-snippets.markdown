---
layout: post
title: C++ Snippets
tags:
- vim
- c++
---
**UPDATE**: Go to [http://github.com/derekwyatt/vim-config](http://github.com/derekwyatt/vim-config) to get the current version of this stuff.

I'm not entirely sure where the "snippet" concept came from but it certainly appears to have been made famous by [TextMate](http://macromates.com/). And in the great sprit of ripping stuff off, people have gone nuts in Vim community making snippet plugins:

I've used four snippet template systems...

- [snippetsEmu](http://www.vim.org/scripts/script.php?script_id=1318) was the first.
- [snipMate](http://www.vim.org/scripts/script.php?script_id=2540) came next and improved upon snippetsEmu in a big way
- [xptemplate](http://www.vim.org/scripts/script.php?script_id=2611) is what I'm using now - it's still the best.
- [UltiSnips](http://www.vim.org/scripts/script.php?script_id=2715) is a new contender that I've tried but it's a bit fragile at the moment. It has some serious potential but it's not quite there.

Some of the snippets that are included in the [xptemplate](http://www.vim.org/scripts/script.php?script_id=2611) plugin file you can can download below.

- `test` -- Creates an entire C++ implementation file for [CPPUNIT](http://sourceforge.net/apps/mediawiki/cppunit/index.php?title=Main_Page) that lets you get right into coding up your test immediately.
- `tf` -- Test function definition that works with the automatic registration code defined in [General C++ Settings](/vim/working-with-vim-and-cpp/general-cpp-settings/).
- `try` -- Try/Catch blocks.
- `tsp` -- Typedef to a `std::tr1::shared_ptr`.
- `header` -- Creates a header file for a given class, includes the namespace (if your directory to the file is like `include/name/space/goes/here`), include guards, and the whole deal.
- `src` -- Creates a source file with a companion to the header file. Pulls in definitions using [Protodef](http://www.vim.org/scripts/script.php?script_id=2624), and also requires [FSwitch](http://www.vim.org/scripts/script.php?script_id=2590).
-   ... and more ...

[Download the snippets file here](/wp-content/uploads/2009/08/my.cpp.xpt.vim).

Download [FSwitch here](http://www.vim.org/scripts/script.php?script_id=2590).

Download [Protodef here](http://www.vim.org/scripts/script.php?script_id=2624).
