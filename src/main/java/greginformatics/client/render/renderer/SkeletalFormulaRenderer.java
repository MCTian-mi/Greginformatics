package greginformatics.client.render.renderer;

import greginformatics.client.render.RenderUtil;
import greginformatics.client.style.background.BackgroundStyles;
import gregtech.api.unification.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class SkeletalFormulaRenderer {

    private SkeletalFormulaRenderer() {
    }

    public static void render(int x, int y, int w, int h, ResourceLocation location) {

        if (location == null) return;

        int pad = 7;
        float scale = 0.1F;

        RenderUtil.drawTooltipBackGround(300, x, y, (int) ( w * 0.1F), (int) ( h * 0.1F), BackgroundStyles.DEFAULT); // TODO: Adjust bgstyle here

        Minecraft.getMinecraft().getTextureManager().bindTexture(location);

        GlStateManager.pushMatrix();
        GlStateManager.color(1F, 1F, 1F);
        RenderHelper.disableStandardItemLighting();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        GlStateManager.translate(x, y, 301);
        GlStateManager.scale(scale, scale, 1.0F);

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(-pad, h, 0.0D).tex(0.0D, 1.0D).endVertex();
        buffer.pos(w, h, 0.0D).tex(1.0D, 1.0D).endVertex();
        buffer.pos(w, -pad, 0.0D).tex(1.0D, 0.0D).endVertex();
        buffer.pos(-pad, -pad, 0.0D).tex(0.0D, 0.0D).endVertex();
        tessellator.draw();

        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}
