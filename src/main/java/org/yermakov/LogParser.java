package org.yermakov;

import FileVisitor.MyFileVisitor;
import org.yermakov.query.IPQuery;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/*
1.1. Добавь в класс LogParser конструктор с параметром Path logDir, где logDir - директория с логами (логов может быть несколько, все они должны иметь расширение log).
1.2. Реализуй интерфейс IPQuery у класса LogParser:
1.2.1. Метод getNumberOfUniqueIPs(Date after, Date before) должен возвращать количество уникальных IP адресов за выбранный период. Здесь и далее, если в методе есть параметры Date after и Date before, то нужно возвратить данные касающиеся только данного периода (включая даты after и before).
Если параметр after равен null, то нужно обработать все записи, у которых дата меньше или равна before.
Если параметр before равен null, то нужно обработать все записи, у которых дата больше или равна after.
Если и after, и before равны null, то нужно обработать абсолютно все записи (без фильтрации по дате).
1.2.2. Метод getUniqueIPs() должен возвращать множество, содержащее все не повторяющиеся IP. Тип в котором будем хранить IP будет String.
1.2.3. Метод getIPsForUser() должен возвращать IP, с которых работал переданный пользователь.
1.2.4. Метод getIPsForEvent() должен возвращать IP, с которых было произведено переданное событие.
1.2.5. Метод getIPsForStatus() должен возвращать IP, события с которых закончилось переданным статусом.
 */
public class LogParser implements IPQuery {
    private final Path directoryWithLogs;
    public LogParser(Path directoryWithLogs) {
        this.directoryWithLogs = directoryWithLogs;
    }

    private List<String> getLogStrings(Date after, Date before){
        List<String> stringList= new ArrayList<>();
        try {
            Files.walkFileTree(directoryWithLogs, new MyFileVisitor<>(before, after, stringList));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return stringList;
    }
    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        List<String> stringList= getLogStrings(after,before);
        HashSet<String> uniqueIps = new HashSet<>();
        for(String s: stringList){
            uniqueIps.add(s.split("\\s+")[0]);
        }
        uniqueIps.forEach(System.out::println);
        return uniqueIps.size();
        }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        List<String> stringList= getLogStrings(after,before);
        HashSet<String> uniqueIps = new HashSet<>();
        for(String s: stringList) {
            uniqueIps.add(s.split("\\s+")[0]);
        }
        return uniqueIps;
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        List<String> stringList= getLogStrings(after,before);
        HashSet<String> uniqueIps = new HashSet<>();
        for(String s: stringList){
            if(s.split("\\t")[1].equals(user))uniqueIps.add(s.split("\\t")[0]);
        }
        return uniqueIps;
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        List<String> stringList= getLogStrings(after,before);
        HashSet<String> uniqueIps = new HashSet<>();
        for(String s: stringList){
            if(s.split("\\t")[3].equals(event.name()))uniqueIps.add(s.split("\\t")[0]);
        }
        return uniqueIps;

    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        List<String> stringList= getLogStrings(after,before);
        HashSet<String> uniqueIps = new HashSet<>();
        for(String s: stringList){
            if(s.split("\\t")[4].equals(status.name()))uniqueIps.add(s.split("\\t")[0]);
        }
        return uniqueIps;
    }
}