package com.kjmaster.kjlib.tileentity;

import com.kjmaster.kjlib.blockcommands.IRunnable;
import com.kjmaster.kjlib.blockcommands.IRunnableWithList;
import com.kjmaster.kjlib.blockcommands.IRunnableWithListResult;
import com.kjmaster.kjlib.blockcommands.IRunnableWithResult;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class AnnotationHolder {

    // This is static but that is not a big problem since when a new world loads this remains valid
    static final Map<BlockEntityType, AnnotationHolder> annotations = new HashMap<>();

    final Map<String, IRunnable<?>> serverCommands = new HashMap<>();
    final Map<String, IRunnableWithResult> serverCommandsWithResult = new HashMap<String, com.kjmaster.kjlib.blockcommands.IRunnableWithResult>();
    final Map<String, IRunnable> clientCommands = new HashMap<String, com.kjmaster.kjlib.blockcommands.IRunnable>();
    final Map<String, IRunnableWithListResult> serverCommandsWithListResult = new HashMap<String, com.kjmaster.kjlib.blockcommands.IRunnableWithListResult>();
    final Map<String, IRunnableWithList> clientCommandsWithList = new HashMap<String, com.kjmaster.kjlib.blockcommands.IRunnableWithList>();
    final Map<String, ValueHolder<?, ?>> valueMap = new HashMap<>();
    final List<Pair<Field, Cap>> capabilityList = new ArrayList<>();
}
