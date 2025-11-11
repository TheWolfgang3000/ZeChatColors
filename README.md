# ZeChatColors

Because your server chat clearly wasn't screaming for enough visual noise already.

## What is this?

A brutally simple plugin that lets your players set a default color for their chat messages. That's it. It doesn't do more. Don't expect more.

It was developed for one of those weird, old Beta servers (uberbukkit) because apparently, we're all still stuck in 2010.

## Features

* Lets players permanently *stain* their chat messages with one of 16 exciting colors.
* A command to show them what colors they can use, because who *actually* remembers what `&b` is.
* A command to wash off all that nonsense (`reset`) when they come to their senses.
* Actively prevents players from using formatting codes (`&l`, `&k`). Seriously, *nobody* wants to read magical, bold, rainbow text spam.

## Commands

There are two. Don't get overwhelmed.

### `/cc`
Shows the list of available color codes. Yes, it's just the standard colors. No, you're not getting hex codes.

### `/setcolor <&code>`
This sets your color.
*Example:* `/setcolor &a`. Congratulations, now everyone has to endure your toxic green text.

### `/setcolor reset`
For when you realize how terrible your choice was. Resets your color to boring, readable, default white. You'll do it again.

## Permissions

None.

Why? Because managing permissions is more work than writing this plugin. Everyone can use it. Live with it.

If you want permissions, fork the repo and add them yourself. Or don't. I don't really care.

## Installation

1.  Download the `.jar` file.
2.  Throw it in your `plugins` folder.
3.  Restart the server.
4.  If it doesn't work, you probably messed up step 1 or 2. Or 3.
