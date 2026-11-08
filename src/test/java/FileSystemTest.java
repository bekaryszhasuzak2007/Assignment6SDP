
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileSystemTest {

    @Test
    void testDoubleDispatch() {
        int[] counts = new int[2];

        Visitor visitor = new Visitor() {
            @Override
            public void visit(FileNode file) {
                counts[0]++;
            }

            @Override
            public void visit(DirectoryNode directory) {
                counts[1]++;
            }
        };

        new FileNode("test.txt", 100).accept(visitor);
        new DirectoryNode("Root").accept(visitor);

        assertEquals(1, counts[0]);
        assertEquals(1, counts[1]);
    }

    @Test
    void testTotalSize() {
        DirectoryNode root = new DirectoryNode("Root");
        DirectoryNode folder = new DirectoryNode("Folder");

        root.addChild(folder);
        folder.addChild(new FileNode("a.txt", 100));
        folder.addChild(new FileNode("b.txt", 200));

        assertEquals(300, new TotalSizeVisitor().calculate(root));
    }

    @Test
    void testSearchFilters() {
        DirectoryNode root = new DirectoryNode("Root");

        root.addChild(new FileNode("report.pdf", 1000));
        root.addChild(new FileNode("report.txt", 500));
        root.addChild(new FileNode("photo.pdf", 2000));

        SearchVisitor visitor = new SearchVisitor("report", 1000);

        assertEquals(List.of("report.pdf"), visitor.search(root));
    }

    @Test
    void testSortedRootRelativePaths() {
        DirectoryNode root = new DirectoryNode("Root");
        DirectoryNode b = new DirectoryNode("B");
        DirectoryNode a = new DirectoryNode("A");

        root.addChild(b);
        root.addChild(a);

        b.addChild(new FileNode("report.txt", 100));
        a.addChild(new FileNode("report.txt", 100));

        SearchVisitor visitor = new SearchVisitor("report", 0);

        assertEquals(
                List.of("A/report.txt", "B/report.txt"),
                visitor.search(root)
        );
    }

    @Test
    void testStatistics() {
        DirectoryNode root = new DirectoryNode("Root");
        DirectoryNode folder = new DirectoryNode("Folder");

        root.addChild(folder);
        folder.addChild(new FileNode("a.txt", 100));
        root.addChild(new FileNode("b.txt", 200));

        StatisticsVisitor visitor = new StatisticsVisitor();
        visitor.analyze(root);

        assertEquals(2, visitor.getFileCount());
        assertEquals(2, visitor.getDirectoryCount());
        assertEquals(2, visitor.getMaxDepth());
    }

    @Test
    void testEmptyDirectory() {
        DirectoryNode root = new DirectoryNode("Root");

        assertEquals(0, new TotalSizeVisitor().calculate(root));
        assertTrue(new SearchVisitor("test", 0).search(root).isEmpty());

        StatisticsVisitor visitor = new StatisticsVisitor();
        visitor.analyze(root);

        assertEquals(0, visitor.getFileCount());
        assertEquals(1, visitor.getDirectoryCount());
        assertEquals(0, visitor.getMaxDepth());
    }

    @Test
    void testSearchNoMatches() {
        DirectoryNode root = new DirectoryNode("Root");
        root.addChild(new FileNode("photo.jpg", 500));

        assertTrue(new SearchVisitor("report", 100).search(root).isEmpty());
    }

    @Test
    void testRepeatedVisitorExecution() {
        DirectoryNode root = new DirectoryNode("Root");
        root.addChild(new FileNode("report.txt", 100));

        TotalSizeVisitor size = new TotalSizeVisitor();
        SearchVisitor search = new SearchVisitor("report", 0);
        StatisticsVisitor stats = new StatisticsVisitor();

        assertEquals(100, size.calculate(root));
        assertEquals(100, size.calculate(root));

        assertEquals(1, search.search(root).size());
        assertEquals(1, search.search(root).size());

        stats.analyze(root);
        stats.analyze(root);

        assertEquals(1, stats.getFileCount());
        assertEquals(1, stats.getDirectoryCount());
    }

    @Test
    void testInvalidTreeChanges() {
        DirectoryNode root = new DirectoryNode("Root");
        DirectoryNode folder = new DirectoryNode("Folder");

        assertThrows(
                IllegalArgumentException.class,
                () -> new FileNode("bad.txt", -1)
        );

        root.addChild(folder);
        root.addChild(new FileNode("a.txt", 100));

        assertThrows(
                IllegalArgumentException.class,
                () -> root.addChild(new FileNode("a.txt", 200))
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> folder.addChild(root)
        );

        DirectoryNode another = new DirectoryNode("Another");

        assertThrows(
                IllegalArgumentException.class,
                () -> another.addChild(folder)
        );
    }

    @Test
    void testChildrenCannotBeModifiedDirectly() {
        DirectoryNode root = new DirectoryNode("Root");

        assertThrows(
                UnsupportedOperationException.class,
                () -> root.getChildren().add(new FileNode("a.txt", 100))
        );
    }
}
