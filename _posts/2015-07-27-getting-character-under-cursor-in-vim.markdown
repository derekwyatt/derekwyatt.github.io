---
layout: post
title: Getting the value of the character under the cursor
header-img: img/female-sitting-in-library.jpg
abstract: Getting the ascii / hex code of the character under the cursor in Vim is harder than I thought...
tags:
- vim
---
I've been playing with [lightline][1] for [Vim][2] and wanted to write a custom function for returning the decimal and hex value of the character under the cursor.  It turns out that this is non-trivial.

* The simplest way to get it is to just show it on the `statusline` (or equivalently in _lightline_) with `%b` and `%B`, as visible in the docs or in [this Vim Tips entry][3].
 * The problem is that I can't get the value into a Vim variable this way.
* Another way is to simply yank the damn thing with something like `"zyl`, which will put the value in the `@z` register, as described [here][4].
 * If `virtualedit` is set to anything, then this will return a `space` if there is no character under the cursor, for any mode in which `virtualedit` is active.
 * What's more is that I may very well not want to run a `normal` command like this, especially in `visual` mode.
* Another way is to do something like `let ch = getline('.')[col('.') - 1]`, which is also described [here][4].
 * This doesn't respect unicode characters because their length is "interesting".

So here's what I eventually tried:

``` vim
function! LightLineCharacter()
  " Save what's in the `z` register and clear it
  let x = @z
  let @z = ""

  " Redirect output to the `z` register
  redir @z

  " Run the `ascii` command to get all of the interesting character information
  silent! ascii
  redir END

  " Clean up the output and split the line
  let line = substitute(substitute(@z, '^.*> ', '', ''), ',', '', 'g')
  let list = split(line)

  " Reset the `z` register
  let @z = x

  " `dec` and `hex` hold the values I want
  let dec = 0
  let hex = 0

  " If we've split something reasonable, then get decimal and hex values
  if len(list) >= 4
    let dec = list[0]
    let hex = list[2]
  endif

  " Return it the way I want
  return dec . "/0x" . hex
endfunction
```

However, as I was researching the links for this post, [I found the "real" answer][5]:

``` vim
let dec = char2nr(matchstr(getline('.'), '\%' . col('.') . 'c.'))
```

You can find the explanation in one of the comments in the [StackOverflow post][5] but I'll also explain it here.

* `getline('.')` returns the entire line that cursor is sitting on.
* `col('.')` returns the column number that the cursor is sitting on.
* The regular expression `\%nc` matches a _specific given column_ where `n` is that column.
* The '`.`' regular expression matches _exactly one character_.

So, let's assume that the column the cursor is currently on is `31`. Then the above code evaluates to:

``` vim
let dec = char2nr(matchstr(getline('.'), '\%31c.'))
```

which means that you're going to match the single character (multi-byte or otherwise) at position `31` in the string returned by `getline('.')`. And `char2nr()` is going to transform the character to a decimal number.

It's really not always that easy to figure out what needs writing when you're in Vimscript!

  [1]: http://vimawesome.com/plugin/lightline-vim "Lightline"
  [2]: http://www.vim.org "Vim"
  [3]: http://vim.wikia.com/wiki/Showing_the_ASCII_value_of_the_current_character "%B"
  [4]: http://vim.1045645.n5.nabble.com/How-to-get-the-charactor-under-cursor-in-vimscript-td1181648.html "Vim mailing list entry"
  [5]: http://stackoverflow.com/questions/23323747/vim-vimscript-get-exact-character-under-the-cursor "How to get the value under the cursor"
