package me.supermaxman.conquestfood;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

	
public class ConquestFoodListener implements Listener {
	
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerEat(PlayerItemConsumeEvent e) {
		System.out.println();
		if(ConquestFood.foods.containsKey(e.getItem().getType().toString())){
			Player p = e.getPlayer();
			if(ConquestFood.combat.containsKey(p.getName())) {
				if(ConquestFood.combat.get(p.getName())+13000 < System.currentTimeMillis()) {
					ConquestFood.combat.remove(p.getName());
					p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, ConquestFood.foods.get(e.getItem().getType().toString())*25, 1, true));
				}else {
					e.setCancelled(true);
					p.updateInventory();
				}
			}else {
				p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, ConquestFood.foods.get(e.getItem().getType().toString())*25, 1, true));
			}
		}
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(e instanceof EntityDamageByEntityEvent) {
				if(((EntityDamageByEntityEvent) e).getDamager() instanceof Player) {
					Player d = (Player) ((EntityDamageByEntityEvent) e).getDamager();
					ConquestFood.combat.put(p.getName(), System.currentTimeMillis());
					ConquestFood.combat.put(d.getName(), System.currentTimeMillis());
					for (PotionEffect effect : p.getActivePotionEffects()) {
						if(effect.getType().equals(PotionEffectType.REGENERATION) && effect.isAmbient()) {
							p.removePotionEffect(PotionEffectType.REGENERATION);
						}
					}
					for (PotionEffect effect : d.getActivePotionEffects()) {
						if(effect.getType().equals(PotionEffectType.REGENERATION) && effect.isAmbient()) {
							d.removePotionEffect(PotionEffectType.REGENERATION);
						}
					}
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
	public void onPlayerJoin(PlayerJoinEvent e) {
		e.getPlayer().setFoodLevel(19);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		e.getPlayer().setFoodLevel(19);
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
