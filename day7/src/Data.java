public class Data implements File {
    String name;
    int size;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        return size;
    }

//    @Override
//    public int getLimitSize() {
//        if (size <= Main.size_limit)
//            return size;
//        else
//            return 0;
//    }

    public Data(String name, int size) {
        this.name = name;
        this.size = size;
    }
}
