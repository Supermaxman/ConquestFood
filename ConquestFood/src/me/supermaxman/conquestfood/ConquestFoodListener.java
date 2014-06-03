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
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

	
public class ConquestFoodListener implements Listener {
	
	
	
	@EventHandler
	public void onPlayerEat(PlayerItemConsumeEvent e) {
		System.out.println();
		if(ConquestFood.foods.containsKey(e.getItem().getType().toString())){
			Player p = e.getPlayer();
			if(ConquestFood.combat.containsKey(p.getName())) {
				System.out.println(ConquestFood.combat.get(p.getName()));
				System.out.println(System.currentTimeMillis());
				System.out.println(ConquestFood.combat.get(p.getName())+13000);
				if(ConquestFood.combat.get(p.getName())+13000 < System.currentTimeMillis()) {
					ConquestFood.combat.remove(p.getName());
					p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, ConquestFood.foods.get(e.getItem().getType().toString())*25, 1, true));
				}else {
					ItemStack i = e.getItem();
					i.setAmount(1);
					p.getInventory().addItem(i);
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
	public void onEntityRegainHealth(EntityRegainHealthEvent e) {
		if (e.getEntity() instanceof Player) {
			if(e.getRegainReason().equals(RegainReason.REGEN) || e.getRegainReason().equals(RegainReason.SATIATED)) {
				e.setCancelled(true);
			}
		}
	}
}
