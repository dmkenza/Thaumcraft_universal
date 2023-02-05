package com.kenza.thaumcraft.datagen.sound;


import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kenza.thaumcraft.datagen.sound.KPathResolver;
import net.minecraft.data.DataGenerator;
import net.minecraft.resource.*;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.resource.VanillaDataPackProvider.DEFAULT_PACK_METADATA;

public class ExistingFileHelper {


    //    static {
//        PackResourceMetadata DEFAULT_PACK_METADATA = new PackResourceMetadata(Text.translatable("resourcePack.vanilla.description"), net.minecraft.resource.ResourceType.CLIENT_RESOURCES.getPackVersion(SharedConstants.getGameVersion()));
//        LOGGER = LogUtils.getLogger();
//        SHA1_PATTERN = Pattern.compile("^[a-fA-F0-9]{40}$");
//        APPLYING_PACK_TEXT = Text.translatable("multiplayer.applyingPack");
//    }
    public File musicJson;
    private final LifecycledResourceManagerImpl clientResources;
    private final LifecycledResourceManagerImpl serverData;
    private final boolean enable;
    private KPathResolver soundsPathResolver;
    private final Multimap<net.minecraft.resource.ResourceType, Identifier> generated = HashMultimap.create();

    //    public ExistingFileHelper(Collection<Path> existingPacks, Set<String> existingMods, boolean enable, @Nullable String assetIndex, @Nullable File assetsDir) {
    public ExistingFileHelper(KPathResolver soundsPathResolver, @Nullable String modId, @Nullable File assetsDir) {
        this.soundsPathResolver = soundsPathResolver;
//        musicJson = new File("C:\\projects_mc\\work\\Thaumcraft_universal\\common\\src\\main\\resources\\assets\\" + modId);

        List<ResourcePack> candidateClientResources = new ArrayList();
        List<ResourcePack> candidateServerResources = new ArrayList();
        candidateClientResources.add(new DefaultResourcePack(DEFAULT_PACK_METADATA, new String[]{"minecraft", "realms", "thaumcraft"}));
        if (modId != null && assetsDir != null) {
//            candidateClientResources.add(new DefaultClientResourcePack(DEFAULT_PACK_METADATA, new ResourceIndex(assetsDir, assetIndex)));
        }

        candidateServerResources.add(new DefaultResourcePack(DEFAULT_PACK_METADATA, new String[]{"minecraft", "thaumcraft"}));
//        Iterator var8 = existingPacks.iterator();
//
//        while(var8.hasNext()) {
//            Path existing = (Path)var8.next();
//            File file = existing.toFile();
//            ResourcePack pack = file.isDirectory() ? new DirectoryResourcePack(file) : new ZipResourcePack(file);
//            candidateClientResources.add(pack);
//            candidateServerResources.add(pack);
//        }
//
//        var8 = existingMods.iterator();

//        while(var8.hasNext()) {
//            String existingMod = (String)var8.next();
//            IModFileInfo modFileInfo = ModList.get().getModFileById(existingMod);
//            if (modFileInfo != null) {
//                ResourcePack pack = ResourcePackLoader.createPackForMod(modFileInfo);
//                candidateClientResources.add(pack);
//                candidateServerResources.add(pack);
//            }
//        }

        this.clientResources = new LifecycledResourceManagerImpl(net.minecraft.resource.ResourceType.CLIENT_RESOURCES, candidateClientResources);
        this.serverData = new LifecycledResourceManagerImpl(net.minecraft.resource.ResourceType.SERVER_DATA, candidateServerResources);
        this.enable = true;
    }

    private ResourceManager getManager(net.minecraft.resource.ResourceType packType) {
        return packType == net.minecraft.resource.ResourceType.CLIENT_RESOURCES ? this.clientResources : this.serverData;
    }

    private Identifier getLocation(Identifier base, String suffix, String prefix) {
        return new Identifier(base.getNamespace(), prefix + "/" + base.getPath() + suffix);
    }

    public boolean exists(Identifier loc, net.minecraft.resource.ResourceType packType) {
        if (!this.enable) {
            return true;
        } else {

            return this.generated.get(packType).contains(loc) || soundsPathResolver.resolveFile(loc).toFile().exists() || this.getManager(packType).getResource(loc).isPresent();
        }
    }

    public boolean exists(Identifier loc, IResourceType type) {
        return this.exists(this.getLocation(loc, type.getSuffix(), type.getPrefix()), type.getPackType());
    }

    public boolean exists(Identifier loc, net.minecraft.resource.ResourceType packType, String pathSuffix, String pathPrefix) {
        return this.exists(this.getLocation(loc, pathSuffix, pathPrefix), packType);
    }

    public void trackGenerated(Identifier loc, IResourceType type) {
        this.generated.put(type.getPackType(), this.getLocation(loc, type.getSuffix(), type.getPrefix()));
    }

    public void trackGenerated(Identifier loc, net.minecraft.resource.ResourceType packType, String pathSuffix, String pathPrefix) {
        this.generated.put(packType, this.getLocation(loc, pathSuffix, pathPrefix));
    }

    @VisibleForTesting
    public Resource getResource(Identifier loc, net.minecraft.resource.ResourceType packType, String pathSuffix, String pathPrefix) throws IOException {
        return this.getResource(this.getLocation(loc, pathSuffix, pathPrefix), packType);
    }

    @VisibleForTesting
    public Resource getResource(Identifier loc, net.minecraft.resource.ResourceType packType) throws IOException {
        return (Resource) this.getManager(packType).getResource(loc).orElseThrow();
    }

    public boolean isEnabled() {
        return this.enable;
    }

    public interface IResourceType {
        net.minecraft.resource.ResourceType getPackType();

        String getSuffix();

        String getPrefix();
    }

    public static class ResourceType implements IResourceType {
        final net.minecraft.resource.ResourceType packType;
        final String suffix;
        final String prefix;

        public ResourceType(net.minecraft.resource.ResourceType type, String suffix, String prefix) {
            this.packType = type;
            this.suffix = suffix;
            this.prefix = prefix;
        }

        public net.minecraft.resource.ResourceType getPackType() {
            return this.packType;
        }

        public String getSuffix() {
            return this.suffix;
        }

        public String getPrefix() {
            return this.prefix;
        }
    }
}
