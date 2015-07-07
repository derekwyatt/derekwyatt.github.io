---
layout: post
title: Formatting Code by Block
header-img: img/compact-discs.jpg
abstract: How we can use Vim to nicely indent C++ code using the 'block' text object
tags:
- c++
- vim
---
Ever been inside a code block and want to reformat (via ==) a number of lines but not the whole file and not line by line?

Let's say you've got code like this:

``` cpp
class MyClass
{
public:
    MyClass()
    {
std::cout << "In MyClass constructor" << std::endl;
}

    int somefunc()
    {
for (;;)
    {
std::cout << "breaking" << std::endl;
    }
    }
};
```

That's just not ideally formatted, is it?  Here's one cool way to format it:

- Put the cursor somewhere inside the outer most block - say on `public:` or on the blank line between the constructor and the function.
- hit: `=aB`

That will format "a block" and that means it formats the outer block recursively down to the inner most blocks.  Not that this is a great demonstration, but it looks like this:

``` cpp
class MyClass
{
  public:
    MyClass()
    {
        std::cout << "In MyClass constructor" << std::endl;
    }

    int somefunc()
    {
        for (;;)
        {
            std::cout << "breaking" << std::endl;
        }
    }
}; 
```
