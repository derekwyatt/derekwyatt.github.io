---
layout: post
title: Moving from WordPress to Github Pages
---
When I started writing a blog in 2008 (or thereabouts), I hooked up with [Dreamhost][1] and started using [WordPress][2] because it was the best thing going.  Integrating code snippets was a serious pain, the interface was a drag, the spam protection was decent but not nearly perfect, and the whole damn thing was just _slow_.  I had been meaning to switch to [Github Pages][3] for quite a while and finally had the chance over the holidays.  Here's how it was done.

I do all of my blog work on a Mac, so these instructions are somewhat specific to Macs, but I don't think it would be much different on other platforms as there's not all that much that's really Macish about this.

Getting the Data out of WordPress
---------------------------------

[Dreamhost][1] provides a shell account via [SSH][4] so it was pretty comfortable to be able to tar up my website and dump the database:

```
dreamhost> tar zcf website.tgz derekwyatt.org
dreamhost> mysqldump -h {DB host} -u {DB username} -p {DB password} {DB name} > db.dump
dreamhost> gzip db.dump
```

Then it's just a question of pulling the files back home:

```
home> scp derekwyatt.org:~/website.tgz .
home> scp derekwyatt.org:~/db.dump.gz .
```

You also need to export the [WordPress][2] data using [WordPress][2] itself, which will generate an XML file for you.  Unfortunately, I don't have a screenshot of this, since it's now offline for me.  Fortunately, there's help all over the place, and [this page should make it pretty clear][14] what needs doing.  You'll end up with an XML file at the end of the day.

Installing Jekyll
-----------------

[Jekyll][5] is a blog-aware static website generator.  This provides me a much better workflow than I had with [WordPress][2] since everything can be done from the command line and [Vim][7] with the help of [Git][8].

As for installation, there's nothing really special here.  I just followed the instructions that were [provided by pages][6] and it was all pretty easy.  The only thing I probably didn't need was [Bundler][9]; I'm sure it's helpful when things become more complex but this was so simple, it wasn't a big deal.  I didn use it to install [Jekyll][5] on my second Mac and I had no issue.

Migrating the WordPress Data
----------------------------

[Jekyll][5] has a nifty [Ruby][10] gem [useful for importing data][11] from other blog applications, [WordPress][2] being one of them.  But...

Unfortunately, the importer expects to be able to talk to your [MySQL][12] database server in order to export the blog entries.  The quickest way for me to get this done was to install the [MySQL Community Server][13], load the data into it and then let the importer do it's thing.

```
home> 'create database {DB name};' | mysql -u root
home> zcat db.dump.gz | mysql -u root {DB name}
home> jekyll import --source {WP XML File} --dbname {DB name} --user root \
                    --prefix {prefix}
```

The `prefix` is a bit special.  Normally the tables in [WordPress][2] look like `wp_posts` and so forth, but when WP is hosted, things tend to be a bit more dynamic.  In my case, the table names all looked like `wp_ds2o2_posts`, so the prefix was specified as `wp_ds2o2_`.

The effect of the import was to have a bunch of files created in the `_posts` directory, all formatted as HTML, with [Jekyll][5] header data properly added.

So far so good!

Transforming the Migrated Data
------------------------------

The migrated data had a bunch of "stuff" in it that I didn't want right away (comments, categories, tags, etc...) and it was also in HTML, which I wanted transformed back into [Markdown][15].

Handling the YAML
-----------------

The front matter containing the extraneous [YAML][16] looked like this:

``` yaml
01 ---
02 layout: post
03 status: publish
04 published: true
05 title: MacVim Snapshot 49 Released
06 author: Derek Wyatt
07 author_login: admin
08 author_email: derek@derekwyatt.org
09 author_url: http://derekwyatt.org
10 wordpress_id: 67
11 wordpress_url: http://www.derekwyatt.org/?p=67
12 date: 2009-08-22 06:57:36.000000000 -04:00
13 categories:
14 - Vim
15 tags:
16 - Vim
17 comments: []
18 ---
19 {post content}
```

All I wanted from this was the `title:` and `layout:`, which is easily done in [Vim][7] with the following keystrokes:

