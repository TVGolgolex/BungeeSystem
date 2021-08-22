package de.golgolex.bungeesystem.listener;

/*
===========================================================================================================================
# 
# Copyright (c) 2021 Pascal Kurz
# Class created at 22.08.2021, 19:58
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
import de.golgolex.bungeesystem.objects.report.ReportService;
import de.golgolex.bungeesystem.objects.user.User;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ConnectionListener implements Listener {

    private final BungeeSystem instance;

    public ConnectionListener(BungeeSystem bungeeSystem) {
        this.instance = bungeeSystem;
        this.instance.getProxy().getPluginManager().registerListener(this.instance, this);
    }

    @EventHandler
    public void handle(PostLoginEvent event) {
        final ProxiedPlayer proxiedPlayer = event.getPlayer();
        new User(proxiedPlayer.getName(), proxiedPlayer.getUniqueId());
    }

    @EventHandler
    public void handle(PlayerDisconnectEvent event) {
        final ProxiedPlayer proxiedPlayer = event.getPlayer();

        if (ReportService.getInstance().getReports().stream().anyMatch(report -> report.getAbuserName().equalsIgnoreCase(proxiedPlayer.getName()))) {
            final Report report = ReportService.getInstance().getReport(proxiedPlayer.getName());
            for (User user : BungeeSystem.getInstance().getUsers()) {
                if (user.getPlayer().hasPermission("de.golgolex.report.message")) {
                    user.getPlayer().sendMessage("§8§m-----------------------------§r");
                    user.getPlayer().sendMessage("      §c§lREPORT GESCHLOSSEN");
                    user.getPlayer().sendMessage("");
                    user.getPlayer().sendMessage("§7Reportet§8: §e" + report.getAbuserName());
                    user.getPlayer().sendMessage("§7Reporter§8: §e" + report.getSenderName());
                    user.getPlayer().sendMessage("§7ID§8: §e" + report.getId());
                    user.getPlayer().sendMessage("§7Gemeldet für §e" + report.getReportReasons().name());
                    user.getPlayer().sendMessage("§7Grund§8: §cNetzwerk verlassen");
                    user.getPlayer().sendMessage("");
                    user.getPlayer().sendMessage("§8§m-----------------------------§r");
                }
            }
            ReportService.getInstance().removeReport(report);
        }

        this.instance.getUsers().remove(
                this.instance.getUsers().stream().filter(user -> user.getName().equalsIgnoreCase(proxiedPlayer.getName())).findFirst().orElse(null)
        );
    }

}
