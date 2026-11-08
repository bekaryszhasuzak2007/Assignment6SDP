import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectoryNode extends FileSystemNode {
    private final List<FileSystemNode> children = new ArrayList<>();

    public DirectoryNode(String name) {
        super(name);
    }

    public void addChild(FileSystemNode child) {
        if (child == null) {
            throw new IllegalArgumentException("Child cannot be null");
        }

        if (child.getParent() != null) {
            throw new IllegalArgumentException("Element already has a parent");
        }

        for (FileSystemNode existing : children) {
            if (existing.getName().equals(child.getName())) {
                throw new IllegalArgumentException("Duplicate child name");
            }
        }

        DirectoryNode current = this;

        while (current != null) {
            if (current == child) {
                throw new IllegalArgumentException("Cycle detected");
            }
            current = current.getParent();
        }

        children.add(child);
        child.setParent(this);
    }

    public List<FileSystemNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}