package org.yermakov.Parsers;

import org.yermakov.Event;
import org.yermakov.query.UserQuery;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class LogUserParser extends LogParser implements UserQuery {
    LogUserParser(LogParser logParser) {
        this.logs = logParser.logs;
    }

    @Override
    public Set<String> getAllUsers() {
        HashSet<String> uniqueIps = new HashSet<>();
        for (LogParser.LogEntry lg : logs) {
            uniqueIps.add(lg.user);
        }
        return uniqueIps;
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {
        HashSet<String> uniqueIps = new HashSet<>();
        for (LogParser.LogEntry lg : getTimeCorrectedString(after, before)) {
            uniqueIps.add(lg.user);
        }
        return uniqueIps.size();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        HashSet<Event> uniqueIps = new HashSet<>();
        for (LogParser.LogEntry lg : getTimeCorrectedString(after, before)) {
            if(lg.user.equals(user)) uniqueIps.add(lg.event);
        }
        return uniqueIps.size();
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        HashSet<String> uniqueIps = new HashSet<>();
        for (LogParser.LogEntry lg : getTimeCorrectedString(after, before)) {
            if(lg.ip.equals(ip))uniqueIps.add(lg.user);
        }
        return uniqueIps;
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        HashSet<String> uniqueIps = new HashSet<>();
        for (LogParser.LogEntry lg : getTimeCorrectedString(after, before)) {
            if(lg.event.equals(Event.LOGIN))uniqueIps.add(lg.user);
        }
        return uniqueIps;
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        HashSet<String> uniqueIps = new HashSet<>();
        for (LogParser.LogEntry lg : getTimeCorrectedString(after, before)) {
            if(lg.event.equals(Event.DOWNLOAD_PLUGIN))uniqueIps.add(lg.user);
        }
        return uniqueIps;
    }

    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        HashSet<String> uniqueIps = new HashSet<>();
        for (LogParser.LogEntry lg : getTimeCorrectedString(after, before)) {
            if(lg.event.equals(Event.WRITE_MESSAGE))uniqueIps.add(lg.user);
        }
        return uniqueIps;
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        HashSet<String> uniqueIps = new HashSet<>();
        for (LogParser.LogEntry lg : getTimeCorrectedString(after, before)) {
            if(lg.event.equals(Event.SOLVE_TASK))uniqueIps.add(lg.user);
        }
        return uniqueIps;
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        HashSet<String> uniqueIps = new HashSet<>();
        for (LogParser.LogEntry lg : getTimeCorrectedString(after, before)) {
            if(lg.event.equals(Event.SOLVE_TASK) && lg.eventAddition == task)uniqueIps.add(lg.user);
        }
        return uniqueIps;
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        HashSet<String> uniqueIps = new HashSet<>();
        for (LogParser.LogEntry lg : getTimeCorrectedString(after, before)) {
            if(lg.event.equals(Event.DONE_TASK))uniqueIps.add(lg.user);
        }
        return uniqueIps;
    }
    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        HashSet<String> uniqueIps = new HashSet<>();
        for (LogParser.LogEntry lg : getTimeCorrectedString(after, before)) {
            if(lg.event.equals(Event.DONE_TASK) && lg.eventAddition== task)uniqueIps.add(lg.user);
        }
        return uniqueIps;
    }
}
