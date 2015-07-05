---
layout: post
title: The Most Useful Code I've Written in Weeks
header-img: img/orion-nebula-space-galaxy.png
abstract: SBT's automatic recompile when a file changes made me realize that I wanted that functionality with everything I do, and so I scripted it.
tags:
- shell
---
I'd like to say that I write *useful* code all the time, but let's face facts... useful code doesn't come around all that often. In my new job I'm using [Maven](http://maven.apache.org) instead of [SBT](http://www.scala-sbt.org) and I miss the cool [tilde](http://www.scala-sbt.org/#compilation) modifier in a big way. And now that I'm using the [Artima](http://www.artima.com) book building strategy - which is based on [Ant](http://ant.apache.org) - I miss it even more.

So I finally wrote this little script that's saving my ass.

``` bash
#!/bin/bash
 
command="$1"
shift
fileSpec="$@"
sentinel=/tmp/t.$$
 
touch -t197001010000 $sentinel
while :
do
  files=$(find . -newer $sentinel -a '(' $fileSpec ')')
  if [ $? != 0 ]; then
    exit 1;
  fi
  if [ "$files" != "" ]; then
    $command
    touch $sentinel
  fi
  sleep 0.1
done
```

I tend to use it in a number of different ways, but one of the simple ones is like this:

``` bash
execOnChange.sh "mvn test" -name \*.java
```

And that's all she wrote.
