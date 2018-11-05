package com.hjx.pzwdshxzt.zyc;



public class Main {
    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("Hello.asdd");
        t.insert("Hello.asdsdd");
        t.insert("Hello.aasdasdd");

        FA machine = Util.createFAFromTrie(t);
        System.out.println(machine.genRegEx());
    }
}
