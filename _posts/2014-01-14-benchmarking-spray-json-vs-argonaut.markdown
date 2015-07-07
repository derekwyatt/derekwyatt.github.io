---
layout: post
title: Benchmarking Spray Json vs. Argonaut
header-img: img/compact-discs.jpg
abstract: Spray's Json parser vs. Argonaut's in a head-to-head battle to the death.
tags:
- scala
---
I've been coding up a JSON based REST service at work and it wasn't performing quite the way I wanted.  It's implemented using [Spray][1] on [Scala][3] and has a [Cassandra][4] database piece that uses the [Datastax][5] library.  When I profiled it (ever so simply) I noted that it was spending a lot of time in the [spray-json][6] library.  Normally I wouldn't really care, but in this case it was getting beaten out by a competing Java app running on [Geronimo][7] and, well... that just can't stand :)

So, I took a look to see what the new [Argonaut][2] library could do.  The API is very different, of course, and this post won't show you a damn thing about it; this is all about raw speed.

The question I wanted answered was, _"Will changing the JSON library increase performance?"_.  The answer is "yes".  But, let's see how much...

You can see the code in question [in my github repository][8].  It's using [spray-json][6] version 1.2.5 and [Argonaut][2] version 6.1-M2.

The JSON File
-------------

The file being parsed is 3.7k in size and looks something like this:

``` json
{
  "webapp": {
    "servlet": [   
      {
        "servletname": "cofaxCDS",
        "servletclass": "org.cofax.cds.CDSServlet",
        "initparam": {
          "configGlossaryinstallationAt": "Philadelphia, PA",
          "configGlossaryadminEmail": "ksm@pobox.com",
          "configGlossarypoweredBy": "Cofax",
          "configGlossarypoweredByIcon": "/images/cofax.gif",
          "configGlossarystaticPath": "/content/static",
...
...
    "servletmapping": {
      "cofaxCDS": "/",
      "cofaxEmail": "/cofaxutil/aemail/*",
      "cofaxAdmin": "/admin/*",
      "fileServlet": "/static/*",
      "cofaxTools": "/tools/*"
    },
    "taglib": {
      "tagliburi": "cofax.tld",
      "tagliblocation": "/WEB-INF/tlds/cofax.tld"
    }
  }
}
```

The Code
--------

Pretty dead simple.  All I'm doing is parsing the JSON from a string back to a string.

The [spray-json][6] version is:

``` scala
package com.codeseq

import spray.json._

object Spray {
  def parseToString(jsonStr: String): String =
    jsonStr.asJson.compactPrint
}
```

And the [Argonaut][2] version is:

``` scala
package com.codeseq

import argonaut._, Argonaut._

object Argonaut {
  def parseToString(jsonStr: String): String =
    Parse.parseOption(jsonStr).get.nospaces
}
```

I parsed the file 10 times for 1,000 iterations.  Here are the results:

<img src="/images/spray-vs-argonaut.png" class="unadorned"/>

So, the upshot is that, on average (ignoring the first run of each since they were just warming up):

- spray-json: 1234 ms
- Argonaut: 348 ms

A little over three times faster for [Argonaut][2].  I know it's a dead simple test, hence the ton of salt you need to take with it.  I have noted that marshalling to and from case classes is also faster, but I don't have any concrete numbers to support it.

The APIs are certainly different and I'm definitely more used to the [spray-json][6] API, which means I find the [Argonaut][2] API scary and annoying, but I'm getting there.

  [1]: http://spray.io "Spray"
  [2]: http://argonaut.io "Argonaut"
  [3]: http://scala-lang.org "Scala"
  [4]: http://cassandra.apache.org "Cassandra"
  [5]: http://datastax.com "Datastax"
  [6]: https://github.com/spray/spray-json "spray-json"
  [7]: http://geronimo.apache.org/ "Geronimo"
  [8]: https://github.com/derekwyatt/spray-json-vs-argonaut "spray-vs-argonaut"
