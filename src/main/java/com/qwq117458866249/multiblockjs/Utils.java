package com.qwq117458866249.multiblockjs;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.qwq117458866249.multiblockjs.custom.block.MultiblockPartBE;
import com.qwq117458866249.multiblockjs.custom.block.MultiblockPartBlock;
import com.qwq117458866249.multiblockjs.custom.block.partblock.PartBlockEntity;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.util.TriState;

import javax.annotation.Nullable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Utils {
    public static BlockPos getRelativePos(BlockPos pos, int x, int y, int z){
        return new BlockPos(
                pos.getX() + x,
                pos.getY() + y,
                pos.getZ() + z
        );
    }

    public static int[] getDirectionPos(int[] pPos, Direction pDirection){
        switch (pDirection){
            case SOUTH -> {
                return new int[]{-pPos[0],pPos[1],-pPos[2]};
            }
            case WEST -> {
                return new int[]{pPos[2],pPos[1],-pPos[0]};
            }
            case EAST -> {
                return new int[]{-pPos[2],pPos[1],pPos[0]};
            }
            case null, default -> {
                return pPos;
            }
        }
    }

    public static void formBlock(BlockPos pPos, BlockPos pControllerPos,Level pLevel){
        if (pLevel.getBlockState(pPos).getBlock() instanceof MultiblockPartBlock vPartBlock){
            vPartBlock.form(pLevel.getBlockState(pPos),pLevel,pPos);
            if (pLevel.getBlockEntity(pPos) instanceof MultiblockPartBE vPartBE){
                vPartBE.setControllerPos(pControllerPos);
            }
        } else {
            PartBlockEntity.formPartEntity(pPos, pLevel);
            if (pLevel.getBlockEntity(pPos) instanceof MultiblockPartBE vPartBE){
                vPartBE.setControllerPos(pControllerPos);
            } else {
                MultiblockJS.LOGGER.error("We DIDN'T form multiblock successfully");
            }
        }
    }

    public static void unFormBlock(BlockPos pPos, Level pLevel){
        if (pLevel.getBlockState(pPos).getBlock() instanceof MultiblockPartBlock vPartBlock){
            vPartBlock.unForm(pLevel.getBlockState(pPos),pLevel,pPos);
        }
        if (pLevel.getBlockEntity(pPos) instanceof MultiblockPartBE vPartBlock){
            vPartBlock.unFormEntity();
        }
    }

    public static void writeToGameDir(String subPath, String content) throws Exception {
        Path gameDir = FMLPaths.GAMEDIR.get();
        Path targetPath = gameDir.resolve(subPath);
        Files.createDirectories(targetPath.getParent());
        Files.writeString(targetPath, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static String blockStateToString(BlockState pState) {
        String vTemp = BuiltInRegistries.BLOCK.wrapAsHolder(pState.getBlock()).getRegisteredName();
        boolean canReturn = false;
        for (int i =0;i < pState.toString().length();i++){
            if (pState.toString().charAt(i) == '['){
                canReturn = true;
            }
            if (canReturn){
                vTemp = vTemp + pState.toString().charAt(i);
            }
        }
        return vTemp;
    }

    public static BlockState stringToBlockState(String pString) {
        try {
            BlockStateParser.BlockResult result = BlockStateParser.parseForBlock(
                    BuiltInRegistries.BLOCK.asLookup(),
                    pString,
                    true
            );
            return result.blockState();
        } catch (CommandSyntaxException ignored) {}
        return Blocks.AIR.defaultBlockState();
    }
}
