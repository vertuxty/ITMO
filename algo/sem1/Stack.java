import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Stack {
    public static boolean end_f = false;
    public static ArrayList<Integer> stack = new ArrayList<>();
    public static void main(String[] args) {
        ArrayList<String> commands = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String command = sc.next();
            if (command.equals("push")) {
                push_n(sc.nextInt());
            } else {
                switch (command) {
                    case "back" -> back();
                    case "size" -> size();
                    case "clear" -> clear();
                    case "exit" -> exit();
                    case "pop" -> pop();
                }
                if (end_f) {
                    break;
                }
            }
        }
    }

    public static void push_n(int n) {
        stack.add(n);
        System.out.println("ok");
    }

    public static void pop() {
        System.out.println(stack.get(stack.size() - 1));
        stack.remove(stack.size() - 1);
    }

    public static void back() {
        System.out.println(stack.get(stack.size() - 1));
    }

    public static void size() {
        System.out.println(stack.size());
    }

    public static void clear() {
        stack.clear();
        System.out.println("ok");
    }

    public static void exit() {
        System.out.println("bye");
        end_f = true;
    }
}
