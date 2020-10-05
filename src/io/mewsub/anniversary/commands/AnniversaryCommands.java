package io.mewsub.anniversary.commands;

import io.mewsub.anniversary.Anniversary;

import java.util.Random;

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
				if( args.length != 22 && args.length != 2 && args.length != 1 ) return true;
				byte[] hash = new byte[ 20 ];
				if( args.length == 22 ) {
					for( int i = 2; i < 22; ++i ) {
						hash[ i - 2 ] = ( byte ) ( ( Integer.parseInt( args[ i ], 16 ) & 0xFF ) - 0x100 );
					}	
				}
				for( Player p : Anniversary.server.getOnlinePlayers() ) {
					if( args.length == 22 ) p.setResourcePack( args[ 1 ], hash );
					else if( args.length == 2 ) p.setResourcePack( args[ 1 ] );
					else p.setResourcePack( "https://github.com/jay-red/Anniversary/raw/main/Anniversary_Edition.zip?rev=" + ( new Random() ).nextInt() );
				}
			}
		}
		return true;
	}
	
}