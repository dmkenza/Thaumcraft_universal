package com.kenza.thaumcraft.datagen.sound;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.Identifier;

import java.nio.file.Path;



public class KPathResolver {
    private final Path rootPath;
    private final String directoryName;

    public KPathResolver(Path rootPath, String directoryName) {
        this.rootPath = rootPath;
        this.directoryName = directoryName;
    }

    public Path resolveFile(Identifier id) {
        Path var10000 = this.rootPath.resolve(id.getNamespace());
        String var10001 = id.getPath();
        return var10000.resolve(var10001);
    }
//    public Path resolve(Identifier id, String fileExtension) {
//        Path var10000 = this.rootPath.resolve(id.getNamespace()).resolve(this.directoryName);
//        String var10001 = id.getPath();
//        return var10000.resolve(var10001 + "." + fileExtension);
//    }
//
//    public Path resolveJson(Identifier id) {
//        return this.rootPath.resolve(id.getNamespace()).resolve(this.directoryName).resolve(id.getPath() + ".json");
//    }

//    public static enum OutputType {
//        DATA_PACK("data"),
//        RESOURCE_PACK("assets"),
//        REPORTS("reports");
//
//        final String path;
//
//        private OutputType(String path) {
//            this.path = path;
//        }
//    }
}