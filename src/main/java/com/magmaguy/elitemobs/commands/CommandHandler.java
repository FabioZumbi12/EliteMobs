/*
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.magmaguy.elitemobs.commands;

import com.magmaguy.elitemobs.MetadataHandler;
import com.magmaguy.elitemobs.commands.shops.CustomShopHandler;
import com.magmaguy.elitemobs.commands.shops.ShopHandler;
import com.magmaguy.elitemobs.config.ConfigValues;
import com.magmaguy.elitemobs.config.DefaultConfig;
import com.magmaguy.elitemobs.config.NPCConfig;
import com.magmaguy.elitemobs.config.TranslationConfig;
import com.magmaguy.elitemobs.npcs.NPCEntity;
import com.magmaguy.elitemobs.utils.Round;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * Created by MagmaGuy on 21/01/2017.
 */

public class CommandHandler implements CommandExecutor {

    private final static String STATS = "elitemobs.stats";
    private final static String GETLOOT = "elitemobs.getloot";
    private final static String SIMLOOT = "elitemobs.simloot";
    public final static String RELOAD_CONFIGS = "elitemobs.reload";
    private final static String GIVELOOT = "elitemobs.giveloot";
    private final static String SPAWNMOB = "elitemobs.spawnmob";
    private final static String SPAWN_BOSS_MOB = "elitemobs.spawnbossmob";
    public final static String KILLALL_AGGRESSIVEELITES = "elitemobs.killall.aggressiveelites";
    public final static String KILLALL_PASSIVEELITES = "elitemobs.killall.passiveelites";
    public final static String KILLALL_SPECIFICENTITY = "elitemobs.killall.specificentity";
    private final static String SHOP = "elitemobs.shop";
    private final static String CUSTOMSHOP = "elitemobs.customshop";
    private final static String CURRENCY_PAY = "elitemobs.currency.pay";
    private final static String CURRENCY_ADD = "elitemobs.currency.add";
    private final static String CURRENCY_SUBTRACT = "elitemobs.currency.subtract";
    private final static String CURRENCY_SET = "elitemobs.currency.set";
    private final static String CURRENCY_CHECK = "elitemobs.currency.check";
    private final static String CURRENCY_WALLET = "elitemobs.currency.wallet";
    private final static String CURRENCY_COINTOP = "elitemobs.currency.cointop";
    private final static String VERSION = "elitemobs.version";
    public final static String EVENT_LAUNCH_SMALLTREASUREGOBLIN = "elitemobs.events.smalltreasuregoblin";
    public final static String EVENT_LAUNCH_DEADMOON = "elitemobs.events.smalltreasuregoblin";
    private final static String CHECK_TIER = "elitemobs.checktier";
    private final static String SET_MAX_TIER = "elitemobs.config.setmaxtier";
    private final static String GET_TIER = "elitemobs.gettier";
    private final static String CHECK_MAX_TIER = "elitemobs.checkmaxtier";
    private final static String ADVENTURERS_GUILD = "elitemobs.adventurersguild";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        switch (label) {
            case "ag":
            case "adventurersguild":
            case "adventurerguild":
                if (userPermCheck(ADVENTURERS_GUILD, commandSender))
                    new AdventurersGuildCommand((Player) commandSender);
                return true;
        }

        if (args.length == 0) {
            validCommands(commandSender);
            return true;
        }

        args[0] = args[0].toLowerCase();

