package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("Hello Java");
//        System.out.println(args[0]+' '+args[1]);
        System.out.println(System.getProperty("username"));
        int a;
        int b;
        int c;
        int d;
        a=23;
        b=32;
        c=sum(a,b);
        System.out.println(c);
    }
    public static int sum(int a, int b){
        a+=b;
        b+=a;
        return a+b;
    }
}
