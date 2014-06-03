package me.supermaxman.conquestfood;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

	
public class ConquestFoodListener implements Listener {
	
	
	
	@EventHandler
	public void onPlayerEat(PlayerItemConsumeEvent e) {
		if(ConquestFood.foods.containsKey(e.getItem().getType().toString())){
			Player p = e.getPlayer();
			p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, ConquestFood.foods.get(e.getItem().getType().toString())*25, 1, true));
		}
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			for (PotionEffect effect : p.getActivePotionEffects()) {
				if(effect.getType().equals(PotionEffectType.REGENERATION) && effect.isAmbient()) {
					p.removePotionEffect(PotionEffectType.REGENERATION);
				}
			}
		}
	}
	
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
		if (e.getEntity() instanceof Player) {
			e.setFoodLevel(19);
		}
	}
	
	@EventHandler
	public void onEntityRegainHealth(EntityRegainHealthEvent e) {
		if (e.getEntity() instanceof Player) {
			if(e.getRegainReason().equals(RegainReason.REGEN) || e.getRegainReason().equals(RegainReason.SATIATED)) {
				e.setCancelled(true);
			}
		}
	}
}
