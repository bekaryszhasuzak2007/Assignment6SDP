
# Assignment 6 - Visitor Pattern

## Project Description

This project implements an advanced file system using the Visitor Design Pattern.

The file system contains files and directories. Directories can contain files and other directories.

Three visitors are implemented:

- **TotalSizeVisitor** - calculates the total size of all files.
- **SearchVisitor** - searches files by name and minimum size.
- **StatisticsVisitor** - counts files, directories, and maximum depth.

## Project Structure

- `Element` - common interface.
- `FileSystemNode` - base class for file system elements.
- `FileNode` - represents a file.
- `DirectoryNode` - represents a directory.
- `Visitor` - visitor interface.
- `TotalSizeVisitor` - calculates total size.
- `SearchVisitor` - searches for files.
- `StatisticsVisitor` - calculates file system statistics.
- `Main` - demonstrates the application.

## Traversal Design

Each visitor is responsible for traversing the directory tree.

Directories visit their children using the `accept()` method. This allows the Visitor Pattern to use double dispatch without `instanceof` or type checking.

## How to Run

Run the application:

```bash
mvn compile
```

Then run the `Main` class from IntelliJ IDEA.

Run all tests:

```bash
mvn test
```

## Testing

The project uses JUnit 5.

There are 10 tests in `FileSystemTest` covering:

- Double dispatch
- Total file size
- Search filters
- Sorted file paths
- File system statistics
- Empty directories
- No search results
- Repeated visitor execution
- Invalid tree modifications
- Child collection protection

All 10 tests pass successfully.

## Technologies

- Java 17
- Maven
- JUnit 5
- Visitor Design Pattern
