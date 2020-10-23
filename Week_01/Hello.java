public class Hello {
    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        double c = a+b * 0.5;
        if (c > 1) System.out.println("c > 1");
        else System.out.println("c < 1");
        for (int i =0; i<10; i++){
            c = i;
        }
    }
}