import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Directory implements File {
    Map<String, File> files;
    Directory parent;
    String name;


    public Directory(Directory parent, String name) {
        this.files = new HashMap<>();
        this.parent = parent;
        this.name = name;
    }

    public Directory(String name) {
        this(null, name);
    }

    public Directory getParent() {
        return parent;
    }

    public Collection<File> getFiles() {
        return files.values();
    }

    public Directory getOrCreateChildDirectory(String name) {
        if (!files.containsKey(name)) {
            addNewDirectory(name);
        }
        return (Directory) files.get(name);
    }

    public void addNewDirectory(String name) {
        File newDirectory = new Directory(this, name);
        this.files.put(name, newDirectory);
    }

    public void addNewData(String name, int size) {
        File newData = new Data(name, size);
        this.files.put(name, newData);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        int sizeTotal = 0;
        for (File file : files.values()) {
            sizeTotal += file.getSize();
        }
        return sizeTotal;
    }

    public int getLimitSize() {
        int sizeTotal = 0;
        for (File file : this.getFiles()) {
            if (file instanceof Directory)
                sizeTotal += ((Directory) file).getLimitSize();
        }
        if (this.getSize() <= Main.size_limit) {
            sizeTotal += this.getSize();
        }
        return sizeTotal;
    }

    public int smallestAboveSize(int limitSize) {
        if (this.getSize() < limitSize)
            return Integer.MAX_VALUE;
        int sizeOfFile = this.getSize();
        for (File file : this.getFiles()) {
            if (file instanceof Directory) {
                Directory directory = (Directory) file;
                if (directory.getSize() >= limitSize) {
                    sizeOfFile = Math.min(directory.smallestAboveSize(limitSize), sizeOfFile);
                }
            }
        }
        return sizeOfFile;
    }
}
