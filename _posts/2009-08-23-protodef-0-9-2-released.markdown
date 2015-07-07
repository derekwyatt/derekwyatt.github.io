---
layout: post
title: Protodef 0.9.2 released
header-img: img/dubrovnik-croatia.jpg
tags:
- plugins
- vim
---
I made an enhancement to [Protodef](http://www.vim.org/scripts/script.php?script_id=2624) to allow for the ability to eliminate the namespace when pulling in the prototypes. So now you can make them look like this:

```cpp
one::two::SomeClass::functionA() { }
one::two::SomeClass::functionB() { }
```

... or this:

``` cpp
namespace one { namespace two {
  SomeClass::functionA() { }
  SomeClass::functionB() { }
} }
```

(Protodef doesn't put the namespace wrappers in for you - you would have done that by yourself. What it does do is allow you to skip putting in the `one::two::`.)
