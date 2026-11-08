
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchVisitor implements Visitor {
    private final String filename;
    private final long minSize;
    private final List<String> results = new ArrayList<>();

    public SearchVisitor(String filename, long minSize) {
        this.filename = filename;
        this.minSize = minSize;
    }

    public List<String> search(FileSystemNode root) {
        results.clear();
        root.accept(this);

        Collections.sort(results);
        return new ArrayList<>(results);
    }

    @Override
    public void visit(FileNode file) {
        boolean nameMatches = file.getName().contains(filename);
        boolean sizeMatches = file.getSize() >= minSize;

        if (nameMatches && sizeMatches) {
            results.add(getPath(file));
        }
    }

    @Override
    public void visit(DirectoryNode directory) {
        for (FileSystemNode child : directory.getChildren()) {
            child.accept(this);
        }
    }

    private String getPath(FileSystemNode node) {
        List<String> parts = new ArrayList<>();
        FileSystemNode current = node;

        while (current.getParent() != null) {
            parts.add(current.getName());
            current = current.getParent();
        }

        Collections.reverse(parts);
        return String.join("/", parts);
    }
}
