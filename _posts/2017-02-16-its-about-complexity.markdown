---
layout: post
title: The Architectural View of the "The Cloud"
header-img: img/old-bridge.jpg
abstract: There are lots of ways to look at the cloud, but the key architectural view is that of managing complexity.
tags:
- cloud
- microservices
---
_This isn't a rant; it's an architectural opinion piece wearing a special hat that it stole from another rant it met while at a party. Honestly, it's really supposed to be light hearted, so when you read it, smile. :D_

## On Buzzwords

The field of software design and development is full of them. Literally. It's full. So y'all can stop it any time you're ready. It's not that they get overused by everyone that wants to be on the "cutting edge" of design or architecture; it's that the words themselves quickly lose all meaning in very short order. The concepts that these buzzwords embody tend to be complex, hard won discoveries after years of pain and heartache. I don't think people really set out to create a buzzword - they're trying to give voice to one of those hard won, difficult concepts - but unfortunately, they tend to boil down to things like these:

* **Microservices**: A service. A process, or program with a REST interface. Just not big. You know, like the services we had 20 years ago, but smaller. More "single purpose". Kinda the same as before, but different in size.  Not so much of that largeness.
* **The Cloud**: A bunch of computers, not here, that run programs we can use more conveniently. A place where you can create virtual machines easily. You know, to run your stuff.

In other words, people use the terms with an understanding gained not from investigating their true meaning and definition, but guessing about that meaning based on some previous understanding (e.g. SOA) and the dictionary definition of the word's parts. I have literally heard someone say the word _"microservice"_ and then describe the thing they want to build as the complete opposite of what a microservice wants to be. However, nobody seems to call it out because the service being described is "small". It has no autonomy, it doesn't represent a business capability whatsoever, it's tightly coupled to a ton of different things, but it's "small", so it seems to pass the test of a _"microservice"_. Personally, I blame [Twitter][1]. If you can't say it in 140 characters, does it need to be said?

In fact, these two particular buzzwords are deeply intertwined. [**Microservices**][2] were not really practical until the [**The Cloud**][3] not only came into being, but matured to the level that we find today. Indeed the architectural paradigm of the [**Microservice**][5] was not fully conceptualized until the mature [**Cloud**][4] enabled that mode of thinking. In turn, [**The Cloud**][6] owes its importance and its immense utility to the existence and requirements of [**Microservices**][7].

The above buzzword definitions are really what a lot of people understand these two fundamentally transformative architectural concepts to be, and I think that's the point. These two concepts are _architectural concepts_ but they live in the space of marketing and have made their way into common speech. We can't expect marketing professionals and targets of marketing professionals to understand or care about the architectural principles behind them.

Rather paradoxically I am going to try to illuminate one of the most important aspects of the interplay between these two things, thus simplifying it for easy consumption.  I do this to create a wedge that can be used when discussing cloud design and architecture in the hope of cleaning up the vast amount of confusion that cloud architecture can create.

## On Complexity...

What we do is complex. The software we build is complex. Anything non-trivial that we do is complex. It's complex to build, complex to operate, monitor, test, to explain, and to learn.  Given a complex solution to a problem and a simple solution to a problem, assuming both solve the problem equally well, the simple solution should always win out. But even that simple solution is probably complex in its own right.

> I have made this longer than usual because I have not had time to make it shorter. - Blaise Pascal (ish)

We abhor complexity in all things technical; the simple solution is the elegant solution. In software, the complex solution is the solution of spiky death. It's expensive, difficult to work with, demoralizing, embarrasing, and can cost customers money.

But if what we do is complex, regardless of the simplicity of the solution, what are we to do?

## The Cloud Gives us a _"Slider of Complexity"_

The modern cloud has provided us with one of the greatest architectural tools we've ever had; **we can choose to move certain amounts of complexity _away_ from our code and into the infrastructure**.

Let's imagine we have a secure service for storing passwords that are used to contact other critical services. The service is state of the art and has already worked out the 'first mover' problem of authenticating the clients such that they are allowed to access the passwords they require. The mere existence of this service introduces new complexities.

