package de.golgolex.bungeesystem.commands;

/*
===========================================================================================================================
# 
# Copyright (c) 2021 Pascal Kurz
# Class created at 22.08.2021, 19:52
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
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PingCOmmand extends Command {

    public PingCOmmand() {
        super(
                "ping",
                null,
                "ms");
        BungeeSystem.getInstance().getProxy().getPluginManager().registerCommand(BungeeSystem.getInstance(), this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final User user = BungeeSystem.getInstance().getUser(sender.getName());

        if (args.length == 0) {
            user.getPlayer().sendMessage(BungeeSystem.PREFIX + "§7Dein Ping beträgt §e" + user.getPlayer().getPing() + " §7ms.");
            return;
        }

        if (args.length > 1) {
            user.getPlayer().sendMessage(BungeeSystem.PREFIX + "§e/ping <Name>");
            user.getPlayer().sendMessage(BungeeSystem.PREFIX + "§e/ping");
            return;
        }

        if (ProxyServer.getInstance().getPlayer(args[0]) == null) {
            user.getPlayer().sendMessage(BungeeSystem.PREFIX + "§cDieser Speielr ist nicht online!");
            return;
        }

        ProxiedPlayer proxiedPlayer = ProxyServer.getInstance().getPlayer(args[0]);
        user.getPlayer().sendMessage(BungeeSystem.PREFIX + "§7Der Ping von §e" + proxiedPlayer.getName() + " §7beträgt §e" + proxiedPlayer.getPing() + " §7ms.");

    }
}
