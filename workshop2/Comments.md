### Class Diagram
The dotted association means that the class makes use of an instance of the other. Some of these are not correct in your system as the class contains a member of that type which should be a solid line instead.


### Code
*Returning internal data structures (such as the boat list in Member) is generally bad practice and breaks the Expert pattern. The functions that use these should instead call functions on Member that handle this in a better way or return immutable types that cannot be otherwise edited.*
I now explicitly copy a member for use outside the Registry. This will enable using the List type outside of registry, but it will not change the base collection. I need a List<> to make searching easier; Iterators are just meh… they iterate too often.

*Your ID generation function does not guarantee unique IDs.*
Yes, it does. A person always has a unique personal number and I check for already existing personal numbers upon creating a new Member instance. Also, the last 4 digits of the ID are based on a person's full name. If a new member is added with the exact same name and personal number, it will be the same ID, yes, but using a hashcode for this would have yielded the same result. A different name at the same personal number (which is not possible because I check for existing personal numbers) gives a 0.0125% chance to get the same last 4 digits. A boat yacht club doesn’t contain that many members to justify a very unique member ID generator.
Only thing I improved was checking the personal number upon updating a member’s personal number.

*You should not use BoatType.toString() to obtain a string to print to the view - this string should be generated in the view by a view class, not by a model class.*
Fixed, String representations of enumeration BoatType are now handled in View.

*The Registry's removeBoat is encroaching on the Member's domain - rather than directly modifying the member's boat list structure (bad) it should ask the Member class to do this itself.*
Moved boat specifics to Member instead of registry. It is indeed more logical this way.

*The circular dependency between the Controller and View is not great - ideally only one should depend on the other.*
Fixed, View doesn’t depend on Controller anymore.
