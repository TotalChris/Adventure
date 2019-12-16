import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;
import java.util.Vector;

public class Container extends Item{
    private Vector<Item> contents;
    private int capacity;
    public Container(){
        super();
        contents = new Vector<>();
        this.capacity = 64;
    }
    public Container(String name){
        super(name);
        contents = new Vector<>();
        this.capacity = 64;
    }
    public Container(int capacity){
        super();
        contents = new Vector<>();
        this.capacity = capacity;
    }
    public Container(long code) throws UnsafeCodeException {
        super(code);
        contents = new Vector<>();
        this.capacity = 64;
    }
    public Container(int capacity, String name){
        super(name);
        contents = new Vector<>();
        this.capacity = capacity;
    }
    public Container(String name, long code) throws UnsafeCodeException {
        super(name, code);
        contents = new Vector<>();
        this.capacity = 64;
    }
    public Container(int capacity, long code) throws UnsafeCodeException {
        super(code);
        contents = new Vector<>();
        this.capacity = capacity;
    }
    public Container(int capacity, String name, long code) throws UnsafeCodeException {
        super(name, code);
        contents = new Vector<>();
        this.capacity = capacity;
    }
    /**
     * Add an item to this container. It is good practice to specify a name for the item prior to adding it. This way, it is easy to find using getItem() later on.
     *
     * @param item The item to add.
     */
    public void addItem(Item item) throws ContainerFullException {
        if(item instanceof Container){ //no stacking containers yet!
        } else {
            if(this.contents.size() < capacity){
                contents.add(item);
            } else {
                throw new ContainerFullException(item);
            }
        }
    }

    /**
     * Remove an item from this container.
     *
     * @param item the item to remove
     * @return True if the item was removed from the container. False if the item was not found in the container.
     */
    public void removeItem(Item item) throws ItemNotFoundException, ContainerEmptyException {
        if(contents.size() == 0){
            throw new ContainerEmptyException();
        }
        if(!contents.remove(item)){
            throw new ItemNotFoundException(item);
        }
    }

    /**
     * Gets the item vector for this container.
     *
     * @return The item vector for this container
     */
    public Vector<Item> getAllItem() {
        return contents;
    }

    /**
     * Get a specific item for the container based on its name
     *
     * @param name The name string to look for
     * @throws ItemNotFoundException When the item can't be found
     * @return The item that has that name
     */
    public Item getItemByName(String name) throws ItemNotFoundException, ContainerEmptyException {
        if(contents.size() == 0){
            throw new ContainerEmptyException();
        }
        for (Item i: contents) {
            if(i.getName().equals(name)){
                return i;
            }
        }
        throw new ItemNotFoundException(name);
    }

    /**
     * Get a specific item for the container based on its global code
     *
     * @param code The code to look for
     * @throws ItemNotFoundException When the item can't be found
     * @return The item that has that code
     */
    public Item getItemByCode(int code) throws ItemNotFoundException, ContainerEmptyException {
        if(contents.size() == 0){
            throw new ContainerEmptyException();
        }
        for (Item i: contents) {
            if(i.getCode() == code){
                return i;
            }
        }
        throw new ItemNotFoundException(code);
    }

    public int getCapacity(){
        return this.capacity;
    }

    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    public boolean isEmpty(){
        return contents.size() == 0;
    }

    public Item getFirstItem() throws ContainerEmptyException {
        try {
            return contents.firstElement();
        } catch(NoSuchElementException e){
            throw new ContainerEmptyException();
        }
    }
    public String getAllItemsString(String containerName){
        String response = "";
        Vector<Item> itemList = this.getAllItem();
        for (Item item : itemList) {
            response += "There is a | " + item.getName() + " | in the | " + containerName + " |.\n";
            if(item instanceof Container){
                response += ((Container)item).getAllItemsString(item.getName());
            }
        }
        return response;
    }

}