        switch (args[0]) {
            case "spawn":
            case "spawnmob":
                if (permCheck(SPAWNMOB, commandSender))
                    SpawnMobCommandHandler.spawnMob(commandSender, args);
                return true;
            case "ag":
            case "adventurersguild":
            case "adventurerguild":
                if (userPermCheck(ADVENTURERS_GUILD, commandSender))
                    new AdventurersGuildCommand((Player) commandSender);
                return true;
            case "stats":
                if (permCheck(STATS, commandSender))
                    StatsCommandHandler.statsHandler(commandSender);
                return true;
            case "getloot":
            case "gl":
                if (userPermCheck(GETLOOT, commandSender) && args.length == 1) {
                    LootGUI lootGUI = new LootGUI();
                    lootGUI.lootGUI((Player) commandSender);
                } else {
                    if (GetLootCommandHandler.getLoot(((Player) commandSender), args[1]))
                        return true;
                    else
                        ((Player) commandSender).sendTitle("", "Could not find that item name.");
                }
                return true;
            case "shop":
            case "store":
                if (userPermCheck(SHOP, commandSender)) {
                    new ShopHandler((Player) commandSender);
                }
                return true;
            case "customshop":
            case "cshop":
            case "customstore":
            case "cstore":
                if (userPermCheck(CUSTOMSHOP, commandSender)) {
                    CustomShopHandler.CustomShopHandler((Player) commandSender);
                }
                return true;
            case "wallet":
            case "bal":
            case "balance":
            case "currency":
            case "money":
            case "$":
                if (userPermCheck(CURRENCY_WALLET, commandSender))
                    CurrencyCommandsHandler.walletCommand(commandSender, args);
                return true;
            case "cointop":
            case "baltop":
            case "cashtop":
            case "currencytop":
            case "$top":
                if (permCheck(CURRENCY_COINTOP, commandSender))
                    CurrencyCommandsHandler.coinTop(commandSender);
                return true;
            case "version":
                if (permCheck(VERSION, commandSender))
                    VersionHandler.versionCommand(commandSender, args);
                return true;
            case "checktier":
            case "tiercheck":
            case "tier":
                if (permCheck(CHECK_TIER, commandSender))
                    CheckTierCommand.checkTier(((Player) commandSender));
                return true;
            case "checkmaxtier":
            case "maxtier":
                if (permCheck(CHECK_MAX_TIER, commandSender))
                    CheckMaxItemTierCommand.checkMaxItemTier(commandSender);
                return true;
            case "reload":
            case "restart":
                if (permCheck(RELOAD_CONFIGS, commandSender))
                    ReloadHandler.reloadCommand(commandSender, args);
                return true;
            case "killall":
            case "kill":
                KillHandler.killCommand(commandSender, args);
                return true;
            case "simloot":
            case "simulateloot":
            case "simdrop":
            case "simulatedrop":
                if (userPermCheck(SIMLOOT, commandSender))
                    SimLootHandler.simLoot((Player) commandSender, Integer.parseInt(args[1]));
                return true;
            case "check":
            case "checkcurrency":
            case "checkbal":
            case "checkbalance":
            case "check$":
                if (permCheck(CURRENCY_CHECK, commandSender))
                    CurrencyCommandsHandler.checkCommand(commandSender, args);
                return true;
            case "event":
            case "launchevent":
            case "startevent":
            case "triggerevent":
                TriggerEventHandler.triggerEventCommand(commandSender, args);
                return true;
            case "spawnbossmob":
            case "spawnboss":
                if (userPermCheck(SPAWNMOB, commandSender))
                    SpawnMobCommandHandler.spawnBossMob((Player) commandSender, args);
                return true;
            case "gettier":
            case "spawntier":
                if (userPermCheck(GET_TIER, commandSender))
                    TierSetSpawner.spawnTierItem(Integer.parseInt(args[1]), (Player) commandSender);
                return true;
            case "setmaxtier":
                if (permCheck(SET_MAX_TIER, commandSender))
                    SetMaxItemTierCommand.setMaxItemTier(Double.parseDouble(args[1]), commandSender);
                return true;
            case "giveloot":
                if (permCheck(GIVELOOT, commandSender))
                    GiveLootHandler.giveLootCommand(commandSender, args);
                return true;
            case "pay":
                if (userPermCheck(CURRENCY_PAY, commandSender))
                    CurrencyCommandsHandler.payCommand((Player) commandSender, args);
                return true;
            case "add":
                if (permCheck(CURRENCY_ADD, commandSender))
                    CurrencyCommandsHandler.addCommand(commandSender, args);
                return true;
            case ("subtract"):
                if (permCheck(CURRENCY_SUBTRACT, commandSender))
                    CurrencyCommandsHandler.subtractCommand(commandSender, args);
                return true;
            case ("set"):
                if (permCheck(CURRENCY_SET, commandSender))
                    CurrencyCommandsHandler.setCommand(commandSender, args);
            case ("npc"):
                if (args.length <= 1) {
                    commandSender.sendMessage("[EliteMobs] Invalid command syntax. Valid options:");
                    commandSender.sendMessage("/em npc set [npc key]");
                    commandSender.sendMessage("/em npc remove [npc key]");
                    return true;
                } else {
                    if (args[1].equalsIgnoreCase("set")) {

                        if (permCheck("elitemobs.npc.set", commandSender)) {

                            if (args.length == 2) {
                                commandSender.sendMessage("[EliteMobs] Invalid command syntax. Valid options:");
                                commandSender.sendMessage("/em npc set [npc key]");
                                commandSender.sendMessage("Valid keys: " + ConfigValues.npcConfig.getKeys(false).toString());
                                return true;
                            }

                            Location playerLocation = ((Player) commandSender).getLocation();

                            String location = playerLocation.getWorld().getName() + ","
                                    + Round.twoDecimalPlaces(playerLocation.getX()) + ","
                                    + Round.twoDecimalPlaces(playerLocation.getY()) + ","
                                    + Round.twoDecimalPlaces(playerLocation.getZ()) + ","
                                    + Round.twoDecimalPlaces(playerLocation.getYaw()) + ","
                                    + Round.twoDecimalPlaces(playerLocation.getPitch());

                            try {
                                NPCEntity.removeNPCEntity(NPCEntity.getNPCEntityFromKey(args[2]));
                                NPCConfig npcConfig = new NPCConfig();
                                npcConfig.configuration.set(args[2] + "." + NPCConfig.LOCATION, location);
                                npcConfig.customConfigLoader.saveCustomConfig(NPCConfig.CONFIG_NAME);
                                ConfigValues.npcConfig.set(args[2] + "." + NPCConfig.LOCATION, location);
                                new NPCEntity(args[2]);
                            } catch (Exception e) {
                                commandSender.sendMessage("[EliteMobs] Invalid key. Valid options:");
                                commandSender.sendMessage("Valid keys: " + ConfigValues.npcConfig.getKeys(false).toString());
                                return true;
                            }
                        }

                        return true;

                    }
                    if (args[1].equalsIgnoreCase("remove")) {

                        if (permCheck("elitemobs.npc.remove", commandSender)) {

                            if (args.length == 2) {
                                commandSender.sendMessage("[EliteMobs] Invalid command syntax. Valid options:");
                                commandSender.sendMessage("/em npc remove [npc key]");
                                commandSender.sendMessage("Valid keys: " + ConfigValues.npcConfig.getKeys(false).toString());
                                return true;
                            }

                            try {
                                NPCEntity.removeNPCEntity(NPCEntity.getNPCEntityFromKey(args[2]));
                                NPCConfig npcConfig = new NPCConfig();
                                npcConfig.configuration.set(args[2] + "." + NPCConfig.ENABLED, false);
                                npcConfig.customConfigLoader.saveCustomConfig(NPCConfig.CONFIG_NAME);
                                ConfigValues.npcConfig.set(args[2] + "." + NPCConfig.ENABLED, false);
                                new NPCEntity(args[2]);
                            } catch (Exception e) {
                                commandSender.sendMessage("[EliteMobs] Invalid key. Valid options:");
                                commandSender.sendMessage("Valid keys: " + ConfigValues.npcConfig.getKeys(false).toString());
                                return true;
                            }
                        }

                        return true;
                    }

                    if (args[1].equalsIgnoreCase("add")) {

                        if (permCheck("elitemobs.npc.add", commandSender)) {

                            if (args.length == 2) {
                                commandSender.sendMessage("[EliteMobs] Invalid command syntax. Valid options:");
                                commandSender.sendMessage("/em npc add [npc key]");
                                commandSender.sendMessage("Valid keys: " + ConfigValues.npcConfig.getKeys(false).toString());
                                return true;
                            }

                            try {
                                if (!ConfigValues.npcConfig.getKeys(false).contains(args[2])) throw new Exception();
                                NPCConfig npcConfig = new NPCConfig();
                                npcConfig.configuration.set(args[2] + "." + NPCConfig.ENABLED, true);
                                npcConfig.customConfigLoader.saveCustomConfig(NPCConfig.CONFIG_NAME);
                                ConfigValues.npcConfig.set(args[2] + "." + NPCConfig.ENABLED, true);
                                new NPCEntity(args[2]);
                            } catch (Exception e) {
                                commandSender.sendMessage("[EliteMobs] Invalid key. Valid options:");
                                commandSender.sendMessage("Valid keys: " + ConfigValues.npcConfig.getKeys(false).toString());
                                return true;
                            }
                        }

                        return true;
                    }
                }
                return true;
            default:
                validCommands(commandSender);
                return true;

        }

    }

    public static boolean permCheck(String permission, CommandSender commandSender) {

        if (commandSender.hasPermission(permission)) return true;

        if (commandSender instanceof Player &&
                Bukkit.getPluginManager().getPlugin(MetadataHandler.ELITE_MOBS).getConfig().getBoolean(DefaultConfig.ENABLE_PERMISSION_TITLES)) {

            Player player = (Player) commandSender;

            player.sendTitle(ConfigValues.translationConfig.getString(TranslationConfig.MISSING_PERMISSION_TITLE).replace("$username", player.getDisplayName()),
                    ConfigValues.translationConfig.getString(TranslationConfig.MISSING_PERMISSION_SUBTITLE).replace("$permission", permission));

        } else {

            commandSender.sendMessage("[EliteMobs] You may not run this command.");
            commandSender.sendMessage("[EliteMobs] You don't have the permission " + permission);

        }

        return false;

    }

    public static boolean userPermCheck(String permission, CommandSender commandSender) {

        if (commandSender instanceof Player) {

            return permCheck(permission, commandSender);

        }

        commandSender.sendMessage("[EliteMobs] You may not run this command.");
        commandSender.sendMessage("[EliteMobs] This is a user command.");
        return false;

    }

    private static void validCommands(CommandSender commandSender) {

        if (commandSender instanceof Player) {

            Player player = (Player) commandSender;

            player.sendMessage("[EliteMobs] " + ConfigValues.translationConfig.getString(TranslationConfig.VALID_COMMANDS));
            if (silentPermCheck(STATS, commandSender))
                player.sendMessage("/elitemobs stats");
            if (silentPermCheck(SHOP, commandSender))
                player.sendMessage("/elitemobs shop");
            if (silentPermCheck(CUSTOMSHOP, commandSender))
                player.sendMessage("/elitemobs customshop");
            if (silentPermCheck(CURRENCY_WALLET, commandSender))
                player.sendMessage("/elitemobs wallet");
            if (silentPermCheck(CURRENCY_COINTOP, commandSender))
                player.sendMessage("/elitemobs cointop");
            if (silentPermCheck(CURRENCY_PAY, commandSender))
                player.sendMessage("/elitemobs pay [username]");
            if (silentPermCheck(CURRENCY_ADD, commandSender))
                player.sendMessage("/elitemobs add [username]");
            if (silentPermCheck(CURRENCY_SUBTRACT, commandSender))
                player.sendMessage("/elitemobs subtract [username]");
            if (silentPermCheck(CURRENCY_SET, commandSender))
                player.sendMessage("/elitemobs set [username]");
            if (silentPermCheck(CURRENCY_CHECK, commandSender))
                player.sendMessage("/elitemobs check [username]");
            if (silentPermCheck(RELOAD_CONFIGS, commandSender))
                player.sendMessage("/elitemobs reload");
            if (silentPermCheck(KILLALL_AGGRESSIVEELITES, commandSender))
                player.sendMessage("/elitemobs kill aggressive");
            if (silentPermCheck(KILLALL_PASSIVEELITES, commandSender))
                player.sendMessage("/elitemobs kill passive");
            if (silentPermCheck(SIMLOOT, commandSender))
                player.sendMessage("/elitemobs simloot [mob level]");
            if (silentPermCheck(GETLOOT, commandSender))
                player.sendMessage("/elitemobs getloot [loot name (no loot name = AdventurersGuildGUI)]");
            if (silentPermCheck(GIVELOOT, commandSender))
                player.sendMessage("/elitemobs giveloot [player name] random/[loot_name_underscore_for_spaces]");
            if (silentPermCheck(SPAWNMOB, commandSender))
                player.sendMessage("/elitemobs SpawnMob [mobType] [mobLevel] [mobPower] [mobPower2(keep adding as many as you'd like)]");
            if (silentPermCheck(SPAWN_BOSS_MOB, commandSender))
                commandSender.sendMessage("/elitemobs spawnBossMob [bossName]");
            if (silentPermCheck(CHECK_TIER, commandSender))
                commandSender.sendMessage("/elitemobs checktier");
            if (silentPermCheck(CHECK_MAX_TIER, commandSender))
                commandSender.sendMessage("/elitemobs checkmaxtier");
            if (silentPermCheck(SET_MAX_TIER, commandSender))
                commandSender.sendMessage("/elitemobs setmaxtier [tier]");
            if (silentPermCheck(GET_TIER, commandSender))
                commandSender.sendMessage("/elitemobs gettier [tier]");


        } else if (commandSender instanceof ConsoleCommandSender) {

            commandSender.sendMessage("[EliteMobs] " + ConfigValues.translationConfig.getString(TranslationConfig.INVALID_COMMAND));
            commandSender.sendMessage("elitemobs stats");
            commandSender.sendMessage("elitemobs reload");
            commandSender.sendMessage("elitemobs check [username]");
            commandSender.sendMessage("elitemobs set [username]");
            commandSender.sendMessage("elitemobs add [username]");
            commandSender.sendMessage("elitemobs subtract [username]");
            commandSender.sendMessage("elitemobs killall passiveelites");
            commandSender.sendMessage("elitemobs killall aggressiveelites");
            commandSender.sendMessage("elitemobs giveloot [player name] random/[loot_name_underscore_for_spaces]");
            commandSender.sendMessage("elitemobs SpawnMob [worldName] [x] [y] [z] [mobType] [mobLevel] [mobPower] [mobPower2(keep adding as many as you'd like)]");

        }

    }

    private static boolean silentPermCheck(String permission, CommandSender commandSender) {

        return commandSender.hasPermission(permission);

    }


}
