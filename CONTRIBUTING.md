# Contributing guidelines

## Writing code
For our code style we'll be using the Google Java Style format.

Use the Checkstyle plugin for Android Studio with the [google_checks.xml](https://github.com/checkstyle/checkstyle/blob/master/src/main/resources/google_checks.xml) rules for the IDE to help you keep the code correctly formatted.

The Google coding style uses tab size of 2, instead of 4.
To change this in Android Studio, go to Settings -> Editor -> Code Style -> Java, and change "Tab size" and "Indent" to 2, and "Continuation indent" to 4.

For a full specification go to the official [Google Java Code](https://google.github.io/styleguide/javaguide.html) page.

## Commiting
For our commit messages, we'll be using the Chris Beams style.

A short summary of the rules are:

* Separate the title from the body with a blank line in the message
* Keep your messages short (about 50 characters)
* Start commit messages with a capital letter
* Don't punctuate commit title
* Use imperative mood in commit messages, eg. "Add coordinates to map"
* Wrap body at 72 characters
* Use body to explain "what" and "why", instead of "how"

For a full explanation visit the [site](http://chris.beams.io/posts/git-commit/).

## Merging with codebase
Before sending a pull request from your fork to the main repository, perform an [interactive rebase](https://www.atlassian.com/git/tutorials/rewriting-history/git-rebase-i) to clean up your commits into a bigger commit. For example, if your have commits "Create Player class", "Add walking functionality to Player", you can merge them to "Add Player". IMPORTANT: Only rebase commits that are locally on your machine. Rebasing things on your fork can cause conflicts.
