package org.yermakov;

import java.nio.file.Paths;

public class Solution {
    public static void main(String[] args) {
        LogParser logParser = new LogParser(Paths.get("src/main/java/org/yermakov/logs"));
        System.out.println(logParser.getNumberOfUniqueIPs(null, null));
        System.out.println(logParser.getUniqueIPs(null, null));
        System.out.println(logParser.getIPsForStatus(Status.FAILED,null, null));
        System.out.println(logParser.getIPsForEvent(Event.LOGIN,null, null));
        System.out.println(logParser.getIPsForUser("Vasya Pupkin",null, null));

    }
}