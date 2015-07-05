---
layout: post
title: Back to ZSH
header-img: img/compact-discs.png
tags:
- shell
- unix
---
A little while ago, [I gave up on Zsh](http://www.derekwyatt.org/2012/02/14/i-gave-up-on-zsh/). There were a number of people that showed me how I could have fixed up [Zsh](http://www.zsh.org/) so it would suck less, but I just didn't have time. Well, I found myself with some time yesterday that couldn't be spent doing much useful, so I made the switch.

It now feels like [Bash](http://www.gnu.org/software/bash/) but a little bit better, so I'm pretty happy. Thanks to [@datanoise](http://twitter.com/#!/datanoise), and others for the tips.

Here's what I did to make it useful:

- `unsetopt nomatch`: I have *no idea* why this defaults to "on". It's useful about %0.00001 of the time and a pain in the bloody ass the other %99.99999 of the time.
- Use `bindkey -v` instead of the `vi-mode` plugin: This is also odd. Most of what is needed (the prompt stuff) is pointless eye-candy and the `zle reset-prompt` is just an awful feature. You hit `ESC-\` and it deletes the line above... which a lot of the time, carried data that you really wanted to read.
- Ported my directory stacking stuff to Zsh (just barely :D). I can't leave this feature behind... without it, running around the filesystem is a major drag.

My config can be found on [GitHub](http://github.com/derekwyatt/dotfiles/) if you're interested in having a look.
