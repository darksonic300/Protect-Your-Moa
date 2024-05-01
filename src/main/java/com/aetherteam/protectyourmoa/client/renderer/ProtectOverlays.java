package com.aetherteam.protectyourmoa.client.renderer;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.api.AetherMoaTypes;
import com.aetherteam.aether.api.registers.MoaType;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.protectyourmoa.ProtectYourMoa;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ProtectYourMoa.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ProtectOverlays {
    private static final ResourceLocation TEXTURE_JUMPS = new ResourceLocation(ProtectYourMoa.MODID, "textures/gui/jumps_gravitite.png");

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("moa_jumps_gravitite", (gui, graphics, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderMoaJumps(graphics, window, player);
            }
        });
    }

    private static void renderMoaJumps(GuiGraphics guiGraphics, Window window, LocalPlayer player) {
        if (player.getVehicle() instanceof Moa moa) {
            MoaType moaType = moa.getMoaType();
            int jumps = moaType != null ? moaType.getMaxJumps() : AetherMoaTypes.BLUE.get().getMaxJumps();
            for (int jumpCount = jumps; jumpCount < moa.getMaxJumps(); jumpCount++) {
                int xPos = ((window.getGuiScaledWidth() / 2) + (jumpCount * 8)) - (moa.getMaxJumps() * 8) / 2;
                int yPos = 18;
                if (jumpCount < moa.getRemainingJumps()) {
                    guiGraphics.blit(TEXTURE_JUMPS, xPos, yPos, 0, 0, 9, 11, 256, 256);
                } else {
                    guiGraphics.blit(TEXTURE_JUMPS, xPos, yPos, 10, 0, 9, 11, 256, 256);
                }
            }
        }
    }
}
