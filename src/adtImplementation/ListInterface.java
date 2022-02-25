public interface ListInterface {
    void add(int newValue);
    void remove(int specificValue);
    void removeOnID(int id);
    void retrieve(int id);
    void retrieveOnID(int id);
    void edit(int id,int newValue);
}
