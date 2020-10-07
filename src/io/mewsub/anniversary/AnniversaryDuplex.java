package io.mewsub.anniversary;

import io.mewsub.anniversary.Anniversary;
import io.mewsub.anniversary.AnniversaryPlayer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

import org.bukkit.Location;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;

import org.bukkit.entity.Cat;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Player;

import org.bukkit.util.Vector;

import org.bukkit.craftbukkit.v1_16_R2.entity.CraftEntity;

import net.minecraft.server.v1_16_R2.Vec3D;
import net.minecraft.server.v1_16_R2.Entity;
import net.minecraft.server.v1_16_R2.EntityInsentient;
import net.minecraft.server.v1_16_R2.EntityLiving;
import net.minecraft.server.v1_16_R2.EntityHuman;
import net.minecraft.server.v1_16_R2.PacketPlayInSteerVehicle;

public class AnniversaryDuplex extends ChannelDuplexHandler {
	AnniversaryPlayer player;

	public AnniversaryDuplex( Player player ) {
		this.player = Anniversary.getPlayer( player );
	}

	public void channelRead( ChannelHandlerContext ctx, Object msg ) throws Exception {
		if( msg instanceof PacketPlayInSteerVehicle	) {
			PacketPlayInSteerVehicle packet = ( PacketPlayInSteerVehicle ) msg;
			Cat cat = player.getCat();
			Giant giant = player.getGiant();
			if( cat != null ) {
				double forward = packet.c();
				double sideways = packet.b();
				boolean jump = packet.d();

				EntityInsentient ei = ( EntityInsentient ) ( ( CraftEntity ) cat ).getHandle();
				EntityLiving elCat = ( EntityLiving ) ei;

				EntityLiving elPlayer = ( EntityLiving ) ( ( CraftEntity ) player.getPlayer() ).getHandle();

				Vec3D catVelocity = elCat.getMot();
				if( jump && cat.isOnGround() ) {
					elCat.setMot( catVelocity.x, 0.42, catVelocity.z );
					catVelocity = elCat.getMot();
                    elCat.impulse = true;
				}

				if( forward > 0 || forward < 0 || sideways > 0 || sideways < 0 ) {
					//cat.setRotation( player.getPlayer().getLocation().getYaw(), player.getPlayer().getLocation().getPitch() );
					elCat.yaw = elPlayer.yaw;
					elCat.lastYaw = elCat.yaw;
					elCat.pitch = elPlayer.pitch * 0.5F;

					Field aZField = EntityLiving.class.getDeclaredField( "aZ" );
					aZField.setAccessible( true );

					Field bbField = EntityLiving.class.getDeclaredField( "bb" );
					bbField.setAccessible( true );

					Field bBField = EntityLiving.class.getDeclaredField( "bB" );
					bBField.setAccessible( true );

					Method setYawPitch = Entity.class.getDeclaredMethod( "setYawPitch", float.class, float.class );
					setYawPitch.setAccessible( true );
                	setYawPitch.invoke( elCat, elCat.yaw, elCat.pitch );

                	double aZ = elPlayer.getMot().x * 20;
                	double bb = elPlayer.getMot().z * 20;

                	AttributeInstance speedAttr = cat.getAttribute( Attribute.GENERIC_MAX_HEALTH );
                	bBField.set( elCat, ( ( float ) speedAttr.getBaseValue() ) );
                	elCat.setMot( new Vec3D( aZ, catVelocity.y, bb ) );
				}
				

				/*el.yaw = el.yaw;
				el.lastX = el.lastX;
				Field aZField = EntityLiving.class.getDeclaredField( "aZ" );

				aZField.setAccessible( true );
				//el.aZ = el.aZ;
				Object aZ = aZField.get( el );

				Field bbField = EntityLiving.class.getDeclaredField( "aZ" );

				bbField.setAccessible( true );
				//el.bb = el.bb;
				Object bb = bbField.get( el );
				el.e(new Vec3D(0.0, 0.0, 0.0));

				if( ei instanceof EntityLiving ) {
					System.out.println( "isLiving" );
				}*/
				//( ( EntityInsentient ) ( ( CraftEntity ) cat ).getHandle() ).getNavigation().a( loc.getX(), loc.getY(), loc.getZ(), 1.5 );
			} else if( giant != null ) {
				double forward = packet.c();
				double sideways = packet.b();
				boolean jump = packet.d();

				EntityInsentient ei = ( EntityInsentient ) ( ( CraftEntity ) giant ).getHandle();
				EntityLiving elGiant = ( EntityLiving ) ei;

				EntityLiving elPlayer = ( EntityLiving ) ( ( CraftEntity ) player.getPlayer() ).getHandle();

				Vec3D giantVelocity = elGiant.getMot();
				if( jump && giant.isOnGround() ) {
					elGiant.setMot( giantVelocity.x, 2, giantVelocity.z );
					giantVelocity = elGiant.getMot();
                    elGiant.impulse = true;
				}

				if( forward > 0 || forward < 0 || sideways > 0 || sideways < 0 ) {
					//cat.setRotation( player.getPlayer().getLocation().getYaw(), player.getPlayer().getLocation().getPitch() );
					elGiant.yaw = elPlayer.yaw;
					elGiant.lastYaw = elGiant.yaw;
					elGiant.pitch = elPlayer.pitch * 0.5F;

					Field aZField = EntityLiving.class.getDeclaredField( "aZ" );
					aZField.setAccessible( true );

					Field bbField = EntityLiving.class.getDeclaredField( "bb" );
					bbField.setAccessible( true );

					Field bBField = EntityLiving.class.getDeclaredField( "bB" );
					bBField.setAccessible( true );

					Method setYawPitch = Entity.class.getDeclaredMethod( "setYawPitch", float.class, float.class );
					setYawPitch.setAccessible( true );
                	setYawPitch.invoke( elGiant, elGiant.yaw, elGiant.pitch );

                	double aZ = elPlayer.getMot().x * 20;
                	double bb = elPlayer.getMot().z * 20;

                	bBField.set( elGiant, 3.0f );
                	elGiant.setMot( new Vec3D( aZ, giantVelocity.y, bb ) );
				}
			}
		}
		super.channelRead( ctx, msg );
	}
}