First let's look at the "simple" situation we started with:

<img src="/images/SimpleGetPasswords.png" />

That's almost as simple as it gets. You need a password, then go to local storage (which can be a file, a database, a memory mapped filesystem, environment variables, etc...) and get it.

With the new secure password service, complexity has been increased:

<img src="/images/ComplexGetPasswords.png" />

Why is this more complex? A number of reasons:

1. We have a new protocol introduced.
  - All services must now understand how to speak to the credentials service.
  - Services are now susceptible to the changes in the credentials service.
  - The services themselves must have a secured and identified communication channel with the credentials service.
2. The credentials service is now a known entity to the dependent services.
  - It must either be run during testing, mocked or stubbed out.
  - It also consumes memory for developers, might have problems starting from time to time, must be configured, etc...
3. It is an incredibly important service that can be called at any time.
  - It must, therefore, but redundantly deployed and highly available.

In short, a very tight coupling has been created between the credentials service and the dependent services. We can certainly argue that the credentials service is cohesively designed and the cohesion of the other services is not affected, but the coupling introduces a significant level of complexity.

## Sliding the Complexity Away from Our Software

So how does the cloud assist?  We know that, at a minimum, the cloud offers us the ability to create and destroy virtual machines, or even containers, with incredible ease.  We also know that the cloud allows us the ability tailor make these machines in any way we see fit.

It's also easy to recognize that there are secure ways of presenting information to applications without the need to resort to certificates and cross-network communication _directly from within the application_.  All of these things combined allow us to **_move this particular complexity away from the application_**.

<img src="/images/ComplexGetPasswordsShifted.png" />

We can see that we've shifted the complexity further into the cloud, _which is designed to handle that complexity_.  But why is this better and how has the complexity been moved?  In short, this:

<img src="/images/DevelopmentAndOpsSeparation.png" />

1. The services are no longer coupled to the credentials service.
  - Developers don't need to run it, which means they don't need to spend the ram, don't need to debug any startup problems, and so forth.
  - When the credentials service's API changes, the other services won't notice or care.
  - Developers are back to using standard operating system facilities to retrieve simple bits of data.
2. We can define the credentials service's importance a fair bit more flexibly now.
  - Yes, it's very important, no question.
  - But because it's not dependend upon by _N_ services directly, the fallout from such an event need not be catastrophic.
3. The human knowledge and responsibility are now further compartmentalized and can now be largely maintained by "the cloud", also known as _DevOps_.
  - Ideally the securing of information and access to that information should be the sole responsibility of DevOps and the software should be designed to maximize that autonomy.
4. We no longer have _N_ distinct pieces of code that need to be maintained with that knowledge spread across _N_ teams.
  - The need to get information from the credentials service has not changed.
  - The credentials service itself has not changed.
  - What has changed is that only _one team_ is responsible for interacting with the credentials service, no matter how many services use the information stored within it.
  - As we add services, the complexity of this solution does not change.

  [1]: http://twitter.com/derekwyatt "Twitter"
  [2]: http://shop.oreilly.com/product/0636920033158.do "Building Microservices, by Sam Newman"
  [3]: https://www.infoq.com/cloud-computing/ "InfoQ Cloud Computing"
  [4]: http://nvlpubs.nist.gov/nistpubs/Legacy/SP/nistspecialpublication800-145.pdf "The definition of the cloud from NIST. Seriously. NIST took the time to do this. Way to go NIST!"
  [5]: http://microservices.io/patterns/microservices.html "Yet another definition of the Microservice"
  [6]: http://www.forbes.com/sites/joemckendrick/2012/01/25/how-cloud-redefines-our-most-important-business-relationships/#556380742c65 "Forbes is talking about the cloud too... Forbes. WTF?"
  [7]: http://blog.smartbear.com/microservices/why-you-cant-talk-about-microservices-without-mentioning-netflix/ "It's true. If you mention 'Microservices', you're probably going to have to say 'Netflix' before your next breath."
