# Contribute to the project

## Language

English will be used for function names, variable names, class names, and comments.

---

## Coding Standards

### General

- When defining the types of methods and attributes, try to use the highest type possible (while staying logical and practical) _e.g._

```java
// Bad
ArrayList<Integer> array = new ArrayList<>();
PlayerImplementation player = new Player(...);
MazeModelImplementation maze = new MazeModelImplementation(...);

// Good
List<Integer> array = new ArrayList<>();
Player player = new PlayerImplementation(...);
MazeModel maze = new MazeModelImplementation(...);
```

- Any magic number must be defined in a constant _e.g._

```java
// Bad
if (array.size() == 3) {
    // ...
}

// Good
private static final int ARRAY_SIZE = 3;

if (array.size() == ARRAY_SIZE) {
    // ...
}

```

### Functions

- Single Responsibility Principle (`SRP`): A function should have only one goal, otherwise decompose into at least two functions
- The length of a function should not exceed 30 lines
- No more than 3/4 arguments in a function
- A condition composed of more than 2 conditions should be decomposed into several functions _e.g._

```java
// Bad
if (condition1 && condition2 && condition3 && condition4) {
    // ...
}

// Good
if (isInputValid()) {
    // ...
}

private boolean isInputValid() {
    return condition1 && condition2 && condition3 && condition4
}
```

- Any unused functions (i.e., not used) should be removed. Dead code is not allowed in the project. It can always be found in the GitLab history.

### Classes

- Small and powerful classes are expected i.e. classes with few public methods but which can do a lot. Therefore, it is necessary to avoid classes with many setters and getters.
- Please respect the Model-View-Controller (`MVC`) pattern
- Functions should be placed as close as possible to their use and following the calling/called convention _e.g._

```java
// Bad
public void g (){
    f();
}

public void h(){
    // ...
}

public void f() {
    // ...
}

// Good
public void h(){
    // ...
}

public void g (){
    f();
}

public void f() {
    // ...
}

```

### Comments

- Comments should be used to explain why and not how _e.g._

```java
// Bad
private int time = 0; // Time

// Good
private int time = 0; // Time in seconds since the beginning of the game

```

- A `javadoc` is expected (except for private methods and trivial functions)

### Variable Names

- Sensible names are necessary for functions and variables _e.g._

  - `isOK` -> `isValidInput`
  - `name` -> `PlayerName`
  - `i` -> `column`
  - `j` -> `row`

  ...

### Tests

- Any code that is not tested will not be accepted
- The name of the test class must be `Test` followed by the name of the class being tested _e.g._ `PlayerImplementationTest`

- Tests are expected for the majority of functions (not `private`)
- It is expected that tests be independent of each other (no dependency between tests)
- One test per function and per test case is expected, _e.g._

```java

@Test
public void testGetAllWithEmptyList() {
    // ...
}

@Test
public void testGetAllWithNonEmptyList() {
    // ...
}
```

---

## GitLab Standards

### Branches

- Branch naming follows the convention: `Issue#-ShortIssueName` e.g. `1-CreateProject`
- No commits will be tolerated on `develop` and `master` branches
- A merge`develop` -> `master` is expected at a minimum of one sprint every two sprints
- The only branch that can be merged into Master is the `develop` branch

### Merge Requests

- Confirmation with a 👍 from at least two other members is expected before any `MR` is merged
- Verify that your code passes each test before submitting an `MR`

### Issues

#### Update your issues

We try to update our issues as often as possible. Regular updates allow us to observe a certain work pace and identify possible difficulties encountered.

#### Short issues

An issue created should at best correspond to a work limit associated with a half-sprint duration. If this is not the case, the issue has been poorly thought out and it is then preferable to revise its design or simply divide it into several smaller issues.

#### Precise labels

It should be possible to understand the usefulness and interest of an issue only through its title and the labels assigned to it.

#### Categories

##### Issue Status

- To Do
- On Hold
- WIP
- To Review

##### Issue Type

- Bug
- Advancement Status
- Implementation
- Sound
- Maze
- Research
