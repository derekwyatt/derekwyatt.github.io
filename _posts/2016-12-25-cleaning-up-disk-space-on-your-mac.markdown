---
layout: post
title: Cleaning up Disk Space on your Mac
header-img: img/old-bridge.jpg
abstract: How to use the terminal to find out where your disk space is going and how to clean it up
tags:
- unix
- mac
---
My nephew asked me (again) how to clean up disk space on his mac.  He tells me that he deletes files and empties the trash but nothing happens.  Clearly he's either:

1. Drunk
2. Speaking through one of many different personalities that he posesses, none of which know what the other is doing
3. Lying

There are a number of pages out there that will tell you how to [reclaim][1] [disk][2] [space][3] but the best tools to do this are good ol' unix tools that have been around for decades.  Incidentally, [Disk Inventory X][4] is also a great tool for visualizing what your disk usage looks like so you should download it, run it, and see what you've got.  But here, I'm going to show you what I do.

First, you'll need a command line shell, so go to the `Finder` and launch `Applications -> Utilities -> Terminal`.  From there you'll want to do this:

``` bash
# Change to your home directory
> cd ~

# Find out how much disk you have available
> df .
Filesystem     1K-blocks      Used Available Use% Mounted on
/dev/disk1     487350400 458293832  28800568  95% /

# You can see that I have, essentially, a 500GB disk on my mac and I'm using nearly all of it.
# If I want to know where it's going, I would first find out how much is being used in my
# home directory.

# This might take a while to run so be patient
> du -sh
354G	.

# 354 GB.  Well, that's a lot.  Now let's break that down:
> du -sk * | sort -n
0	Public
4	molex.parts.txt
8	Desktop
8	song.txt
20	bin
240	scratch
1228	Applications
6292	Pictures
1385960	Macheist
1561260	code
2864748	Dropbox
2955184	Documents
11738324	Downloads
12477984	Library
19843364	Movies
29010916	Videos - March 22nd 2009
43003148	Music
48296516	AudioData
195036888	Guitar
```

That's the most important command. You run `du` in in summary mode, where all of the numbers are in _kilobytes_ and then you sort them numerically, where the larger numbers go to the bottom.  I do a lot of work with my guitar and I have a lot of data associated with it so it's the biggest by far.  Let's take the `Music` directory and see what's in there:

``` bash
# Head into the Music diredtory and rerun the du command there
> cd Music
> du -sk * | sort -n
716	Audio Music Apps
42968676	iTunes

# Not much of value in that, so let's look in the iTunes directory
> cd iTunes
> du -sk * | sort -n
4	sentinel
16	iTunes Library Extras.itdb
32	iTunes Library Genius.itdb
940	iTunes Library.itl
1944	Previous iTunes Libraries
212240	Album Artwork
42753492	iTunes Media

# Keep going
> cd iTunes\ Media
> du -sk * | sort -n
0	Automatically Add to iTunes.localized
0	Downloads
7256	Voice Memos
3331504	Audiobooks
39414720	Music

# Let's look in Music.  But I'm only going to be interested in the top 2
> cd Music
> du -sk * | sort -n | tail -2
1728208	Pat Metheny
1844824	Dave Matthews Band
```

Dave an Pat!  I love Dave and Pat's just ridiculously good... Well, I'm not going to delete them but it was fun to look.  If I wanted to wipe out Dave then I could go to iTunes and delete him, but there's no way I doing that.

There you go.  That's how to see what you've got per folder, but don't forget about [Disk Inventory X][4] because it does, essentially, the same thing but graphically and more fun, if you're afraid of the command line.

  [1]: http://www.macworld.co.uk/how-to/mac/how-free-up-disk-space-on-your-mac-3504803/
  [2]: http://osxdaily.com/2013/05/10/advanced-tricks-reclaim-disk-space-mac-os-x/
  [3]: http://www.howtogeek.com/184091/5-ways-to-free-up-disk-space-on-a-mac/
  [4]: http://www.derlien.com/
