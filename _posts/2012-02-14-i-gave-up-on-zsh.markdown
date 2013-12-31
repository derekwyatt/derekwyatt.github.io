---
layout: post
title: I gave up on Zsh
---
Ok, so everyone and their grandmother appears to be using [Zsh](http://www.zsh.org/) and I was starting to wonder what was wrong with me. Why was I still using [Bash](http://www.gnu.org/software/bash/)? So I grabbed [Oh-My-Zsh](https://github.com/robbyrussell/oh-my-zsh) (which was pretty cool, BTW) and dove in... but here's the problem - I didn't have time to dive in.

I'm sure that, had I days to dedicate to getting Zsh working, that I would probably find the "right" way to use it, and make myself happy. But, given that I just wanted to start using it and figure things out later... well, it absolutely f'ing sucked! I got no benefit from this approach and a lot of pain. Here's why it sucked:

- In bash, if I type -- `grep thing.*other myfile.txt` -- and -- `thing.*other` -- doesn't glob, do you know what I get? That's right, I get -- `thing.*other`. With Zsh, *I get an error.*
- Same thing for -- `sbt ~test`. *Blurgh!*
- I like a two line status. It keeps all of that long path information, git stuff, and whatnot on a line by itself, and then gives me a whole fresh line for writing commands. I get this in Bash, and I get this in Zsh. Difference? Every time I hit -- `<ESC> k` -- in Zsh, it would erase the line above it - a rendering bug. You know what? I really needed to read that line, thanks very much.
- The directory stacking is pretty terrible, which is insane considering that "cd" is the command you probably run more than any other in the shell, *by far*.

There were some other annoying bits too, but not too big of a deal to mention.

The bottom line here, is that before I would be able to successfully use Zsh, I would have to figure out how to get it to *stay the hell out of my way*. Said another way: If there's a feature of Zsh that I only use 0.5% of the time, then it should not be defaulted to be *on* 100% of the time such that 99.5% of the time I have to protect myself from it.

I know, I know... I didn't give it a chance. Very true. What I was expecting was that I could use it and access features when I was willing to head down that path, but what I found was that I spent nearly all my time working around annoyances just to get things done.
