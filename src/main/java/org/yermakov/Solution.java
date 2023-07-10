package org.yermakov;

import org.yermakov.Parsers.LogIpParser;
import org.yermakov.Parsers.LogParserFactory;
import org.yermakov.Parsers.LogUserParser;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Solution {
    public static void main(String[] args) {
        Path directory = Paths.get("src/main/java/org/yermakov/logs");
        LogIpParser logIpParser = LogParserFactory.createLogIpParser(directory);
        System.out.println(logIpParser.getNumberOfUniqueIPs(null, null));
        System.out.println(logIpParser.getIPsForEvent(Event.LOGIN,null,null));
        System.out.println(logIpParser.getIPsForUser("Vasya Pupkin",null, null));
        System.out.println(logIpParser.getIPsForStatus(Status.OK,null,null));
        LogUserParser logUserParser = LogParserFactory.createLogUserParser(directory);
        System.out.println(logUserParser.getAllUsers());
        System.out.println(logUserParser.getNumberOfUsers(null,null));
        System.out.println(logUserParser.getLoggedUsers(null,null));

        //        LogParser logParser = new LogParser(Paths.get("src/main/java/org/yermakov/logs"))
        //        ;
//        System.out.println(logParser.getNumberOfUniqueIPs(null, null));
//        System.out.println(logParser.getUniqueIPs(null, null));
//        System.out.println(logParser.getIPsForStatus(Status.FAILED,null, null));
//        System.out.println(logParser.getIPsForEvent(Event.DONE_TASK,null, null));
//        System.out.println(logParser.getIPsForUser("Vasya Pupkin",null, null));

    }
}