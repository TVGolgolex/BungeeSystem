package de.golgolex.bungeesystem.objects.report;

/*
===========================================================================================================================
# 
# Copyright (c) 2021 Pascal Kurz
# Class created at 22.08.2021, 20:21
# Class created by: Pascal
# 
# Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation 
# files (the "Software"),
# to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, 
# distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software 
# is furnished to do so, subject to the following conditions:
# 
# The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
# 
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
# INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE 
# AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
#  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
# 
===========================================================================================================================
*/

import de.golgolex.bungeesystem.BungeeSystem;
import de.golgolex.bungeesystem.objects.user.User;

public class ReportService {

    private static volatile ReportService instance;

    private final ReportCache reports;

    public ReportService() {
        instance = this;
        this.reports = new ReportCache();
    }

    public ReportCache getReports() {
        return reports;
    }

    public static ReportService getInstance() {
        return instance;
    }

    public Report getReport(String abuserName) {
        return this.reports.stream().filter(report -> report.getAbuserName().equalsIgnoreCase(abuserName)).findFirst().orElse(null);
    }

    public Report getReportById(String id) {
        return this.reports.stream().filter(report -> report.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    public void createReport(String senderName, String abuseName, String id, ReportReasons reportReasons) {
        this.reports.add(new Report(abuseName, senderName, id, reportReasons));

        for (User user : BungeeSystem.getInstance().getUsers()) {
            if (user.getPlayer().hasPermission("de.golgolex.report.message")) {
                user.getPlayer().sendMessage("§8§m-----------------------------§r");
                user.getPlayer().sendMessage("      §e§lNEUER REPORT");
                user.getPlayer().sendMessage("");
                user.getPlayer().sendMessage("§7Reportet§8: §e" + abuseName);
                user.getPlayer().sendMessage("§7Reporter§8: §e" + senderName);
                user.getPlayer().sendMessage("§7ID§8: §e" + id);
                user.getPlayer().sendMessage("§7Gemeldet für §e" + reportReasons.name());
                user.getPlayer().sendMessage("");
                user.getPlayer().sendMessage("§8§m-----------------------------§r");
            }
        }

    }

    public void removeReport(Report report) {
        this.reports.remove(report);
    }

    public void removeReportsById(String id) {
        this.reports.remove(this.reports.stream().filter(report -> report.getId().equalsIgnoreCase(id)).findFirst().orElse(null));
    }

    public void removeReports(String abuserName) {
        this.reports.remove(this.reports.stream().filter(report -> report.getAbuserName().equalsIgnoreCase(abuserName)).findFirst().orElse(null));
    }

}
