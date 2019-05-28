public class Test {
    static int x1 = 2;

    static{
        x1++;
    }

    public static void main(String[] args) {
        System.out.println(x1);
    }

    static {
        x1 -= 2;
    }
}