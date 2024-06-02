package greginformatics.client.render;

import com.cleanroommc.modularui.drawable.GuiDraw;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DepictionTooltip {

    private static final int maxWidth = 200;
    private static final List<String> EMPTY = new ArrayList<>();
    private int width = 16;
    private int height = 16;

    public DepictionTooltip() {
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void render(int x, int y) {
        GuiDraw.drawTooltipBackground(ItemStack.EMPTY, EMPTY, x, y, width, height);
    }
}
