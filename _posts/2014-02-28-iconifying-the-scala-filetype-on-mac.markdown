---
layout: post
title: Iconifying the Scala Filetype on Mac
header-img: img/female-sitting-in-library.png
abstract: Want to add some Scala icon bling to everything on your Mac? Here's how you do it.
tags:
- scala
- vim
- mac
---
I couldn't sleep last night, and for some reason I pictured the Mac Finder, showing me those generic icons for files that the mac simply doesn't know about.  You know what I mean:

<img class="unadorned" src="/images/generic_file_type.png" />

I wanted things to look sexier, so I read [this post on SuperUser.com][1] and modified it a bit to fit better with [MacVim][2].  Here's what I did:

First, you've gotta get yourself an `ICNS` file.  I grabbed the `PNG` from the main [Scala][3] website and used [this web app][4] to convert it to an `ICNS` file.  To save you the trouble [just grab it here][5].

Next, you've gotta modify MacVim's [Info.plist][6] file (located at `/Applications/MacVim.app/Contents/Info.plist`) by adding this:

``` xml
<dict>
	<key>CFBundleTypeExtensions</key>
	<array>
		<string>scala</string>
	</array>
	<key>CFBundleTypeIconFile</key>
	<string>MacVim-scala</string>
	<key>CFBundleTypeName</key>
	<string>Scala File</string>
	<key>CFBundleTypeRole</key>
	<string>Editor</string>
	<key>LSIsAppleDefaultForType</key>
	<true/>
</dict>
```

You stick this somewhere inside here:

``` xml
<dict>
	<key>CFBundleDocumentTypes</key>
	<array>
  ...
```

There are a _ton_ of these in there, so you shouldn't have a tough time finding it.

Next, copy the [ICNS file][5] file into `/Applications/MacVim.app/Contents/Resources`.

Log out and log back in (or whatever better hack you know how to do in order to get the OS to see the change), and you should start seeing things like this:

<img src="/images/Scala_icons_in_folder.png"/>

and this:

<img src="/images/Scala_in_MacVim_Bar.png" class="unadorned" />

Yeah, it's a bit excessive, but hey... I really couldn't sleep.

  [1]: http://superuser.com/questions/178316/how-to-set-an-icon-for-a-file-type-on-mac "Basic icon HowTO"
  [2]: https://code.google.com/p/macvim/ "MacVim"
  [3]: http://scala-lang.org/ "Scala"
  [4]: http://iconverticons.com/online/ "ICNS Converter"
  [5]: /images/MacVim-scala.icns "The Scala ICNS file"
  [6]: https://developer.apple.com/library/ios/documentation/general/Reference/InfoPlistKeyReference/Articles/AboutInformationPropertyListFiles.html "Property list file information"
