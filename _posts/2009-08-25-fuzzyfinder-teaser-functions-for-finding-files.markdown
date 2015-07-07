---
layout: post
title: FuzzyFinder Teaser Functions (for finding Files)
header-img: img/dubrovnik-croatia.jpg
tags:
- vim
---
I've been playing with [FuzzyFinder](http://www.vim.org/scripts/script.php?script_id=1984) recently. I've had it on my list of "things to understand better" for a while now, but I've also had a couple of people asking smart questions like this:

> "Now that I am only using [One Vim](http://vimeo.com/4446112), how do you expect me to manage this jungle of files and buffers I've wound up with?"

Well, an app like [FuzzyFinder](http://www.vim.org/scripts/script.php?script_id=1984) is great for helping you tame that particular beast. I'll be putting up a video tutorial about it once I've got a better handle on the ins and outs of it, but for now, I'll give you a little teaser on how it can make life a bit better. Let's say you've got a code layout like this (which is a convention I've luckily managed to see happen where I work):

    /root/of/code/tree/component_name
    /root/of/code/tree/component_name/include/{directory per namespace}
    /root/of/code/tree/component_name/src
    /root/of/code/tree/component_name/test
    /root/of/code/tree/component_name/test/include
    /root/of/code/tree/component_name/test/src

You can probably imagine that I bounce around the `component_name/include/{whatever}`, and the `component_name/src` and the `component_name/test/src` areas a lot (I always write tests as pure implementation files - no headers). The [FSwitch](http://www.vim.org/scripts/script.php?script_id=2590) plugin helps a lot for switching between the header and the cpp file but that's not all you want to do... For the rest, FuzzyFinder is what's useful. However, even FuzzyFinder is a pain right out of the box because it's really not much better than just using the [command-line](http://vimdoc.sourceforge.net/htmldoc/cmdline.html#Command-line). Yeah it's a bit smarter but the initial navigation is still a pain. But it would be really snazzy if you could just give it a hint to get it started, and I've written a few functions to help out with that:

``` vim
"
" SanitizeDirForFuzzyFinder()
"
" This is really just a convenience function to clean up
" any stray '/' characters in the path, should they be there.
"
function! SanitizeDirForFuzzyFinder(dir)
    let dir = expand(a:dir)
    let dir = substitute(dir, '/\+$', '', '')
    let dir = substitute(dir, '/\+', '/', '')

    return dir
endfunction

"
" GetDirForFF()
"
" The important function... Given a directory to start 'from', 
" walk up the hierarchy, looking for a path that matches the
" 'addon' you want to see.
"
" If nothing can be found, then we just return the 'from' so 
" we don't really get the advantage of a hint, but just let
" the user start from wherever he was starting from anyway.
"
function! GetDirForFF(from, addon)
    let from = SanitizeDirForFuzzyFinder(a:from)
    let addon = expand(a:addon)
    let addon = substitute(addon, '^/\+', '', '')
    let found = ''
    " If the addon is right here, then we win
    if isdirectory(from . '/' . addon)
        let found = from . '/' . addon
    else
        let dirs = split(from, '/')
        let dirs[0] = '/' . dirs[0]
        " Walk up the tree and see if it's anywhere there
        for n in range(len(dirs) - 1, 0, -1)
            let path = join(dirs[0:n], '/')
            if isdirectory(path . '/' . addon)
                let found = path . '/' . addon
                break
            endif
        endfor
    endif
    " If we found it, then let's see if we can go deeper
    "
    " For example, we may have found component_name/include
    " but what if that directory only has a single directory
    " in it, and that subdirectory only has a single directory
    " in it, etc... ?  This can happen when you're segmenting
    " by namespace like this:
    "
    "    component_name/include/org/vim/CoolClass.h
    "
    " You may find yourself always typing '' from the
    " 'include' directory just to go into 'org/vim' so let's
    " just eliminate the need to hit the ''.
    if found != ''
        let tempfrom = found
        let globbed = globpath(tempfrom, '*')
        while len(split(globbed, "\n")) == 1
            let tempfrom = globbed
            let globbed = globpath(tempfrom, '*')
        endwhile
        let found = SanitizeDirForFuzzyFinder(tempfrom) . '/'
    else
        let found = from
    endif

    return found
endfunction

"
" GetTestDirForFF()
"
" Now overload GetDirFF() specifically for
" the test directory (I'm really only interested in going
" down into test/src 90% of the time, so let's hit that
" 90% and leave the other 10% to couple of extra keystrokes)
"
function! GetTestDirForFF(from)
    return GetDirForFF(a:from, 'test/src/')
endfunction

"
" GetIncludeDirForFF()
"
" Now specialize for the 'include'.  Note that we rip off any
" '/test/' in the current 'from'.  Why?  The /test/ directory
" contains an 'include' directory, which would match if we 
" were anywhere in the 'test' directory, and we don't want that.
"
function! GetIncludeDirForFF(from)
    let from = substitute(SanitizeDirForFuzzyFinder(a:from),
                        \  '/test/.*$', '', '')
    return GetDirForFF(from, 'include/')
endfunction

"
" GetSrcDirForFF()
"
" Much like the GetIncludeDirForFF() but for the 'src' directory.
"
function! GetSrcDirForFF(from)
    let from = substitute(SanitizeDirForFuzzyFinder(a:from),
                        \  '/test/.*$', '', '')
    return GetDirForFF(from, 'src/')
endfunction
```

Now, you can use these by making some mappings. I have the following three:

``` vim
nnoremap ,ff :FuzzyFinderFile<cr>
nnoremap ,ft :FuzzyFinderFile <c-r>=GetTestDirForFF('%:p:h')<cr><cr>
nnoremap ,fi :FuzzyFinderFile <c-r>=GetIncludeDirForFF('%:p:h')<cr><cr>
nnoremap ,fs :FuzzyFinderFile <c-r>=GetSrcDirForFF('%:p:h')<cr><cr>
```

Clearly this requires that you're already working on a file somewhere inside the `/root/of/code/tree/component_name` directory but that's my convention most of the time when working on a library so this works great for me. Cheers!
