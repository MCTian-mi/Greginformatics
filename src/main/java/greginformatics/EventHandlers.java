package greginformatics;

import greginformatics.client.DepictionTooltip;
import gregtech.api.fluids.GTFluid;
import gregtech.api.items.materialitem.MetaPrefixItem;
import gregtech.api.unification.material.Material;
import gregtech.common.blocks.BlockCompressed;
import mezz.jei.gui.recipes.RecipesGui;
import mezz.jei.input.ClickedIngredient;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import static greginformatics.Greginformatics.guiScreenHelper;
import static net.minecraft.block.Block.getBlockFromItem;

@Mod.EventBusSubscriber(modid = Tags.MODID)
@SideOnly(Side.CLIENT)
public class EventHandlers {

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onMetaPrefixItemTooltipRednerEvent(@NotNull RenderTooltipEvent.PostText event) {
        ItemStack stack = event.getStack();
        FluidStack fluidStack = null;
        if (stack.isEmpty()) {
            try {
                Object clicked = null;
                GuiScreen gui = Minecraft.getMinecraft().currentScreen;
                if (gui instanceof GuiContainer guiContainer) {
                    clicked = guiScreenHelper.getPluginsIngredientUnderMouse(guiContainer, event.getX() - 12, event.getY() + 12);
                } else if (gui instanceof RecipesGui recipesGui) {
                    clicked = recipesGui.getIngredientUnderMouse(event.getX() - 12, event.getY() + 12);
                }
                if (clicked instanceof ClickedIngredient<?>) {
                    fluidStack = ((ClickedIngredient<FluidStack>) clicked).getValue();
                }
            } catch (Exception ignored) {}
        } else if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
            IFluidHandlerItem fluidHandlerItem = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if (fluidHandlerItem != null) {
                fluidStack = fluidHandlerItem.drain(Integer.MAX_VALUE, false);
                stack = ItemStack.EMPTY;
            }
        }
        if (stack.isEmpty() && fluidStack == null) return;
        Material material = null;
        if (!stack.isEmpty()) {
            Item item = stack.getItem();
            if (item instanceof MetaPrefixItem metaItem) {
                material = metaItem.getMaterial(stack);
            }
        } else if (fluidStack != null ) {
            if (fluidStack.getFluid() instanceof GTFluid.GTMaterialFluid materialFluid) {
                material = materialFluid.getMaterial();
            }
        }
        if (material == null) return;
//        } else {
//            Block block = getBlockFromItem(item);
//            if (block instanceof BlockCompressed materialBlock) {
//                material = materialBlock.getGtMaterial(stack);
//            } else {
//                return;
//            }
//        }
        DepictionTooltip depictionTooltip = new DepictionTooltip(material);
        depictionTooltip.render(event.getX(), event.getY());
        return;
//        FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(stack.getTagCompound());
    }
}
