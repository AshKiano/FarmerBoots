package com.ashkiano.farmerboots;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class FarmerBoots extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Register this Listener when the plugin starts
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if(event.getTo().getBlock().getType() == Material.FARMLAND && event.getPlayer().getInventory().getBoots() != null) {
            if(event.getPlayer().getInventory().getBoots().getType() == Material.LEATHER_BOOTS) {
                // Mark the block so that it doesn't turn back to dirt
                event.getTo().getBlock().setMetadata("preventTrample", new FixedMetadataValue(this, true));
            }
        }
    }

    @EventHandler
    public void onBlockFade(BlockFadeEvent event) {
        Block block = event.getBlock();
        if(block.getType() == Material.FARMLAND && block.hasMetadata("preventTrample")) {
            event.setCancelled(true);
            block.removeMetadata("preventTrample", this);
        }
    }
}