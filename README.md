Chengyu Chen 03/02/2017 cs8bwane


Part 3:
1. mkdir -p fooDir/barDir
2. The wildcard character I would use is * when you use it with rm 
for example rm *xxx* ,this means to tell the rm command to delate all 
files in the current directory that have the string xxx in their name.
3. gvim -p *.java
4. static means that the method marked as such is available at the class level.
In other words, you don't need to create an instance of the class to access it.
For example in Foo class you have a static method written as
public static void doStuff(){
   //does stuff
}
}
So instead of creating an instance of Foo and then calling doStuff,
you can just call the method directly against the class like Foo.doStuff.
5. In order to improve her deisgn by using the object-oriented terminology I
   think she can use polymorphism and write subclasses to represent different 
   shapes and use the ShapeDrawer as the parent class. So all the subclasses
   about different shapes inherent the characteristics froom the parent class
   but they also have its own characteristics, as different shapes have its
   unique properties.


Description:
In ReverseRecurse.java the reader is allowed to assign the size of the array and
create the array with the size specifized by the user. Then user is allowed to
input the number it wants to be inside the array, however when a user enter a non 
integer number it will stops you from inputting the number. After that it will
reverse your array in two different ways.

In Gui2048.java 
This file is to create a fully functioning graphical 2048 game, so it can be
shown and playing in a window on the computer. You are able to move to any
directions you want and it is functioned just as the 2048 game you played in
your computer.

Testing
For ReverseRecurse.java in order to test my methods, one way is to use the
tester it provides to us, we can also create small tester or just print out the
value after each method we write, so it is easy for us to know which step I did
wrong. For Gui2048.java, one way I used to test my method is to visualize what
happened when I play the game, if the tiles can not move, I will know there are
something wrong with updating the Gui. I can also comment out some part of the
code to see exactly which part am I doing it wrong.
