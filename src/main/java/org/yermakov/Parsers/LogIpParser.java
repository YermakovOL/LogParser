package org.yermakov.Parsers;

import org.yermakov.Event;
import org.yermakov.Status;
import org.yermakov.query.IPQuery;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class LogIpParser extends LogParser implements IPQuery {
     LogIpParser(LogParser parent) {
        super();
        this.logs = parent.logs;
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        HashSet<String> uniqueIps = new HashSet<>();
        for (LogParser.LogEntry lg : getTimeCorrectedString(after, before)) {
            uniqueIps.add(lg.ip);
        }
        return uniqueIps.size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        HashSet<String> uniqueIps = new HashSet<>();
        for (LogParser.LogEntry lg : getTimeCorrectedString(after, before)) {
            uniqueIps.add(lg.ip);
        }
        return uniqueIps;
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        HashSet<String> uniqueIps = new HashSet<>();
        for (LogParser.LogEntry lg : getTimeCorrectedString(after, before)) {
            if (lg.user.equals(user)) uniqueIps.add(lg.ip);
        }
        return uniqueIps;
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        HashSet<String> uniqueIps = new HashSet<>();
        for (LogParser.LogEntry lg : getTimeCorrectedString(after, before)) {
            uniqueIps.add(lg.ip);
        }
        return uniqueIps;

    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        HashSet<String> uniqueIps = new HashSet<>();
        for (LogParser.LogEntry lg : getTimeCorrectedString(after, before)) {
            if (lg.status == status) uniqueIps.add(lg.ip);
        }
        return uniqueIps;
    }
}
