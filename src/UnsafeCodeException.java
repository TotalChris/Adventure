public class UnsafeCodeException extends Exception {
    private long triedCode;
    UnsafeCodeException(long triedCode){
        super("Unable to create item with code" + triedCode + "either because code resides in system-reserved space (code < 0), or the code is already in use by another Item (code = othercode).");
        this.triedCode = triedCode;
    }
    public long getTriedCode(){
        return triedCode;
    }
}
