---
layout: post
title: Functional "map" in C++
---
**Note:** This is C++0x stuff and it's really just an investigation into some of the more obvious parts of it, including [the `auto` keyword](http://en.wikipedia.org/wiki/C%2B%2B0x#Type_inference) and [lambda functions](http://en.wikipedia.org/wiki/C%2B%2B0x#Lambda_functions_and_expressions) as well as working with [higher kinded types](http://en.wikipedia.org/wiki/Type_class) (signified in C++ as [template-template parameters](http://www.progdoc.de/papers/ttp/psi-ttp/psi-ttp.html)).

I've been doing a lot of Scala recently and have thus become quite spoiled by its awesomeness. It's made me look at C++ again with a different eye - a more *functional* eye. I've been doing C++ for so long, that I forget about that functional style of programming, and I also forget just how bad C++ is at functional programming - when you're a [multi-paradigm language](http://c2.com/cgi/wiki?MultiParadigmProgrammingLanguage), it's hard to be great at all the paradigms simultaneously.

So, I took a stab at writing a more "functional" `map` function. C++ provides the `transform` function template in the *functional* header file but I find it lacking because you have to pre-create the container that will be *mapped-to*. This is an attempt at creating that inside the `map` function.

First, we'll need some header files:

``` cpp
#include <iostream>
#include <vector>
#include <list>
#include <string>
#include <functional>
#include <algorithm>
#include <iterator>
#include <boost/lexical_cast.hpp>
```

And now we can define the function.

``` cpp
template <typename InType,
  template <typename U, typename alloc = allocator<U>>
            class InContainer,
  template <typename V, typename alloc = allocator<V>>
            class OutContainer = InContainer,
  typename OutType = InType>
OutContainer<OutType> mapf(const InContainer<InType>& input,
                           function<OutType(const InType&)> func)
{
  OutContainer<OutType> output;
  output.resize(input.size());
  transform(input.begin(), input.end(), output.begin(), func);
  return output;
}
```

The idea is that the output container may be a different type of container from the input container and the output type may also be of a different type from the input type.

A really simple usage of this could be:

``` cpp
vector<int> v1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
auto v2 = mapf(v1, function<int(const int&)>([](const int& i) { return i + 9; }));
```

The reason it's simple is that all it does is just take a `vector` of `int`s and map it to a `vector` of `int`s. That's nothing flashy.

It's more interesting if you do something like this:

``` cpp
auto v3 = mapf<float, vector, list>(
            mapf(
              mapf(v1, function<int(const int&)>([](const int& i) { return i + 9; })),
              function<float(const int&)>([](const int& i) {
                return i * 3.1415;
              })),
            function<string(const float&)>([](const float& i) {
              return string("\"" + lexical_cast<string>(i) + "\"");
            }));
```

This is the standard functional "chaining" we see with more fluid, immutable data structures. Futher, it is actually returning a `list<string>` instead of a `vector<string>`. In [Scala](http://typesafe.com) (you could do something similar in [Ruby](http://ruby-lang.org) or many other languages) this would be:

``` cpp
v1.map(_ + 9).map(_ * 3.1415).map(_.toString).toList
```

Now, I'm not exactly happy with what we've got here. It's not that I can't [pimp](http://www.artima.com/weblogs/viewpost.jsp?thread=179766) the `int` and `float` types and thus make it more fluid (although that would be nice). It's more that the template function specification is hideous. In order to actually use different output container types, you must specify too much in the template parameter list.

``` cpp
auto l1 = mapf<int, vector, list>(v1,
  function<int(const int&)>([](const int& i) { return i + 2; }));
copy(l1.begin(), l1.end(), ostream_iterator<int>(cout, "\n"));
```

Ideally we'd like to simply specify something like `mapf<list>(...)` instead. I need to work on this and see if I can come up with something nicer. Perhaps it's as simple as wrapping it up with another function or a template class... when I get more time.
