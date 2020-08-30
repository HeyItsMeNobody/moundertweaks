package mounderfod.moundertweaks.mixin.client;

import mounderfod.moundertweaks.MounderTweaksMain;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.*;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public abstract class ItemMixin {
    // "Improved" and rewritten Shnupbups code.

    @Inject(method = "appendTooltip(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/item/TooltipContext;)V", at = @At("HEAD"))
    public void appendTooltipInject(ItemStack stack, World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        if (Screen.hasShiftDown() && MounderTweaksMain.CONFIG.client.advancedToolTips) {
            if (stack.isDamageable()) {
                tooltip.add(new TranslatableText("moundertweaks.tooltip.max_durability", new LiteralText(String.valueOf(stack.getMaxDamage()))).formatted(Formatting.GRAY));
            }

            if ((Object) this instanceof ToolItem) {
                ToolItem toolItem = (ToolItem)(Object) this;
                ToolMaterial toolMaterial = toolItem.getMaterial();

                // Enchantability
                tooltip.add(new TranslatableText("moundertweaks.tooltip.enchantability", new LiteralText(String.valueOf(toolMaterial.getEnchantability()))).formatted(Formatting.GRAY));

                // Mining tool specific info
                if (toolItem instanceof MiningToolItem) {
                    tooltip.add(new TranslatableText("moundertweaks.tooltip.harvest_level", new LiteralText(String.valueOf(toolMaterial.getMiningLevel()))).formatted(Formatting.GRAY));
                    // Get the speed and apply efficiency to that.
                    int efficiency = EnchantmentHelper.get(stack).getOrDefault(Enchantments.EFFICIENCY, 0);
                    int efficiencyModifier = efficiency>0?(efficiency*efficiency)+1:0;
                    MutableText speedText = new TranslatableText("moundertweaks.tooltip.harvest_speed", new LiteralText(String.valueOf(toolMaterial.getMiningSpeedMultiplier()+efficiencyModifier))).formatted(Formatting.GRAY);
                    if(efficiency > 0) {
                        speedText.append(new LiteralText(" (+"+efficiencyModifier+")").formatted(Formatting.WHITE));
                    }
                    tooltip.add(speedText);
                }
            } else if ((Object) this instanceof ArmorItem) {
                ArmorItem armor = (ArmorItem)(Object)this;
                ArmorMaterial material = armor.getMaterial();
                tooltip.add(new TranslatableText("moundertweaks.tooltip.enchantability", new LiteralText(String.valueOf(material.getEnchantability()))).formatted(Formatting.GRAY));
            }
        } else if (stack.isDamageable() && MounderTweaksMain.CONFIG.client.advancedToolTips) {
            tooltip.add(new TranslatableText("moundertweaks.tooltip.press_shift").formatted(Formatting.GRAY));
        }
    }

}
