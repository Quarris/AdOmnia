package dev.quarris.adomnia.mixins;

import dev.quarris.adomnia.modules.impl.GrowModule;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.extensions.IForgeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements IForgeEntity {

    @Inject(method = "setShiftKeyDown", at = @At("HEAD"))
    private void crouchHandler(boolean isShifting, CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        if (entity instanceof Player && isShifting) {
            GrowModule.INSTANCE.onCrouch(entity.getLevel(), entity.blockPosition(), (Player) entity);
        }
    }

}
