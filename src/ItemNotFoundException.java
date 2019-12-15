public class ItemNotFoundException extends Exception {
    Object reference;
    ItemNotFoundException(Object reference){
        super("Item was not found by reference " + reference + "in container.");
    }
    public Object getReference() {
        return reference;
    }
}
