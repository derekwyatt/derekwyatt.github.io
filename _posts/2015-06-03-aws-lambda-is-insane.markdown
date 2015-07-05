---
layout: post
title: AWS Lambda is Insane!
header-img: img/old-bridge.png
abstract: Ah, the life of an AWS DevOps employee is a pain in the ass... but Lambda just made it a whole lot more awesome.
tags:
- aws
---
_Over the last year, I've (rather reluctantly, I have to admit) become quite involved in the world of `DevOps`, most of which happens in the world of [AWS][9]. In 5 to 10 years, [AWS][9] is going to have taken over the entire internet... they damn well deserve to, and I think I'm looking forward to it._

_There's also [this post][10], which came before this one, which is from a guy I work with :)_

---

Bad things happen to our production environment, and when they do, we get alerts. We wanted these alerts to show up in [Slack][1] (another company that I'm really starting to love) because [Slack][1] has become our company's information hub, and if it doesn't have a [Slack][1] integration, it's just not on our radar.  How do we take a [Cloudwatch][2] alert and ship it off to our [Slack][1] notification channel?  One word... **[Lambda][3]**.

[Lambda][3] is a DevOps guru's _wet dream_, and what I'm about to show you is a stupid example of what it can do, but it's still *freakin' awesome*.  At the very least, I don't have to put up another damn server to handle a simple bit of work (and then monitor it, make sure it stays up, put in some redundancy and fail over, and ... _**blurgh!**_)

The Goal
--------

All I want to do (_all_ I want to do... jeez... think about it, man, this is heavy) is plumb alerts that are being triggered in [Cloudwatch][2], that are being sent to [SNS][4], to our `#production` channel in [Slack][1].

We've already got the metrics being uploaded to [Cloudwatch][1], are being alerted and sent to [SNS][4], and [SNS][4] can already send those to various email addresses, but unfortunately there's no automatic support from [SNS][4] to [Slack][1].  We need to add this last bit of plumbing.

<img width=400px src="/images/Product_to_Slack.png"/>

The How
-------

We create a [Lambda][3] function called `sns2slack` (more on this in a minute) and then plumb that to SNS using the SNS subscription configuration.  It looks like this:

<img width=800px src="/images/SNS_Lambda_config.png"/>

I'm only showing a couple of things that we've got subscribed to the function, but we could easily (and do) subscribe tons of different notifications to the same [Lambda][3] function.  The function itself can be as generalized, or as specifc as you'd like and can be capable of handling notifications from tons of different areas because the events are self-describing JSON objects.

You configure the function from the [Lambda][3] configuration screen, and you can modify the subscriptions here as well.  However, you don't really interact with this much because the best way to interact with it (and with [AWS][9], in general) is through the [AWS CLI][5].

<img width=800px src="/images/Lambda_configuration.png"/>

The main supported language for [Lambda][3] executables is _Javascript_ (_an opportunistic little language that, were it food, would be vomited up by even the most destitute dog on the streets of Calcutta_) which are hosted by [NodeJS][6]. But, let's face it, it's sure as hell a boat load better than nothing.

Since things are running under [Node][6], we can wield the power of [NPM][7], and install [node-slackr][8].

``` bash
npm install node-slackr
```

And then we can hook the script up to the notifications that are going to come from [SNS][4].

``` javascript
// Grab our slack module
var Slack = require('node-slackr');
// Instantiate it
var slack = new Slack('https://hooks.slack.com/services/your hook here',
                      { username: "The Username",
                        icon_emoji: ":choose something good:"});

// This is the handler that Lambda needs in order to
// process the notification.  It's pretty simple: an
// event comes in - specifically in this case we know
// it's going to be from SNS - and a context that you
// can use to signal Lamba itself.  exports.handler =
function(event, context) {
  // We know that the notification object is a
  // singleton event in the event records
  var obj = event.Records[0];

  // Parse the event using our own function that
  // will inspect and dissect the SNS notification
  // and translate that into another object that is
  // specifically designed for the Slack API.
  // e.g. Something like:
  //     {
  //       text: "*Unknown Event Occurred*",
  //       channel: "#ops",
  //       attachments: [
  //         {
  //           color: "danger",
  //           fields: [
  //             {
  //               title: "JSON object",
  //               value: text
  //             }
  //           ]
  //         }
  //       ]
  //     };
  var slackObj = process_the_event(obj)

  slack.notify(slackObj, function(err, result) {
    console.log(err);
    console.log(result);

    // It's important to signal the completion of the
    // code inside the slack notification callback.
    // If you do it outside, then it's probably going
    // to signal completion before slack is actually
    // notified
    context.done(null, "SUCCESS");
  });
};
```

So now we've got a directory that looks like this:

``` text
slack.js
node_modules/
```

In order to upload this information to [AWS][9], you've gotta zip it up and then process that zip file through the [AWS CLI][5].

``` bash
zip -r * slack_notifier.zip && \
aws lambda update-function-code \
    --function-name sns2slack \
    --zip-file fileb://slack_notifier.zip
```

Those two commands will zip up the files and then upload the code to [AWS][9], replacing the existing implementation with your newly minted (and totally bug free) version.

That's it!

Amazon will run that code for you, on "some piece of hardware somewhere" that you don't need to care about.  They're going to charge you a pittance to do it and you don't have to worry about anything. [Lambda][3] can react to a ton of different events from within the [AWS][9] cloud infrastructure and, considering that the services within [AWS][9] are Web Services, we can easily manipulate them as well right from within [Lambda][3] itself.

[Lambda][3] is one impressive little monkey.


  [1]: http://slack.com "Slack"
  [2]: https://aws.amazon.com/cloudwatch/ "Cloudwatch"
  [3]: https://aws.amazon.com/lambda/ "Lambda"
  [4]: https://aws.amazon.com/sns/ "SNS"
  [5]: https://aws.amazon.com/cli/ "AWS CLI"
  [6]: http://nodejs.org/ "NodeJS"
  [7]: http://https://www.npmjs.com/ "NPM"
  [8]: https://github.com/chenka/node-slackr "node-slackr"
  [9]: https://aws.amazon.com/ "AWS"
 [10]: http://www.digitaljedi.ca/2015/05/06/lambda-sns-cloudwatch-alarms-slack-integration/ "lambda sns cloudwatch alarms slack integration"

