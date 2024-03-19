package greginformatics;

import greginformatics.client.DepictionTooltip;
import greginformatics.client.render.renderer.SkeletalFormulaRenderer;
import gregtech.api.items.materialitem.MetaPrefixItem;
import gregtech.api.unification.material.Material;
import gregtech.common.blocks.BlockCompressed;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.block.Block.getBlockFromItem;

@Mod.EventBusSubscriber(modid = Tags.MODID)
public class EventHandlers {

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onMetaPrefixItemTooltipRednerEvent(@NotNull RenderTooltipEvent.PostText event) {
        ItemStack stack = event.getStack();
        if (stack.isEmpty()) return;
        Material material = null;
        Item item = stack.getItem();
        if (item instanceof MetaPrefixItem metaItem) {
            material = metaItem.getMaterial(stack);
        } else if (false) {
            return; // TODO: Add tooltips for fluids and fluid holders
        } else {
            Block block = getBlockFromItem(item);
            if (block instanceof BlockCompressed materialBlock) {
                material = materialBlock.getGtMaterial(stack);
            } else {
                return;
            }
        }
        DepictionTooltip depictionTooltip = new DepictionTooltip(material);
        depictionTooltip.render(event.getX(), event.getY());
        return;
//        FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(stack.getTagCompound());
    }
}
