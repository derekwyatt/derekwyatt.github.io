---
layout: post
title: General C++ Settings
---
**UPDATE**: Go to [http://github.com/derekwyatt/vim-config](http://github.com/derekwyatt/vim-config) to get the current version of this stuff. These settings go into the `$VIM/after/ftplugin/cpp.vim` file. You can find the file linked [here](/wp-content/uploads/2009/08/cpp.vim). You'll find that it's heavily commented so you shouldn't have too much trouble figuring out what's there and why. Of course you can always use the `:help` functionality to discover what the Vim-specific settings and commands do. It contains the following features:

- Proper comment formatting independent of code formatting
- Highlighting of "bad" leading tabs and code that goes beyond 120 columns
- Code that automatically updates [CPPUNIT](http://sourceforge.net/apps/mediawiki/cppunit/index.php?title=Main_Page) test macros to match the tests you've actually written
- Definitions for the [FSwitch](http://www.vim.org/scripts/script.php?script_id=2590) plugin.
- Mappings to help with some of the usual wants and needs.
- Changes the indent expression so that we ignore `namespaces`. I do not appreciate it when Vim decides to add a '[shiftwidth](http://vimdoc.sourceforge.net/htmldoc/options.html#'shiftwidth')' to my code just because it's in a namespace.
