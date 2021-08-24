package net.onvoid.adjunct.common.tile;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.onvoid.adjunct.Adjunct;
import net.onvoid.adjunct.common.block.AdjunctBlocks;
import net.onvoid.adjunct.compat.quark.AdjunctQuarkBlocks;
import net.onvoid.adjunct.compat.quark.AdjunctVariantChestTileEntity;
import net.onvoid.adjunct.compat.quark.AdjunctVariantTrappedChestTileEntity;

public class AdjunctTiles {

    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Adjunct.MODID);
    public static final RegistryObject<TileEntityType<PizzaStationTile>> PIZZA_STATION_TILE = TILES.register("pizza_station", () -> TileEntityType.Builder.of(PizzaStationTile::new, AdjunctBlocks.PIZZA_STATION.get()).build(null));
    public static final RegistryObject<TileEntityType<PizzaOvenTile>> PIZZA_OVEN_TILE = TILES.register("pizza_oven", () -> TileEntityType.Builder.of(PizzaOvenTile::new, AdjunctBlocks.PIZZA_OVEN.get()).build(null));
    public static final RegistryObject<TileEntityType<OliveSignTileEntity>> OLIVE_SIGN = TILES.register("olive_sign", () -> TileEntityType.Builder.of(OliveSignTileEntity::new, AdjunctBlocks.OLIVE_WALL_SIGN.get(), AdjunctBlocks.OLIVE_SIGN.get()).build(null));
    public static final RegistryObject<TileEntityType<AdjunctVariantChestTileEntity>> OLIVE_CHEST = TILES.register("olive_chest", () -> TileEntityType.Builder.of(AdjunctVariantChestTileEntity::new, AdjunctQuarkBlocks.OLIVE_CHEST.get()).build(null));
    public static final RegistryObject<TileEntityType<AdjunctVariantTrappedChestTileEntity>> OLIVE_TRAPPED_CHEST = TILES.register("olive_trapped_chest", () -> TileEntityType.Builder.of(AdjunctVariantTrappedChestTileEntity::new, AdjunctQuarkBlocks.OLIVE_TRAPPED_CHEST.get()).build(null));

    public static void create(){
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
