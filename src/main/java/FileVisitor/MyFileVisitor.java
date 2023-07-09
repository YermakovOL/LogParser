package FileVisitor;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MyFileVisitor<Path> implements FileVisitor<Path> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.y H:m:s");
    private LocalDateTime before;
    private LocalDateTime after;
    private final List<String> logLines ;

    public MyFileVisitor(Date before, Date after, List<String> logLines) {
        if (before != null) this.before = before.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (after != null) this.after = after.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.logLines = logLines;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        try (BufferedReader bf = new BufferedReader(new FileReader(file.toString()))) {
            while (bf.ready()) {
                String tempLine = bf.readLine();
                String[] splitLine = tempLine.split("\\t+");

                LocalDateTime dateOfLog = LocalDateTime.parse(splitLine[2], formatter);
                if(before==null || !dateOfLog.isBefore(before)){
                    if(after == null|| dateOfLog.isAfter(after)) logLines.add(tempLine);
                }
            }
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return FileVisitResult.CONTINUE;
    }
}
