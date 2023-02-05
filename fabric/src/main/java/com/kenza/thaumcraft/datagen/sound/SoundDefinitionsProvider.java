package com.kenza.thaumcraft.datagen.sound;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.resource.ResourceType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.kenza.support.utils.reg.Ref.SOUNDS_EVENTS;

public abstract class SoundDefinitionsProvider implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private final DataGenerator generator;
    private final String modId;
    private final ExistingFileHelper helper;
    private final Map<String, SoundDefinition> sounds = new LinkedHashMap();
    public KPathResolver soundsPathResolver;

    protected SoundDefinitionsProvider(KPathResolver soundsPathResolver, DataGenerator generator, String modId) {
        this.soundsPathResolver = soundsPathResolver;
        this.generator = generator;
        this.modId = modId;
        this.helper = new ExistingFileHelper( soundsPathResolver,  this.modId,  this.generator.getOutput().resolve("assets/" + this.modId + "/sounds.json" ).toFile());
    }

    public abstract void registerSounds();

    public void run(DataWriter writer) throws IOException {
        this.sounds.clear();
        this.registerSounds();
        this.validate();
        if (!this.sounds.isEmpty()) {
            this.save(writer, this.generator.getOutput().resolve("assets/" + this.modId + "/sounds.json"));
        }

    }

    public String getName() {
        return "Sound Definitions";
    }

    protected static SoundDefinition definition() {
        return SoundDefinition.definition();
    }

    protected static SoundDefinition.Sound sound(Identifier name, SoundDefinition.SoundType type) {
        return SoundDefinition.Sound.sound(name, type);
    }

    protected static SoundDefinition.Sound sound(Identifier name) {
        return sound(name, SoundDefinition.SoundType.SOUND);
    }

    protected static SoundDefinition.Sound sound(String name, SoundDefinition.SoundType type) {
        return sound(new Identifier(name), type);
    }

    protected static SoundDefinition.Sound sound(String name) {
        return sound(new Identifier(name));
    }

    protected void add(Supplier<SoundEvent> soundEvent, SoundDefinition definition) {
        this.add((SoundEvent)soundEvent.get(), definition);
    }

    protected void add(SoundEvent soundEvent, SoundDefinition definition) {
        this.add(soundEvent.getId(), definition);
    }

    protected void add(Identifier soundEvent, SoundDefinition definition) {
        this.addSounds(soundEvent.getPath(), definition);
    }

    protected void add(String soundEvent, SoundDefinition definition) {
        this.add(new Identifier(soundEvent), definition);
    }

    private void addSounds(String soundEvent, SoundDefinition definition) {
        if (this.sounds.put(soundEvent, definition) != null) {
            throw new IllegalStateException("Sound event '" + this.modId + ":" + soundEvent + "' already exists");
        }
    }

    private void validate() {
        List<String> notValid = (List)this.sounds.entrySet().stream().filter((it) -> {
            return !this.validate((String)it.getKey(), (SoundDefinition)it.getValue());
        }).map(Map.Entry::getKey).map((it) -> {
            return this.modId + ":" + it;
        }).collect(Collectors.toList());
        if (!notValid.isEmpty()) {
            throw new IllegalStateException("Found invalid sound events: " + notValid);
        }
    }

    private boolean validate(String name, SoundDefinition def) {
        return def.soundList().stream().allMatch((it) -> {
            return this.validate(name, it);
        });
    }

    private boolean validate(String name, SoundDefinition.Sound sound) {
        switch (sound.type()) {
            case SOUND:
                return this.validateSound(name, sound.name());
            case EVENT:
                return this.validateEvent(name, sound.name());
            default:
                Identifier var10002 = sound.name();
                throw new IllegalArgumentException("The given sound '" + var10002 + "' does not have a valid type: expected either SOUND or EVENT, but found " + sound.type());
        }
    }

    private boolean validateSound(String soundName, Identifier name) {
        boolean valid = this.helper.exists(name, ResourceType.CLIENT_RESOURCES, ".ogg", "sounds");
        if (!valid) {
            String var10000 = name.getNamespace();
            String path = var10000 + ":sounds/" + name.getPath() + ".ogg";
            LOGGER.warn("Unable to find corresponding OGG file '{}' for sound event '{}'", path, soundName);
        }

        return valid;
    }

    private boolean validateEvent(String soundName, Identifier name) {
        boolean valid = this.sounds.containsKey(soundName) || SOUNDS_EVENTS.getRegistrar().contains(name);
        if (!valid) {
            LOGGER.warn("Unable to find event '{}' referenced from '{}'", name, soundName);
        }

        return valid;
    }

    private void save(DataWriter cache, Path targetFile) throws IOException {
        DataProvider.writeToPath(cache, this.mapToJson(this.sounds), targetFile);
    }

    private JsonObject mapToJson(Map<String, SoundDefinition> map) {
        JsonObject obj = new JsonObject();
        map.forEach((k, v) -> {
            obj.add(k, v.serialize());
        });
        return obj;
    }
}
