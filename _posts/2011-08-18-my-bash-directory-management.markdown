---
layout: post
title: My BASH directory management
tags:
- shell
- unix
---
I saw a post on [Supuser.com](http://superuser.com/questions/324512/cd-option-to-go-back-rather-than-up) that asked about how to go "back" to a directory rather than "up". The right answer was "`cd -`", of course, but I've been using a home-grown tool to do this for years and it works really well.

It started out as a [Korn Shell](http://www.kornshell.com/ "Watch your eyes when you click that link... whoah, what an ugly website.") so there are some remnants of that in it, but it does work for [Bash](http://www.gnu.org/s/bash/).

It does the usual thing of replacing the '`cd`' built-in command with a function by aliasing it away. Every time you change directories, it saves the last place on the stack (up to a max of 15). You can look at the directory history and switch to any of them based on their number and also a regular expression.

This sort of functionality, whether you use mine or someone else's or build your own, is **vital** to efficient command line *kung-fu*. How the hell have you been surviving this long without it???

Here's the [Gist](https://gist.github.com/1154129) of the code that you can grab.

- **`cd`** - You know what this does. And, yes, both `cd -` and `cd {pat} {subst}` should also work just fine.
- **`ss`** - Short for "show stack".
- **`csd`** - Short for "change to stacked directory". This is the fun one that I'll show in an example below.

Here's how you use it (I'll leave it to you to read what I'm doing and figure out how you can use it):

    --(/home/dwyatt/scala/scala/src/library/scala/collection/generic)--------------------------------------
    --> ss
    1) /home/dwyatt/scala/scala/src/library/scala
    2) /home/dwyatt/scala/scala/src/library/scala/concurrent
    3) /home/dwyatt/scala/scala/src/library/scala/collection/mutable
    4) /home/dwyatt/scala/scala/src/library/scala/collection/immutable
    5) /home/dwyatt/scala/scala
    6) /home/dwyatt/scala
    7) /home/dwyatt
    --(/home/dwyatt/scala/scala/src/library/scala/collection/generic)--------------------------------------
    --> csd 4
    --(/home/dwyatt/scala/scala/src/library/scala/collection/immutable)------------------------------------
    --> ss
    1) /home/dwyatt/scala/scala/src/library/scala/collection/generic
    2) /home/dwyatt/scala/scala/src/library/scala
    3) /home/dwyatt/scala/scala/src/library/scala/concurrent
    4) /home/dwyatt/scala/scala/src/library/scala/collection/mutable
    5) /home/dwyatt/scala/scala
    6) /home/dwyatt/scala
    7) /home/dwyatt
    --(/home/dwyatt/scala/scala/src/library/scala/collection/immutable)------------------------------------
    --> csd ent$
    --(/home/dwyatt/scala/scala/src/library/scala/concurrent)----------------------------------------------
    --> ss
    1) /home/dwyatt/scala/scala/src/library/scala/collection/immutable
    2) /home/dwyatt/scala/scala/src/library/scala/collection/generic
    3) /home/dwyatt/scala/scala/src/library/scala
    4) /home/dwyatt/scala/scala/src/library/scala/collection/mutable
    5) /home/dwyatt/scala/scala
    6) /home/dwyatt/scala
    7) /home/dwyatt
    --(/home/dwyatt/scala/scala/src/library/scala/concurrent)----------------------------------------------
    --> csd 5
    --(/home/dwyatt/scala/scala)---------------------------------------------------------------------------
    --> ss
    1) /home/dwyatt/scala/scala/src/library/scala/concurrent
    2) /home/dwyatt/scala/scala/src/library/scala/collection/immutable
    3) /home/dwyatt/scala/scala/src/library/scala/collection/generic
    4) /home/dwyatt/scala/scala/src/library/scala
    5) /home/dwyatt/scala/scala/src/library/scala/collection/mutable
    6) /home/dwyatt/scala
    7) /home/dwyatt
    --(/home/dwyatt/scala/scala)---------------------------------------------------------------------------
    --> cd ~
    --(/home/dwyatt)--------------------------------------------------------------------------------------------
    --> cd -
    --(/home/dwyatt/scala/scala)---------------------------------------------------------------------------
    --> 
