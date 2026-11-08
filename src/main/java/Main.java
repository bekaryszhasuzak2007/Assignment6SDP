
public class Main {
    public static void main(String[] args) {
        DirectoryNode root = new DirectoryNode("Root");

        DirectoryNode documents = new DirectoryNode("Documents");
        DirectoryNode images = new DirectoryNode("Images");

        root.addChild(documents);
        root.addChild(images);

        documents.addChild(new FileNode("report.pdf", 5000));
        documents.addChild(new FileNode("notes.txt", 2000));
        images.addChild(new FileNode("photo.jpg", 3000));
        root.addChild(new FileNode("music.mp3", 4000));

        TotalSizeVisitor sizeVisitor = new TotalSizeVisitor();
        System.out.println("Total size: " + sizeVisitor.calculate(root));

        SearchVisitor searchVisitor = new SearchVisitor(".pdf", 1000);
        System.out.println("Search results: " + searchVisitor.search(root));

        StatisticsVisitor statisticsVisitor = new StatisticsVisitor();
        statisticsVisitor.analyze(root);

        System.out.println("Files: " + statisticsVisitor.getFileCount());
        System.out.println("Directories: " + statisticsVisitor.getDirectoryCount());
        System.out.println("Maximum depth: " + statisticsVisitor.getMaxDepth());
    }
}
