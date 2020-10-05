package io.mewsub.anniversary.commands;

import io.mewsub.anniversary.Anniversary;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;

public class AnniversaryCommands implements CommandExecutor {

	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {
		if( args.length > 0 ) {
			String cmd = args[ 0 ];
			if( cmd.equalsIgnoreCase( "pack" ) ) {
				if( args.length != 22 ) return true;
				byte[] hash = new byte[ 20 ];
				for( int i = 2; i < 22; ++i ) {
					hash[ i ] = ( byte ) ( ( Integer.parseInt( args[ i ], 16 ) & 0xFF ) - 0x100 );
				}
				for( Player p : Anniversary.server.getOnlinePlayers() ) {
					p.setResourcePack( args[ 1 ], hash );
				}
			}
		}
		return true;
	}
	
}