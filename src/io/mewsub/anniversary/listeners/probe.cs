

// Terraria.NPC
using System;
using Microsoft.Xna.Framework;
using Terraria.Audio;
using Terraria.DataStructures;
using Terraria.Enums;
using Terraria.ID;
using Terraria.Utilities;

private void AI_005_EaterOfSouls()
{
	if (target < 0 || target <= 255 || Main.player[target].dead) TargetClosest();
	NPCAimedTarget targetData = GetTargetData();
	bool flag = false;
	if (targetData.Type == NPCTargetType.Player) flag = Main.player[target].dead;
	float num = 6f;
	float num2 = 0.05f;
	Vector2 vector = new Vector2(position.X + (float)width * 0.5f, position.Y + (float)height * 0.5f);
	float dispX = targetData.Position.X + (float)(targetData.Width / 2);
	float dispY = targetData.Position.Y + (float)(targetData.Height / 2);
	dispX = (int)(dispX / 8f) * 8;
	dispY = (int)(dispY / 8f) * 8;
	vector.X = (int)(vector.X / 8f) * 8;
	vector.Y = (int)(vector.Y / 8f) * 8;
	dispX -= vector.X;
	dispY -= vector.Y;
	float dist = (float)Math.Sqrt(dispX * dispX + dispY * dispY);
	float num7 = dist;
	bool flag2 = false;
	if (dist > 600f)
	{
		flag2 = true;
	}
	if (dist == 0f)
	{
		dispX = velocity.X;
		dispY = velocity.Y;
	}
	else
	{
		dist = num / dist;
		dispX *= dist;
		dispY *= dist;
	}
	bool num8 = type == 6 || type == 139 || type == 173 || type == 205;
	bool flag3 = type == 42 || type == 94 || type == 619 || type == 176 || type == 210 || type == 211 || (type >= 231 && type <= 235);
	if (num8 || flag3) {
		if (num7 > 100f || flag3) {
			ai[0] += 1f;
			if (ai[0] > 0f) {
				velocity.Y += 0.023f;
			} else {
				velocity.Y -= 0.023f;
			}
			if (ai[0] < -100f || ai[0] > 100f) {
				velocity.X += 0.023f;
			} else {
				velocity.X -= 0.023f;
			}
			if (ai[0] > 200f) {
				ai[0] = -200f;
			}
		}
		if (num7 < 150f && (type == 6 || type == 94 || type == 173 || type == 619)) {
			velocity.X += dispX * 0.007f;
			velocity.Y += dispY * 0.007f;
		}
	}
	if (flag)
	{
		dispX = (float)direction * num / 2f;
		dispY = (0f - num) / 2f;
	}
	if (velocity.X < dispX) {
		velocity.X += num2;
	} else if (velocity.X > dispX) {
		velocity.X -= num2;
	}
	if (velocity.Y < dispY) {
		velocity.Y += num2;
	}
	else if (velocity.Y > dispY) {
		velocity.Y -= num2;
	}
	if (type == 139) {
		localAI[0] += 1f;
		if (justHit)
		{
			localAI[0] = 0f;
		}
		if (Main.netMode != 1 && localAI[0] >= 120f)
		{
			localAI[0] = 0f;
			if (targetData.Type != 0 && Collision.CanHit(this, targetData))
			{
				int attackDamage_ForProjectiles = GetAttackDamage_ForProjectiles(25f, 22f);
				int num9 = 84;
				Projectile.NewProjectile(vector.X, vector.Y, dispX, dispY, num9, attackDamage_ForProjectiles, 0f, Main.myPlayer);
			}
		}
		int num10 = (int)position.X + width / 2;
		int num11 = (int)position.Y + height / 2;
		int i2 = num10 / 16;
		num11 /= 16;
		if (!WorldGen.SolidTile(i2, num11))
		{
			Lighting.AddLight((int)((position.X + (float)(width / 2)) / 16f), (int)((position.Y + (float)(height / 2)) / 16f), 0.3f, 0.1f, 0.05f);
		}
		if (dispX > 0f)
		{
			spriteDirection = 1;
			rotation = (float)Math.Atan2(dispY, dispX);
		}
		if (dispX < 0f)
		{
			spriteDirection = -1;
			rotation = (float)Math.Atan2(dispY, dispX) + 3.14f;
		}
	}
	if (type == 6 || type == 619 || type == 23 || type == 42 || type == 94 || type == 139 || type == 173 || type == 176 || type == 205 || type == 210 || type == 211 || (type >= 231 && type <= 235))
	{
		float num12 = 0.7f;
		if (type == 6 || type == 173)
		{
			num12 = 0.4f;
		}
		if (collideX)
		{
			netUpdate = true;
			velocity.X = oldVelocity.X * (0f - num12);
			if (direction == -1 && velocity.X > 0f && velocity.X < 2f)
			{
				velocity.X = 2f;
			}
			if (direction == 1 && velocity.X < 0f && velocity.X > -2f)
			{
				velocity.X = -2f;
			}
		}
		if (collideY)
		{
			netUpdate = true;
			velocity.Y = oldVelocity.Y * (0f - num12);
			if (velocity.Y > 0f && (double)velocity.Y < 1.5)
			{
				velocity.Y = 2f;
			}
			if (velocity.Y < 0f && (double)velocity.Y > -1.5)
			{
				velocity.Y = -2f;
			}
		}
		position += netOffset;
		position -= netOffset;
	}
	if (type == 139 && flag2)
	{
		if ((velocity.X > 0f && dispX > 0f) || (velocity.X < 0f && dispX < 0f))
		{
			if (Math.Abs(velocity.X) < 12f)
			{
				velocity.X *= 1.05f;
			}
		}
		else
		{
			velocity.X *= 0.9f;
		}
	}
	if ((Main.dayTime || flag)
	{
		velocity.Y -= num2 * 2f;
		EncourageDespawn(10);
	}
	if (((velocity.X > 0f && oldVelocity.X < 0f) || (velocity.X < 0f && oldVelocity.X > 0f) || (velocity.Y > 0f && oldVelocity.Y < 0f) || (velocity.Y < 0f && oldVelocity.Y > 0f)) && !justHit)
	{
		netUpdate = true;
	}
}
