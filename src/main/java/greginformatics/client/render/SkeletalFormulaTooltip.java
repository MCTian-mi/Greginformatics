package greginformatics.client.render;

import greginformatics.DepictGenerator;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class SkeletalFormulaTooltip extends DepictionTooltip {

    private final int textureId;

    public SkeletalFormulaTooltip(String name, int width, int height) {
        super();
        this.textureId = DepictGenerator.textureMap.get(name);
        this.setSize(width / 20, height / 20);
    }

    @Override
    public void render(int x, int y) {

        super.render(x, y - getHeight() - 9);

        GlStateManager.bindTexture(textureId);

        GlStateManager.pushMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.translate(x, y - getHeight() - 9, 301);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(0, getHeight(), 0.0D).tex(0.0D, 1.0D).endVertex();
        bufferbuilder.pos(getWidth(), getHeight(), 0.0D).tex(1.0D, 1.0D).endVertex();
        bufferbuilder.pos(getWidth(), 0, 0.0D).tex(1.0D, 0.0D).endVertex();
        bufferbuilder.pos(0, 0, 0.0D).tex(0.0D, 0.0D).endVertex();
        tessellator.draw();

        GlStateManager.popMatrix();
    }
}
