package com.garbagemule.MobArena.WileeCommands;

import com.garbagemule.MobArena.MAUtils;
import me.StevenLawson.TotalFreedomMod.Commands.PlayerNotFoundException;
import me.StevenLawson.TotalFreedomMod.TFM_RollbackManager;
import me.StevenLawson.TotalFreedomMod.TFM_ServerInterface;
import me.StevenLawson.TotalFreedomMod.TFM_SuperadminList;
import me.StevenLawson.TotalFreedomMod.TFM_WorldEditBridge;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Command_wileemanage extends MA_Command
{
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.RED + "Usage: /wileemanage <power> [arg]");
        }
        else if (args[0].equalsIgnoreCase("help"))
        {
            sender.sendMessage(ChatColor.GREEN + "=====WileeManage Help Page=====");
            sender.sendMessage(ChatColor.GREEN + "Please do not abuse any commands or over-use them. Thanks.");
            sender.sendMessage(ChatColor.RED + "/wileemanage obliviate <player> - Superadmin command - Obliviate a bad player. Just for the really bad ones.");
            sender.sendMessage(ChatColor.RED + "/wileemanage nope <player> - Superadmin command - Nope a bad player.");
            sender.sendMessage(ChatColor.RED + "/wileemanage ebroadcast <message> - Superadmin command - Broadcast to the server Essentials style.");
            sender.sendMessage(ChatColor.RED + "/wileemanage ride <player> - Superadmin command - Ride any player.");
            sender.sendMessage(ChatColor.RED + "/wileemanage unride - Superadmin command - Unride whoever you are riding.");
            sender.sendMessage(ChatColor.RED + "/wileemanage strength <on|off> - Superadmin command - Toggle strength epic powaaazzz.");
            sender.sendMessage(ChatColor.GREEN + "Please do not abuse any commands or over-use them. Thanks.");
            sender.sendMessage(ChatColor.GREEN + "=====WileeManage Help Page=====");
        }
        else if (args[0].equalsIgnoreCase("obliviate"))
        {
            if (TFM_SuperadminList.isUserSuperadmin(sender))
            {
                if (args.length == 1)
                {
                    sender.sendMessage(ChatColor.RED + "Usage: /wileemanage obliviate <player>");
                    return true;
                }

                final Player player;
                try
                {
                    player = getPlayer(args[1]);
                }
                catch (PlayerNotFoundException ex)
                {
                    sender.sendMessage(ex.getMessage());
                    return true;
                }

                MAUtils.adminAction(sender.getName(), "Casting complete holy obliviation over " + player.getName(), ChatColor.RED);
                MAUtils.bcastMsg(player.getName() + " will be completely obliviated using thy satanic holy powers!", ChatColor.RED);
                MAUtils.bcastMsg(player.getName() + " has been a VERY naughty, naughty boy.", ChatColor.RED);

                final String IP = player.getAddress().getAddress().getHostAddress().trim();

                // remove from whitelist
                player.setWhitelisted(false);

                // deop
                if (player.isOp())
                {
                    player.setOp(false);
                }

                // ban IP
                TFM_ServerInterface.banIP(IP, null, null, null);

                // ban name
                TFM_ServerInterface.banUsername(player.getName(), null, null, null);

                // set gamemode to survival
                player.setGameMode(GameMode.SURVIVAL);

                // clear inventory
                player.closeInventory();
                player.getInventory().clear();

                // ignite player
                player.setFireTicks(10000);

                // rollback + undo
                TFM_WorldEditBridge.getInstance().undo(player, 15);

                TFM_RollbackManager.rollback(player.getName());

                // generate explosion
                player.getWorld().createExplosion(player.getLocation(), 7F);

                // go up into the sky
                player.setVelocity(new Vector(0, 20, 0));

                // runnables

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // ask the player a question
                        MAUtils.bcastMsg("Hey, " + player.getName() + ", what's the difference between jelly and jam?", ChatColor.LIGHT_PURPLE);
                    }
                }.runTaskLater(plugin, 40L);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // answer it
                        MAUtils.bcastMsg("I can't jelly my banhammer down your throat.", ChatColor.LIGHT_PURPLE);

                        // generate explosion
                        player.getWorld().createExplosion(player.getLocation(), 7F);

                        // strike lightning
                        player.getWorld().strikeLightning(player.getLocation());
                    }
                }.runTaskLater(plugin, 100L);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // strike lightning
                        player.getWorld().strikeLightning(player.getLocation());

                        // kill (if not done already)
                        player.setHealth(0.0);

                        // generate explosion
                        player.getWorld().createExplosion(player.getLocation(), 7F);

                        // go up into the sky
                        player.setVelocity(new Vector(0, 20, 0));
                    }
                }.runTaskLater(plugin, 140L);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // strike lightning
                        player.getWorld().strikeLightning(player.getLocation());

                        // kill (if not done already)
                        player.setHealth(0.0);

                        // generate explosion
                        player.getWorld().createExplosion(player.getLocation(), 7F);

                        // go up into the sky
                        player.setVelocity(new Vector(0, 20, 0));
                    }
                }.runTaskLater(plugin, 160L);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // message
                        MAUtils.adminAction(sender.getName(), "Banning: " + player.getName() + ", IP: " + IP, ChatColor.RED);

                        // generate explosion
                        player.getWorld().createExplosion(player.getLocation(), 7F);

                        // kick player
                        player.kickPlayer(ChatColor.RED + "FUCKOFF, and get your MOTHER FUCKING shit together!");
                    }
                }.runTaskLater(plugin, 190L);

                return true;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
        }
        else if (args[0].equalsIgnoreCase("nope"))
        {
            if (TFM_SuperadminList.isUserSuperadmin(sender))
            {
                if (args.length == 1)
                {
                    sender.sendMessage(ChatColor.RED + "Usage: /wileemanage nope <player>");
                    return true;
                }

                final Player player;
                try
                {
                    player = getPlayer(args[1]);
                }
                catch (PlayerNotFoundException ex)
                {
                    sender.sendMessage(ex.getMessage());
                    return true;
                }

                final String IP = player.getAddress().getAddress().getHostAddress().trim();
                MAUtils.adminAction(sender.getName(), "Starting a huge nope fest over " + player.getName(), ChatColor.RED);

                // go up into the sky
                player.setVelocity(new org.bukkit.util.Vector(0, 4, 0));

                // blow up
                player.getWorld().createExplosion(player.getLocation(), 4F);

                // strike lightning
                player.getWorld().strikeLightning(player.getLocation());

                // ban IP
                TFM_ServerInterface.banIP(IP, null, null, null);

                // ban name
                TFM_ServerInterface.banUsername(player.getName(), null, null, null);

                // rollback + undo
                TFM_WorldEditBridge.getInstance().undo(player, 15);

                TFM_RollbackManager.rollback(player.getName());

                // runnables

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // go up into the sky
                        player.setVelocity(new org.bukkit.util.Vector(0, 90, 0));

                        // blow up
                        player.getWorld().createExplosion(player.getLocation(), 4F);

                        // strike lightning
                        player.getWorld().strikeLightning(player.getLocation());
                    }
                }.runTaskLater(plugin, 50L);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // go up into the sky
                        player.setVelocity(new org.bukkit.util.Vector(0, 140, 0));

                        // blow up
                        player.getWorld().createExplosion(player.getLocation(), 4F);

                        // strike lightning
                        player.getWorld().strikeLightning(player.getLocation());
                    }
                }.runTaskLater(plugin, 90L);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // message
                        MAUtils.adminAction(sender.getName(), "Banning: " + player.getName() + ", IP: " + IP, ChatColor.RED);

                        // generate explosion
                        player.getWorld().createExplosion(player.getLocation(), 4F);

                        // kick player
                        player.kickPlayer(ChatColor.RED + "NOPE!\nAppeal at totalfreedom.boards.net\nAnd make sure you follow the rules at totalfreedom.me");
                    }
                }.runTaskLater(plugin, 120L);

                return true;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
        }
        else if (args[0].equalsIgnoreCase("ebroadcast"))
        {
            if (TFM_SuperadminList.isUserSuperadmin(sender))
            {
                if (args.length == 1)
                {
                    sender.sendMessage(ChatColor.RED + "Usage: /wileemanage ebroadcast <message>");
                    return true;
                }

                String message = "";
                for (int i = 1; i < args.length; i++)
                {
                    if (i > 1)
                    {
                        message += " ";
                    }
                    message += args[i];
                }

                MAUtils.bcastMsg(ChatColor.RED + "[" + ChatColor.GREEN + "Broadcast" + ChatColor.RED + "] " + ChatColor.AQUA + message);
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
        }
        else if (args[0].equalsIgnoreCase("ride"))
        {
            if (!TFM_SuperadminList.isUserSuperadmin(sender) || sender instanceof ConsoleCommandSender)
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command, or you are runnning this command from the console.");
            }
            else
            {
                if (args.length == 1)
                {
                    sender.sendMessage(ChatColor.RED + "Usage: /wileemanage ride <player>");
                    return true;
                }

                Player player = Bukkit.getPlayer(args[1]);
                if (player.isOnline())
                {
                    if (!player.getName().equalsIgnoreCase(sender.getName()))
                    {
                        player.setPassenger(sender_p);
                        sender.sendMessage(ChatColor.GREEN + "You are now riding: " + player.getName());
                    }
                    else
                    {
                        sender.sendMessage(ChatColor.RED + "You cannot ride yourself.");
                    }
                }
                else
                {
                    sender.sendMessage(ChatColor.RED + "That player is not online.");
                }
            }
        }
        else if (args[0].equalsIgnoreCase("unride"))
        {
            if (!TFM_SuperadminList.isUserSuperadmin(sender) || sender instanceof ConsoleCommandSender)
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command, or you are runnning this command from the console.");
            }
            else
            {
                if (sender_p.getVehicle() != null && sender_p.getVehicle() instanceof Player)
                {
                    Player otherp = (Player) sender_p.getVehicle();
                    otherp.setPassenger(sender_p);
                    sender.sendMessage(ChatColor.RED + "You have stopped riding: " + otherp.getName());
                }
                else
                {
                    sender.sendMessage(ChatColor.RED + "You must be riding someone.");
                }
            }
        }
        else if (args[0].equalsIgnoreCase("strength"))
        {
            if (TFM_SuperadminList.isUserSuperadmin(sender))
            {
                if (args.length == 1)
                {
                    sender.sendMessage(ChatColor.RED + "Usage: /wileemanage strength <on|off>");
                    return true;
                }
                else if (args[1].equalsIgnoreCase("on"))
                {
                    if (!MAUtils.strengthEnabled.contains(sender.getName()))
                    {
                        MAUtils.strengthEnabled.add(sender.getName());
                    }
                    sender.sendMessage(ChatColor.GREEN + "Strength has been enabled.");
                    return true;
                }
                else if (args[1].equalsIgnoreCase("off"))
                {
                    if (MAUtils.strengthEnabled.contains(sender.getName()))
                    {
                        MAUtils.strengthEnabled.remove(sender.getName());
                    }
                    sender.sendMessage(ChatColor.RED + "Strength has been disabled.");
                    return true;
                }

                return true;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "Usage: /wileemanage <power> [arg]");
        }

        return true;
    }
}
