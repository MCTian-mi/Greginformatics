package greginformatics;

import com.google.common.base.Splitter;
import gregtech.api.util.LocalizationUtils;
import gregtech.api.util.Size;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.resource.IResourceType;
import net.minecraftforge.client.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.client.resource.VanillaResourceType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.depict.DepictionGenerator;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmilesParser;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Predicate;

@SideOnly(Side.CLIENT)
public class DepictGenerator implements ISelectiveResourceReloadListener {

    private static final SmilesParser sp = new SmilesParser(new DefaultChemObjectBuilder());
    private static final DepictionGenerator dg = new DepictionGenerator().withAtomColors(atom -> Color.white)
            .withBackgroundColor(new Color(0, 0, 0, 0))
            .withMargin(8)
//            .withParam(StandardGenerator.StrokeRatio.class, 2.0)
            .withZoom(4.0);
    private static final Splitter SPLITTER = Splitter.on('=').limit(2);
    public static HashMap<String, Integer> textureMap;
    public static HashMap<String, Size> sizeMap;

    public DepictGenerator() {
    }

    public static void generate() {
        Greginformatics.LOGGER.info("Generating Depictions");
        textureMap = new HashMap<>();
        sizeMap = new HashMap<>();
        IResourceManager resourceManager = Minecraft.getMinecraft().getResourceManager();
        IResource langFile;
        try {
            langFile = resourceManager.getResource(new ResourceLocation(Tags.MODID, "lang/en_us.lang"));
        } catch (IOException e) {
            Greginformatics.LOGGER.error("Failed to load lang file!");
//            e.printStackTrace();
            return;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(langFile.getInputStream(), StandardCharsets.UTF_8));
        br.lines().forEach(line -> {
            if (line.isEmpty() || line.charAt(0) == '#') {
                return;
            }
            Iterator<String> x = SPLITTER.split(line).iterator();
            String key = x.next();
//            String value = x.next();
            IAtomContainer molecule = null;
            String name = null;


            if (key.endsWith(".smiles")) {
                molecule = fromSMILES(key);
                name = key.replace(".smiles", "");
            }


            // TODO: other formats


            if (molecule == null) {
                return;
            }
            try {
                BufferedImage img = dg.depict(molecule).toImg();

                int glTextureId = TextureUtil.glGenTextures();
                TextureUtil.uploadTextureImageAllocate(glTextureId, img, false, false);
                textureMap.put(name, glTextureId);
                sizeMap.put(name, new Size(img.getWidth(), img.getHeight()));

//                DynamicTexture dynamicTexture = new DynamicTexture(img);
//                ResourceLocation location = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(name + "_depict", dynamicTexture);
//                Minecraft.getMinecraft().getTextureManager().bindTexture(location);
            } catch (Exception e) {
                Greginformatics.LOGGER.error("Failed to generate depiction for name: " + name);
//                e.printStackTrace();
            }
        });
    }

    private static IAtomContainer fromSMILES(String key) {
        String SMILES = LocalizationUtils.format(key);
        try {
            return sp.parseSmiles(SMILES);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Override
    public void onResourceManagerReload(@NotNull IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
        if (resourcePredicate.test(VanillaResourceType.LANGUAGES)) {
            generate();
        }
    }
}
