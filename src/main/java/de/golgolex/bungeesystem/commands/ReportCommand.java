package de.golgolex.bungeesystem.commands;

/*
===========================================================================================================================
# 
# Copyright (c) 2021 Pascal Kurz
# Class created at 22.08.2021, 20:04
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
import de.golgolex.bungeesystem.objects.report.Report;
import de.golgolex.bungeesystem.objects.report.ReportReasons;
import de.golgolex.bungeesystem.objects.report.ReportService;
import de.golgolex.bungeesystem.objects.user.User;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Arrays;

public class ReportCommand extends Command {

    private final BungeeSystem instance;
    private final ReportService reportService;

    public ReportCommand(BungeeSystem bungeeSystem) {
        super(
                "report",
                null,
                "melden", "hacker"
        );
        this.instance = bungeeSystem;
        this.reportService = ReportService.getInstance();
        BungeeSystem.getInstance().getProxy().getPluginManager().registerCommand(BungeeSystem.getInstance(), this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final User user = BungeeSystem.getInstance().getUser(sender.getName());

        if (args.length == 0) {
            sendUSage(user);
            return;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                if (!user.getPlayer().hasPermission("de.golgole.bungeesystem.reportlist")) return;

                if (this.reportService.getReports().size() == 0) {
                    user.getPlayer().sendMessage(BungeeSystem.REPORT_PREFIX + "§cEs gibt keine Reports!");
                    return;
                }

                user.getPlayer().sendMessage(BungeeSystem.REPORT_PREFIX + "§7Offene Reports (§e" + this.reportService.getReports().size() + "§7):");
                user.getPlayer().sendMessage(BungeeSystem.REPORT_PREFIX + "§7Zum Bearbeiten §e/report edit <Name>");

                for (Report reports : this.reportService.getReports()) {
                    user.getPlayer().sendMessage(BungeeSystem.REPORT_PREFIX + "§c" + reports.getAbuserName() + " §7von §e" + reports.getSenderName() + " §7für " + reports.getReportReasons() + " §9(" + reports.getId() + ")");
                }

            }
        } else if (args.length == 2) {

            if (args[0].equalsIgnoreCase("edit")) {
                return;
            }

            if (args[0].equalsIgnoreCase("close")) {
                if (this.reportService.getReports().stream().noneMatch(report -> report.getId().equalsIgnoreCase(args[1]))) {
                    user.getPlayer().sendMessage(BungeeSystem.REPORT_PREFIX + "§cDieser Report existiert nicht!");
                    return;
                }

                final Report report = this.reportService.getReportById(args[1]);

                if (this.instance.getUsers().stream().anyMatch(userSender -> userSender.getName().equalsIgnoreCase(report.getSenderName()))) {
                    final User userSender = BungeeSystem.getInstance().getUser(report.getSenderName());
                    userSender.getPlayer().sendMessage(BungeeSystem.REPORT_PREFIX + "§7Dein Report über §e" + report.getAbuserName() + "§7 wegen §e" + report.getReportReasons().name() + " §7 wurde §cgeschlossen§7!");
                }

                user.getPlayer().sendMessage(BungeeSystem.REPORT_PREFIX + "§7Der Report über " +  report.getAbuserName() + " wird §cgeschlossen...");
                this.reportService.removeReport(report);
                user.getPlayer().sendMessage(BungeeSystem.REPORT_PREFIX + "§7Der Report wurde geschlossen!");
                return;
            }

            if (ProxyServer.getInstance().getPlayer(args[0]) == null) {
                user.getPlayer().sendMessage(BungeeSystem.REPORT_PREFIX + "§cDieser Spieler ist nicht online!");
                return;
            }

            if (args[0].equalsIgnoreCase(sender.getName())) {
                user.getPlayer().sendMessage(BungeeSystem.REPORT_PREFIX + "§7Du kannst dic nicht selber melden!");
                return;
            }

            if (Arrays.stream(ReportReasons.values()).noneMatch(reportReasons -> reportReasons.name().equalsIgnoreCase(args[1]))) {
                user.getPlayer().sendMessage(BungeeSystem.REPORT_PREFIX + "§cDieser Grund existiert nicht!");
                return;
            }

            if (this.reportService.getReports().stream().anyMatch(report -> report.getAbuserName().equalsIgnoreCase(args[0]))) {
                user.getPlayer().sendMessage(BungeeSystem.REPORT_PREFIX + "§eDieser Spieler wurde bereits gemeldet!");
                return;
            }

            ReportReasons reportReasons = Arrays.stream(ReportReasons.values()).filter(reportReasons1 -> reportReasons1.name().equalsIgnoreCase(args[1])).findFirst().orElse(null);

            final String id = this.instance.getStringUtil().randomString(6);
            user.getPlayer().sendMessage(BungeeSystem.REPORT_PREFIX + "§aVielen dank für deinen Report mit der ID: §e" + id +"§a! Wir kümmern uns drum!");
            this.reportService.createReport(user.getName(), args[0], id, reportReasons);

        } else {
            sendUSage(user);
        }

    }

    private void sendUSage(User user) {
        user.getPlayer().sendMessage(BungeeSystem.REPORT_PREFIX + "§e/report <Name> <Grund>");
        if (!user.getPlayer().hasPermission("de.golgole.bungeesystem.reportlist")) return;
        user.getPlayer().sendMessage(BungeeSystem.REPORT_PREFIX + "§e/report list");
        user.getPlayer().sendMessage(BungeeSystem.REPORT_PREFIX + "§e/report edit <ID>");
        user.getPlayer().sendMessage(BungeeSystem.REPORT_PREFIX + "§e/report close <ID>");
    }
}
