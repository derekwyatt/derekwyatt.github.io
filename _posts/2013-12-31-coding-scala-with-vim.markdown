---
layout: post
title: Coding Scala with Vim
header-img: img/compact-discs.png
abstract: A video on how I code Scala with Vim, along with a lot of explanation as to how it gets done.
tags:
- vim
- scala
- sbt
---
There are [Vim][1] users and there are [Scala][2] coders and, occassionally those two wonderful things happen to get mixed up into the same person.  I'm one of those people, and have finally gotten around to sharing how I code [Scala][2] with [Vim][1] in a screencast.

Watch it on [Vimeo][23] using [this link](https://vimeo.com/83065681), or just watch it below.

<center><iframe src="//player.vimeo.com/video/83065681" width="700" height="450" frameborder="0" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe> <p><a href="http://vimeo.com/83065681">Coding Scala in Vim</a> from <a href="http://vimeo.com/user1690209">Derek Wyatt</a> on <a href="https://vimeo.com">Vimeo</a>.</p></center>

The Workflow
------------

This is exceedingly simple; it involves only two GUI elements: [Vim][1] and a terminal window.

<a href="/images/vim-and-terminal.png" target="_blank">
<img src="/images/vim-and-terminal.png" alt="MacVim and iTerm side-by-side" width="780" class=unadorned />
</a>

[SBT][10] does its thing in the terminal all day long (running `~test`) while I get to code in [Vim][1].  Actually... that's about it.  While inside [Vim][1] I use various plugins to navigate between files, and then classic [Vim][1] techniques to manipulate windows and do all of the text twiddling magic that all [Vim][1] users are used to.

- `,fr` opens a file using [CtrlP][4] from anywhere in the project.
- `,fb` helps me switch to a loaded buffer somewhere, again using [CtrlP][4].
- `,of` flips me between a "production" source code file and its test file companion, and vice versa using some custom [Vim][1] code I wrote for Scala files.

It's possible that [Vim][1] is the _second_ most important tool in my day-to-day, if not my [Scala][2] workflow specifically; the most important one is the Unix shell.  I use [ZSH][12], along with the [oh my zsh][16] configuration package and a [custom plugin that interacts with Vim][13].

My experience with IDEs (and yes, I've used them) leaves me wanting in a number of areas, but one of the most crucial is any integration with the Unix shell.  The [ZSH plugin][13] lets me talk to my running [Vim][1] instance from wherever I happen to be.  If I couldn't do this, then I'd have to have my editor completely replicate all of the power I would otherwise be missing from not being able to use the shell.

What I Give Up
--------------

The [Scala IDE][14] gives you all the good stuff that you can get from [Eclipse][15], including code completion, build integration, debugging, refactoring and so forth.  I don't have any of that stuff in my workflow -- and I don't care :)  If you really care, then my workflow isn't for you.

Honestly, I haven't used a debugger in many years now - I'm a caveman kinda guy, using log statements and `println` calls wherever I happen to need them.  There are times when I am envious of code completion but I'm not willing to pay the speed price to integrate it with [Vim][1].  Every IDE I've used, as well as the pseudo-equivalent [Vim][1] plugins have a particular _weight_ associated with them that just messes with my life.

Refactoring tools? Well, yeah... that'd be nice.  But that's 1% of what I need and I'd rather optimize the 99%.

Simplicity
----------

- There are a ton of plugins for [Vim][1] and I do use a number of them, but I'm not a junkie about it.  I have tried using the [VimSIde][8] plugin and the [TagBar][9] plugin, but neither were fast enough for me.  The latter was pretty fast, but not terribly effective.
- [ZSH][12] is key
  - Integrating [ZSH][12] with [Vim][1] happens with a custom [ZSH plugin][13].
- [SBT][10] is, obviously, a big deal too.
  - I don't make any attempt to "integrate" [SBT][10] with [Vim][1].  A while back, I took a swing at it by using the [SBT Quickfix][11] plugin, but it just didn't fit with my workflow.
  - [SBT][10] runs in a separate terminal, doing its thing while I just code away; if [SBT][10] does something that requires my attention then I'll look at it, but for the most part, my peripheral vision takes control.  If the screen's got RED on it, then I look... otherwise, who cares?

Plugins I Use
-------------

- [Scala Plugin][3] - I'm the sole maintainer of this thing.  If you're looking to help out, or even just file some issues, please do!
- [CtrlP Plugin][4] - I used to use [FuzzyFinder][5] but was swayed to change due to the awesomeness (not to mention the active development of) [CtrlP][4].
- [XPTemplate Plugin][6] - This is the template generator that makes dealing with code boilerplate a bit easier.
- [BufKill Plugin][7] - Makes it easier to kill buffers without wrecking the window layout.

My Configuration
----------------

I've got a few [Github][17] repositories that cover my configuration.

- You can find my [Vim configuration][18] there, along with some scripts that pull in all of the plugins I use.
- My [dotfiles][19] that configure my shell and various other things.
- When I'm working in [Linux][20], I use a keen window manager called [Notion][21] and [Github holds that configuration too][22].  How you navigate your window manager is pretty big deal to your workflow, and [Notion][21] is a _ton_ better than the OS X window manager.

That's it.  If it works for you, go for it.  If it doesn't... well, you read this far?  Neat.

  [1]: http://www.vim.org/ "Vim"
  [2]: http://scala-lang.org/ "Scala"
  [3]: https:/github.com/derekwyatt/vim-scala/ "Scala Plugin"
  [4]: http://kien.github.io/ctrlp.vim/ "CtrlP Plugin"
  [5]: http://www.vim.org/scripts/script.php?script_id=1984 "FuzzyFinder Plugin"
  [6]: https://github.com/drmingdrmer/xptemplate "XPTemplate Plugin"
  [7]: https://github.com/vim-scripts/bufkill.vim "BufKill Plugin"
  [8]: https://github.com/megaannum/vimside "VimSIde Plugin"
  [9]: http://www.vim.org/scripts/script.php?script_id=3465 "Tagbar Plugin"
  [10]: http://www.scala-sbt.org/ "SBT"
  [11]: https://github.com/dscleaver/sbt-quickfix "SBT Quickfix"
  [12]: http://www.zsh.org/ "ZSH"
  [13]: https://github.com/derekwyatt/dotfiles/tree/master/zsh_custom/plugins/vim-interaction "ZSH Vim Plugin"
  [14]: http://scala-ide.org/ "Scala IDE"
  [15]: http://www.eclipse.org/ "Eclipse"
  [16]: https://github.com/robbyrussell/oh-my-zsh "oh my zsh"
  [17]: https://github.com/derekwyatt "me@github"
  [18]: https://github.com/derekwyatt/vim-config "my vim configuration"
  [19]: https://github.com/derekwyatt/dotfiles "my dotfiles for shell configuration"
  [20]: http://www.ubuntu.com "Ubuntu"
  [21]: http://notion.sourceforge.net "Notion"
  [22]: https://github.com/derekwyatt/notion-config "my notion configuration"
  [23]: http://vimeo.com "Vimeo"
