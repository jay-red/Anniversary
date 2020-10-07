package io.mewsub.anniversary;

import org.bukkit.entity.Cat;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Player;

public class AnniversaryPlayer {

	Cat cat;
	Giant giant;
	Player player;

	public AnniversaryPlayer( Player player ) {
		this.cat = null;
		this.giant = null;
		this.player = player;
	}

	public Cat getCat() {
		return this.cat;
	}

	public Giant getGiant() {
		return this.giant;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void setCat( Cat cat ) {
		this.cat = cat;
	}

	public void setGiant( Giant giant ) {
		this.giant = giant;
	}

}