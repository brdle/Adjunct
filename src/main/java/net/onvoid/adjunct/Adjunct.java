package net.onvoid.adjunct;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.onvoid.adjunct.handlers.EventHandler;
import net.onvoid.adjunct.handlers.WorldGenHandler;
import net.onvoid.adjunct.proxy.ClientProxy;
import net.onvoid.adjunct.proxy.CommonProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("adjunct")
public class Adjunct
{
    public static final String MODID = "adjunct";

    public static Adjunct instance;
    public static CommonProxy proxy;

    public static final Logger LOGGER = LogManager.getLogger();

    public Adjunct() {
        instance = this;

        proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
        proxy.start();
    }
}