# Go tutorial

<https://go.dev/doc/tutorial/getting-started>

In the getting started tutorial, you learn how to setup
go and some fundamentals about go packages. The `go.mod`
file is first setup, and then tidied as dependencies
are added. It can help to redirect local packages
before each has been published. Each package can be
tested as well, then built and installed to the local
directory.

<https://go.dev/tour/list>

There are a variety of types that can be used and
inferred when declared and assigned. There are nice
short hands for naming multiple variable or parameters
of the same type or returning named variables from
short methods for readability. Flow control statements
are based on for loops and typically allow variable
declaration and assignment in an optional statement
for if and switch statements. Cases do not need to be
constants and can be calculated. This makes it a clean
way to write long if-else chains and have them organized
as cases in a switch statement. There is also support
for pointers, structs, arrays, and slices. Slices are
particularly interesting because they create views on
top of arrays and can be adjusted later or declared
and initialized with the underlying array too.

Next, I'll complete the sections on types, methods
and interfaces, generics, and concurrency. After that,
I'd like to go through a tutorial on Go and Gin, then
GoCD.
