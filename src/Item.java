import java.util.Vector;

public class Item{
    public static long codeIncrement;
    public static Vector<Item> gameItems = new Vector<>();
    private long code;
    private String name;

    public Item(){
        code = codeIncrement--;
        this.name = "";
        gameItems.add(this);
    }
    public Item(String name){
        code = codeIncrement--;
        this.name = name;
        gameItems.add(this);
    }
    public Item(long code) throws UnsafeCodeException {
        setCode(code);
        this.name = "";
        gameItems.add(this);
    }
    public Item(String name, long code) throws UnsafeCodeException {
        setCode(code);
        this.name = name;
        gameItems.add(this);
    }
    public String toString(){ return this.getName(); }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setCode(long code) throws UnsafeCodeException {
        if(code < 0){
            throw new UnsafeCodeException(code);
        } else {
            for (Item i: gameItems) {
                if(i.getCode() == code){
                    throw new UnsafeCodeException(code);
                }
            }
        }
        this.code = code;
    }

    public long getCode(){
        return this.code;
    }
}
