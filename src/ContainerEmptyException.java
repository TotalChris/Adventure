public class ContainerEmptyException extends Exception {
    ContainerEmptyException(){
        super("Unable to find an item in container because container is full.");
    }
}
