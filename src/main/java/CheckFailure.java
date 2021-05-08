public class CheckFailure extends Exception{
    String string = "";
    public CheckFailure(String string) {
        this.string = string;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
        System.out.println(string);
    }
}
