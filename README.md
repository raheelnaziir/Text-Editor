Java Text Editor

A simple Notepad-style text editor built in Java Swing.
This project replicates basic Notepad functionality with some added features for editing, formatting, and searching text.

Features
Core Editor

Line wrapping and word wrapping

Scrollable text area

Temporary highlighting for search results

File Operations

New File

Open .txt files

Save As (enforces .txt extension)

Overwrite confirmation

Formatting

Font picker (system fonts)

Font size selector

Change text color

Editing Tools

Undo and Redo

Cut, Copy, Paste

Select All

Delete All

Find and highlight text

Technologies Used

Java

Java Swing

Java AWT

JFileChooser, UndoManager, Highlighter API

Java NIO for file saving

How to Run

Clone the repository:

git clone https://github.com/<your-username>/<repo-name>.git


Compile and run:

javac EditorFrame.java
java EditorFrame


Or open the project in a Java IDE (Eclipse, IntelliJ, NetBeans).

Project Structure
/src
 └── EditorFrame.java

Contributing

Contributions are welcome.
You can suggest features or report issues by opening a GitHub issue.
