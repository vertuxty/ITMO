package expression;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Add sub = new Add(new Subtract(
                new Multiply(
                        new Variable("x"),
                        new Variable("x")
                ),
                new Variable("x")
        ), new Const(1));
//        System.out.println(sub.evaluate(Integer.parseInt(args[0])));
//        System.out.println(new Multiply(new Variable("x"), new Variable("x"))
//                .equals(new Multiply(new Variable("x"), new Variable("x"))));
//        Subtract sub1 = new Subtract(new Const(1), new Add(new Multiply(
//                new Const(2), new Variable("x")), new Variable("y")));
//////
////        Add ex1 = new Add(new Multiply(new Const(1), new Variable("x")), new Variable("x"));
////        Add ex2 = new Add(new Multiply(new Const(1), new Variable("x")), new Variable("x"));
//        StringBuilder sb = new StringBuilder(sub.toString());
//        System.out.println(sub1);
//        System.out.println(sub1.evaluate(10, 2, -100));
//        System.out.println(sub.evaluate(10, 2, -1));
//        System.out.println(sb);
    }
}
