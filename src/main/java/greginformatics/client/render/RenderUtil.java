package greginformatics.client.render;

import greginformatics.client.style.background.BackgroundStyles;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;

import static net.minecraftforge.fml.client.config.GuiUtils.drawGradientRect;

public class RenderUtil {

    private RenderUtil() {
    }

    public static void drawTooltipBackGround(int zLevel, int x, int y, int w, int h,
                                             int colorbg1, int colorbg2, int coloredge1, int coloredge2) {

        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();

        drawGradientRect(zLevel, x - 3, y - 4, x + w + 3, y - 3, colorbg1, colorbg1);
        drawGradientRect(zLevel, x - 3, y + h + 3, x + w + 3, y + h + 4, colorbg1, colorbg2);
        drawGradientRect(zLevel, x - 4, y - 3, x + w + 4, y + h + 3, colorbg2, colorbg2);

        drawGradientRect(zLevel, x - 3, y - 2, x - 2, y + h + 2, coloredge1, coloredge2);
        drawGradientRect(zLevel, x + w + 2, y - 2, x + w + 3, y + h + 2, coloredge1, coloredge2);
        drawGradientRect(zLevel, x - 3, y - 3, x + w + 3, y - 2, coloredge1, coloredge1);
        drawGradientRect(zLevel, x - 3, y + h + 2, x + w + 3, y + h + 3, coloredge2, coloredge2);

        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableRescaleNormal();
    }

    public static void drawTooltipBackGround(int zLevel, int x, int y, int w, int h, BackgroundStyles bgStyle) {
        drawTooltipBackGround(zLevel, x, y, w, h,
        bgStyle.getColorbg1(), bgStyle.getColorbg2(), bgStyle.getColoredge1(), bgStyle.getColoredge2());
    }
}
