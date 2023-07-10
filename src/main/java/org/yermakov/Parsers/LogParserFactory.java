package org.yermakov.Parsers;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class LogParserFactory {
    private static final Map<Path,LogParser> parsers = new HashMap<>();

    private LogParserFactory(){
    }
    private static LogParser createLogParser(Path directory){
        if (parsers.containsKey(directory))return parsers.get(directory);
        LogParser logParser = new LogParser(directory);
        parsers.put(directory,logParser);
        return logParser;

    }
    public static LogIpParser createLogIpParser(Path directory){
        return new LogIpParser(createLogParser(directory));
    }
    public static LogUserParser createLogUserParser(Path directory){
        return new LogUserParser(createLogParser(directory));
    }

}
