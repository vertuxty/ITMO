import java.util.ArrayList;
import java.util.Scanner;

public class queue {
    public static ArrayList<Integer> queue = new ArrayList<>();
    public static boolean end_f = false;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String command = sc.next();
//            System.out.println(command);
            switch (command) {
                case "push" -> push_n(sc.nextInt());
                case "pop" -> pop();
                case "front" -> front();
                case "size" -> size();
                case "clear" -> clear();
                case "exit" -> exit();
            }
//            System.out.println(queue);
            if (end_f) {
                break;
            }
        }
    }

    public static void pop() {
        System.out.println(queue.get(0));
        queue.remove(0);
    }
    public static void push_n(int n) {
        queue.add(n);
        System.out.println("ok");
    }

    public static void front() {
        System.out.println(queue.get(0));
    }
    public static void clear() {
        queue.clear();
        System.out.println("ok");
    }
    public static void exit() {
        System.out.println("bye");
        end_f = true;
    }

    public static void size() {
        System.out.println(queue.size());
    }
}
