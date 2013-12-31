---
layout: post
title: Coding Scala with Vim
---
There are [Vim] [1] users and there are [Scala] [2] coders and, occassionally those two wonderful things happen to get mixed up into the same person.  I happen to be one of those people, and have finally gotten around to sharing how I work with [Scala] [2] code in [Vim] [1] in a screencast.

The Workflow
------------

There are two GUI elements involved: [Vim] [1] and a terminal window.

<a href="/images/vim-and-terminal.png" target="_blank">
<img src="/images/vim-and-terminal.png" alt="MacVim and iTerm side-by-side" width="780"/>
</a>

[SBT] [10] does its thing in the terminal all day long (running `~test`) while I get to code in [Vim] [1].  Actually... that's about it :)

What I Give Up
--------------

The [Scala IDE] [14] gives you all the good stuff that you can get from [Eclipse] [15], including code completion, build integration, debugging, refactoring and so forth.  I don't have any of that stuff in my workflow -- and I don't care :)  If you really care, then my workflow isn't for you.

Simplicity
----------

- There are a ton of plugins for [Vim] [1] and I do use a number of them, but I'm not a junkie about it.  I have tried using the [VimSIde] [8] plugin and the [TagBar] [9] plugin, but neither were fast enough for me.  The latter was pretty fast, but not terribly effective.
- [ZSH] [12] is key
  - Integrating [ZSH] [12] with [Vim] [1] happens with a custom [ZSH plugin] [13] I wrote.
- [SBT] [10] is, obviously, a big deal too.
  - I don't make any attempt to "integrate" [SBT] [10] with [Vim] [1].  A while back, I took a swing at it by using the [SBT Quickfix] [11] plugin, but it just didn't fit with my workflow.
  - [SBT] [10] runs in a separate terminal, doing its thing while I just code away; if [SBT] [10] does something that requires my attention then I'll look at it, but for the most part, my peripheral vision takes control.  If the screen's got RED on it, then I look... otherwise, who cares?

Plugins I Use
-------------

- [Scala Plugin] [3] - I'm the sole maintainer of this thing.  If you're looking to help out, or even just file some issues, please do!
- [CtrlP Plugin] [4] - I used to use [FuzzyFinder] [5] but was swayed to change due to the awesomeness (not to mention the active development of) [CtrlP] [4].
- [XPTemplate Plugin] [6] - This is the template generator that makes dealing with code boilerplate a bit easier.
- [BufKill Plugin] [7] - Makes it easier to kill buffers without wrecking the window layout.

Writing Tests
-------------

This is probably the most important thing that I actively use.  So long as I keep to a basic, and decent convention, navigating from production code to test code is a breeze.

    src/main/scala/package/hierarchy/here/Source.scala
    src/test/scala/package/hierarchy/here/SourceSpec.scala

The test file for `Source` is called `SourceSpec` and is located under `src/test` instead of `src/main`.  This convention makes it easy to switch between the two with any of a number of key mappings.  For example `,of` will open the "other" file (either `Source` if the current file is `SourceSpec` or vice versa) in the current window.


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
