---
layout: post
title: Pathogen, baby!
---
So there's been some scuttlebutt on the Twitters recently regarding this ["Pathogen" script for Vim](http://www.vim.org/scripts/script.php?script_id=2332) and I decided to have a look. In a word? "Sweet". In a few words? [Tim Pope](http://www.vim.org/account/profile.php?user_id=9012) is the absolute man.

This is an extremely simple and elegant script. All it does is manipulate the ['runtimepath'](http://vimdoc.sourceforge.net/htmldoc/options.html#'runtimepath') but it has a nice focus on allowing you to componentize your Vim extensions into their own, private ['runtimepath'](http://vimdoc.sourceforge.net/htmldoc/options.html#'runtimepath') tree segments. So what? So what?!? Now you can easily upgrade your extensions by just deleting the old tree, downloading the package and exploding it in place.

This would have saved my ass when [xptemplate](http://www.vim.org/scripts/script.php?script_id=2611) went through a revision that deleted files, and I didn't notice. Having unwanted, autoloaded files in place was not a good thing.

And you can also just toss git suppositories straight into this as well - perfect updating. 

Check out [Tammer Saleh](http://tammersaleh.com)'s post called [The Modern Vim Config with Pathogen](http://tammersaleh.com/posts/the-modern-vim-config-with-pathogen) for a concise description on how to get it into your vimrc.
