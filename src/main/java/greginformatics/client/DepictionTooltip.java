package greginformatics.client;

import greginformatics.client.render.renderer.SkeletalFormulaRenderer;
import gregtech.api.unification.material.Material;
import gregtech.api.util.LocalizationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.depict.DepictionGenerator;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.smiles.SmilesParser;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DepictionTooltip {

    private int w;
    private int h;

    private ResourceLocation location;

    public DepictionTooltip(int w, int h, ResourceLocation location) {
        this.w = w;
        this.h = h;
        this.location = location;
    }

    public DepictionTooltip(Material material) {
        location = getOrCreateResourceLocationFromMaterial(material);
    }

    public void render(int x, int y) {
        float scale = 0.1F; // TODO
        SkeletalFormulaRenderer.render(x, (int) (y - h * scale - 8), this.w, this.h, this.location);    // TODO
    }

    public ResourceLocation getOrCreateResourceLocationFromMaterial(Material material) {

        String name = material.getUnlocalizedName() + "_depict";
        ResourceLocation location = new ResourceLocation(name);
        if (false) {// TODO: Check if alr exists
            return location;
        }

        // Create!
        String key = material.getUnlocalizedName();
        IAtomContainer molecule = null;
        if (LocalizationUtils.hasKey(key + ".inchi")) {
            return null;
            // TODO
        } else if (LocalizationUtils.hasKey(key + ".smiles")) {
            String SMILES = LocalizationUtils.format(key + ".smiles");
            SmilesParser smilesParser = new SmilesParser(new DefaultChemObjectBuilder());
            try {
                molecule = smilesParser.parseSmiles(SMILES);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;    // TODO
        }

        try {
            StructureDiagramGenerator sdg = new StructureDiagramGenerator();
            sdg.setMolecule(molecule);
            sdg.generateCoordinates();
            DepictionGenerator dg = new DepictionGenerator()
                    .withAtomColors(atom -> Color.white)
                    .withBackgroundColor(new Color(0, 0, 0, 0))
                    .withMargin(8)
                    .withZoom(4.0);
            BufferedImage img = dg.depict(molecule).toImg();

            this.w = img.getWidth();
            this.h = img.getHeight();

            DynamicTexture dynamicTexture = new DynamicTexture(img);
            location = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(name, dynamicTexture);
            Minecraft.getMinecraft().getTextureManager().bindTexture(location);

            return location;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