``` vim
ggjV/---<cr>k:v/layout:\|title:/d<cr>
```

If you load up all of the files and record a macro, then you're good to go.  I do this with a [plugin][18] I have for [ZSH][17] and [Vim][7] itself:

```
# load in all of the blog files using the ZSH plugin
# It sends a command to my running Vim session that
# tells it to perform the load
home> v *
```

Then we record and execute the macro:

``` vim
" record the macro
qzggjV/---<cr>k:v/layout:\|title:/d<cr>:wn<cr>q

" run the macro across the rest of the files
72@z
```

Transforming the HTML into Markdown
-----------------------------------

OK, this part was a bit more complicated, but it was made a _ton_ easier by employing [Pandoc][19].  [Pandoc][19] will transform lots of different types of marked-up input to different types of marked-up output, and it will happily do HTML to [Markdown][15].  Once I had it installed, it was back to [Vim][7] to get it integrated.

Everything below the second `---` was the post content, which was what needed converting.  So, all that needed doing was the following:

``` vim
ggj/---<cr>jVG:!pandoc -fhtml -tmarkdown_github<cr>
```

And again we would record the macro to do it across the 72 remaning files and you're done!  Well, sorta... I had some extra clean up to do, but it was pretty specific to my blog and wouldn't necessarily translate for anyone else.

The Day-to-Day Workflow
-----------------------

This is probably the sweetest part of the whole thing.  When I want to write something up, here's what I do:

- Launch [Jekyll][5] to serve up my blog locally: `jekyll serve --watch`
  - It will watch for changes and re-render the site as required.
- Thanks to [this post][20] I found it was easy to get [Chrome][21] to reload the current tab on the Mac.
- Load up [Vim][7] and start writing an entry in [Markdown][15] format.

So, I have this script that watches the filesystem for changes to HTML files:

``` bash
#!/bin/bash

sentinel=/tmp/t.$$

touch -t197001010000 $sentinel
while :
do
  files=$(find _site -newer $sentinel -a -name \*.html)
  if [ "$files" != "" ]; then
    touch $sentinel
    echo "realoading active tab..."
    osascript bin/reload_chrome_active_tab.applescript
  fi
  sleep 0.1
done
```

And then this script that runs [Jekyll][5] and the reloader:

``` bash
#!/bin/bash

jekyll serve --watch &
jekyllPID=$!
bin/fswatcher &
fswatcherPID=$!

trap "kill $jekyllPID $fswatcherPID" INT EXIT

wait
```

Then it's just edit / save, edit / save until you're done and then push it up to [pages][3].

And I'm done.  See ya.

  [1]: http://dreamhost.com "Dreamhost"
  [2]: http://wordpress.com "WordPress"
  [3]: http://pages.github.com "Github Pages"
  [4]: http://openssh.com/ "SSH"
  [5]: http://jekyllrb.com "Jekyll"
  [6]: https://help.github.com/articles/using-jekyll-with-pages "Using Jekyll with Pages"
  [7]: http://www.vim.org "Vim"
  [8]: http://git-scm.com "Git"
  [9]: http://bundler.io/ "Bundler"
  [10]: http://ruby-lang.org/ "Ruby"
  [11]: http://import.jekyllrb.com/ "Jekyll Importer"
  [12]: http://mysql.com "MySQL"
  [13]: http://dev.mysql.com/downloads/mysql/ "MySQL Community Server"
  [14]: http://codex.wordpress.org/Tools_Export_Screen "WordPress export"
  [15]: http://daringfireball.net/projects/markdown/ "Markdown"
  [16]: http://yaml.org/ "YAML"
  [17]: http://www.zsh.org/ "ZSH"
  [18]: https://github.com/derekwyatt/dotfiles/tree/master/zsh_custom/plugins/vim-interaction "ZSH Vim Plugin"
  [19]: http://johnmacfarlane.net/pandoc/ "Pandoc"
  [20]: http://blog.daanraman.com/coding/automatically-reload-chrome-when-editing-files-on-osx/ "telling Chrome to reload"
  [21]: http://chrome.google.com "Chrome"
