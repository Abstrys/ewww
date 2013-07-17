# About the Ewww Web Server

Ewww is Eron's Weird World Web (WWW) server.

The Ewww Web Server is written in Java and was designed to be a very simple web-server for demonstration and learning
purposes.

It was originally written in 2005, on a lark and in less than an hour, by Eron Hennessey. The name Ewww might be an
acronym meaning "Eron's Weird World Web-server", or it just *might* have been an expression of how disgustingly simple
this code is. ;)

An Apache Ant script for building the code was added later, as was this README.

Ewww is free software, and is released exclusively under the terms of the GNU General Public License.  A copy of the
license is provided with this software in the file `LICENSE`.

## How to Build

To build the code, you must first have a Java Development kit, either downloaded from the Java website or obtained as
part of your OS distribution.

If you have Ant installed, you can build the project by navigating to the source directory (which should be the same
place this file is found), and typing:

    ant

at the command-prompt.  The built `.jar` will be found in the `dist` directory.

Alternately, you may build *without* ant by typing:

    javac Ewww.java HTTPRequest.java

This second method will simply create .class files in the source directory. You can run these files as-is, or combine
them into a `.jar` by using the JDK's **jar** command.  I won't go into the details of this here -- I'm certain you're
clever enough to figure it out if you're inspired.

## How to Run

To run the Ewww Webserver, simply type:

    java -jar Ewww.jar

at the command-prompt, assuming you built via ant (or made a jar file yourself) and a directory called `htdocs` exists
within the current directory.

A complete list of run options is available by typing:

    java -jar Ewww.jar -?

Have fun!

