---
layout: post
title: Benchmarking Spray Json vs. Argonaut vs. Play Json
header-img: img/underground.jpg
abstract: Spray's Json parser vs. Argonaut's vs. Play's in a head-to-head battle to the death.
tags:
- scala
---
[Yesterday I blogged about][1] a simple benchmark between [Spray][2]'s JSON parser and [Argonaut][3]'s JSON parser.  The results were pretty clear that [Argonaut][3]'s more efficient.  After I posted it, [@David Ross][6] reminded me that [Play][7] has a pretty substantial JSON library as well.  So, I added it to the mix.  It turns out, I should probably be using that one, if I want decent performance.  Of course, [Play's JSON library][9] is based off of [Jackson][8] at the core, which is the fastest JSON library for Java I've ever used, so its speed isn't that surprising.

The setup is the same as yesterday, and you can view the code [in the same Github repository][5].  The results are:

For [spray-json][4]:

<img src="/images/spray-json-parsing-times.png" class=unadorned />

For [Argonaut][3]:

<img src="/images/argonaut-parsing-times.png" class=unadorned />

For [Play Json][9]:

<img src="/images/play-json-parsing-times.png" class=unadorned />

The averages are (ignoring the first run of each):

- spray-json: 1,089 ms
- Argonaut: 324 ms
- Play Json: 179 ms

Meaning that:

- Play Json is 1.8 times faster than Argonaut, and 6.1 times faster than spray-json

Please collect your bag of salt on the way out.

  [1]: /2014/01/14/benchmarking-spray-json-vs-argonaut.html "Yesterday"
  [2]: http://spray.io "Spray"
  [3]: http://argonaut.io "Argonaut"
  [4]: https://github.com/spray/spray-json "spray-json"
  [5]: https://github.com/derekwyatt/spray-json-vs-argonaut "spray-vs-argonaut"
  [6]: https://twitter.com/dyross "@dyross"
  [7]: http://www.playframework.com/ "Play"
  [8]: http://jackson.codehaus.org/ "Jackson"
  [9]: http://www.playframework.com/documentation/2.2.x/ScalaJson "Play Json"
