# Simple_Clean_Architecture_Template

Clean architecture is a method of software development in which you should be able to identify what a program performs merely by looking at its source code. The programming language, hardware, and software libraries needed to achieve the program’s goal should be rendered obsolete. Like other software design philosophies, Clean architecture aims to provide a cost-effective process for developing quality code that performs better, is easier to alter, and has fewer dependencies. Robert C. Martin established clean architecture and promoted it on his blog, Uncle Bob, in 2011.

# Advantages of Using Clean Architecture
Your code is even more easily testable than with plain MVVM. Your code is further decoupled (the biggest advantage.) The package structure is even easier to navigate. Your team can add new features even more quickly.
    
# Layers of Clean Architecture
Clean architecture is also referred to as Onion architecture as it has different layers. As per our requirement, we need to define the layers, however, architecture doesn’t specify the number of layers.
1. Presentation: This is a layer that interacts with the (UI)user interface.
2. Domain: The app’s business logic is stored here.
3. Use cases: Interactors are another name for them.
4. Data: All data sources are defined in a broad sense.
5. Framework: Implements interface with the Android SDK as well as concrete data layer implementations.
