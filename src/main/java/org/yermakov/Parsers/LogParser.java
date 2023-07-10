package org.yermakov.Parsers;

import org.yermakov.Event;
import org.yermakov.Status;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

    class LogParser {
    private final Path directoryWithLogs;

    Set<LogEntry> logs = new HashSet<>();

    public LogParser(Path directoryWithLogs) {
        this.directoryWithLogs = directoryWithLogs;
        readLog();
    }

    public LogParser() {
        directoryWithLogs = null;
    }
    private void readLog() {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directoryWithLogs)) {
            for (Path path : directoryStream) {
                if (!path.toString().endsWith(".log")) continue;
                try (BufferedReader bf = new BufferedReader(new FileReader(path.toFile()))) {
                    while (bf.ready()) {
                        String[] logParts = bf.readLine().split("\\t");
                        logs.add(writeEntry(logParts));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private LogEntry writeEntry(String[] logParts) {
        // example: [192.168.100.2	Vasya Pupkin	30.08.2012 16:08:40	DONE_TASK 15	OK]
        String ip = logParts[0];
        String user = logParts[1];
        LocalDateTime dateOfLog = LocalDateTime.parse(logParts[2], DateTimeFormatter.ofPattern("d.M.y H:m:s"));
        Event event = checkEvent(logParts[3]);
        int eventAddition = -1;
        if (event == Event.SOLVE_TASK || event == Event.DONE_TASK) {
            eventAddition = Integer.parseInt(logParts[3].split("\\s")[1]);
        }
        Status status = checkStatus(logParts[4]);
        return new LogEntry(ip, user, dateOfLog, event, eventAddition, status);
    }

    private Event checkEvent(String nameEvent) {
        switch (nameEvent) {
            case "LOGIN" -> {
                return Event.LOGIN;
            }
            case "DOWNLOAD_PLUGIN" -> {
                return Event.DOWNLOAD_PLUGIN;
            }
            case "WRITE_MESSAGE" -> {
                return Event.WRITE_MESSAGE;
            }
            case "SOLVE_TASK" -> {
                return Event.SOLVE_TASK;
            }
            case "DONE_TASK" -> {
                return Event.DONE_TASK;
            }
            default -> {
                String[] temp = nameEvent.split("\\s");
                if (temp.length == 2) return checkEvent(temp[0]);
                return null;
            }
        }
    }

    private Status checkStatus(String nameStatus) {
        switch (nameStatus) {
            case "OK" -> {
                return Status.OK;
            }
            case "FAILED" -> {
                return Status.FAILED;
            }
            case "ERROR" -> {
                return Status.ERROR;
            }
            default -> {
                return null;
            }
        }

    }

    Set<LogEntry> getTimeCorrectedString(Date after, Date before) {

        if (after == null && before == null) return logs;

        Set<LogEntry> stringSet = new HashSet<>();
        LocalDateTime localDateTimeAfter = null;
        LocalDateTime localDateTimeBefore = null;

        if (after != null) localDateTimeAfter = after.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (before != null)
            localDateTimeBefore = before.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        for (LogEntry lg : logs) {
            if (after == null || lg.date.isAfter(localDateTimeAfter)) {
                if (before == null || lg.date.isBefore(localDateTimeBefore)) stringSet.add(lg);
            }
        }
        return stringSet;
    }

    static class LogEntry {

        final String ip;
        final String user;
        final LocalDateTime date;
        final Event event;
        final int eventAddition;
        final Status status;

        @Override
        public int hashCode() {
            return Objects.hash(ip, user, date, event, eventAddition, status);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LogEntry logEntry = (LogEntry) o;
            return eventAddition == logEntry.eventAddition && Objects.equals(ip, logEntry.ip) && Objects.equals(user, logEntry.user) && Objects.equals(date, logEntry.date) && event == logEntry.event && status == logEntry.status;
        }

        LogEntry(String ip, String user, LocalDateTime date, Event event, int eventAddition, Status status) {
            this.ip = ip;
            this.user = user;
            this.date = date;
            this.event = event;
            this.eventAddition = eventAddition;
            this.status = status;
        }
    }
}