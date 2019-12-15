public class ContainerFullException extends Exception {
    private Item triedItem;
    ContainerFullException(Item triedItem){
        super("Unable to put item " + triedItem.toString() + " in container because container is full.");
        this.triedItem = triedItem;
    }
    public Item getTriedItem(){
        return triedItem;
    }
}
