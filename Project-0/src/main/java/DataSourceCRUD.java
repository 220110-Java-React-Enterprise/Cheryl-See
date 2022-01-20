
// This will be an interface
public interface DataSourceCRUD<T> {
    // Returning the data (T) serves as confirmation of what you did to make sure it was added correctly

    // Create
    public T create(T t);

    // Read
    public T read(T t);

    // Update
    public T update(T t);

    // Delete
    public void delete(T t);
}
