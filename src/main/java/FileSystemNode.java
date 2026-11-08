public abstract class FileSystemNode implements Element {
    private final String name;
    private DirectoryNode parent;

    public FileSystemNode(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public DirectoryNode getParent() {
        return parent;
    }

    void setParent(DirectoryNode parent) {
        this.parent = parent;
    }
}