---
layout: post
title: Versioning your Vim configuration
header-img: img/compact-discs.jpg
abstract: How to version your Vim configuration with Perforce.
tags:
- vim
---
I use [Vim](http://www.vim.org) mostly at work on Windows (don't even get me started on that lovely O/S), working on two different machines - my desktop and my laptop - neither of which are backed up. That presents a couple of problems:

- How do I keep the two machines up to date with each other?
- How do I avoid the death crash from hell?

I solve both of those problems with one move - keep the configuration in [Perforce](http://www.perforce.com). Now, I'm not a huge fan of Perforce but it's all I've got at work, and it certainly does the job when I want to solve these problems. You don't need much to handle this issue so any version control system should do (so long as the repository is remote and backed up for you, of course :D).

Once you've set up your Vim files into your version control system, you just need to tell Vim where they are. Â You do this with the `$VIM` environment variable (or `%VIM%` if you're on Windows and you just want to be really pedantic about the details :D). Â You don't need to do this, of course, if your version controlled Vim files map to the same place as Vim would normally look, but in my case they map to somewhere really weird so I need to help Vim out by telling it where the files are.

Then it's just like versioning any other piece of code. Â Check config files out, modify them, check them in, sync them to other machines. Â Normally I work on my desktop box, so the first thing I do when I power up my laptop is `p4 sync` my Vim configuration so I can get the exact same experience as I had on my desktop, right away.
