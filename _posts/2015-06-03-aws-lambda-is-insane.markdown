---
layout: post
title: AWS Lambda is Insane!
tags:
- aws
---
Over the last year, I've (rather reluctantly, I have to admit) become quite involved in the world of DevOps, most of which happens in the world of AWS. In 5 to 10 years, AWS is going to have taken over the entire internet... they damn well deserve to, and I think I'm looking forward to it :)

So, we've gotta set up some alerts from our product to [Slack][1] (another company that I'm really starting to love) because [Slack][1] has become our company's information hub, and if it doesn't have a [Slack][1] integration, it's just not on our radar.  How do we take a [Cloudwatch][2] alert and ship it off to our [Slack][1] notification channel?  One word... **[Lambda][3]**.

[Lambda][3] is a DevOps guru's *wet dream*, and what I'm about to show you is a stupid example of what it can do, but it's still *freakin' awesome*.  At the very least, I don't have to put up another damn server to handle a simple bit of work.

The Goal
--------

All I want to do (_all_ I want to do... jeez... think about it, man, this is heavy) is plumb alerts that are being triggered in [Cloudwatch][2], that are being sent to [SNS][4], to our `#production` channel in [Slack][1].  So we've already got the metrics being uploaded to [Cloudwatch][1], are being alerted and sent to [SNS][4], and [SNS][4] can already send those to various email addresses, but unfortunately there's no automatic support from [SNS][4] to [Slack][1].  We need to add this last bit fo plumbing.

  [1]: http://slack.com "Slack"
  [2]: https://aws.amazon.com/cloudwatch/ "Cloudwatch"
  [3]: https://aws.amazon.com/lambda/ "Lambda"
  [4]: https://aws.amazon.com/sns/ "SNS"
