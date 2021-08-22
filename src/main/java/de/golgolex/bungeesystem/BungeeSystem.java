package de.golgolex.bungeesystem;

/*
===========================================================================================================================
# 
# Copyright (c) 2021 Pascal Kurz
# Class created at 22.08.2021, 19:49
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

import de.golgolex.bungeesystem.commands.PingCOmmand;
import de.golgolex.bungeesystem.commands.ReportCommand;
import de.golgolex.bungeesystem.listener.ConnectionListener;
import de.golgolex.bungeesystem.objects.report.ReportCache;
import de.golgolex.bungeesystem.objects.report.ReportService;
import de.golgolex.bungeesystem.objects.user.User;
import de.golgolex.bungeesystem.objects.user.UserCache;
import de.golgolex.bungeesystem.utils.StringUtil;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.UUID;

public class BungeeSystem extends Plugin {

    private static volatile BungeeSystem instance;
    public static final String PREFIX = "§8[§6Bungee§8] §7";
    public static final String REPORT_PREFIX = "§8[§cReport§8] §7";

    private UserCache users;
    private StringUtil stringUtil;

    @Override
    public void onEnable() {
        instance = this;
        this.users = new UserCache();
        this.stringUtil = new StringUtil();

        new ReportService();

        new ConnectionListener(this);

        new PingCOmmand();
        new ReportCommand(this);
    }

    public StringUtil getStringUtil() {
        return stringUtil;
    }

    public UserCache getUsers() {
        return users;
    }

    public static BungeeSystem getInstance() {
        return instance;
    }

    public User getUser(String name) {
        return this.users.stream().filter(user -> user.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public User getUser(UUID uuid) {
        return this.users.stream().filter(user -> user.getUniqueId().equals(uuid)).findFirst().orElse(null);
    }
}
