package fuzs.spikyspikes.common.data;

import fuzs.puzzleslib.common.api.data.v3.recipes.AbstractRecipeProvider;
import fuzs.spikyspikes.common.init.ModRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.TransmuteRecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public class ModRecipeProvider extends AbstractRecipeProvider {

    public ModRecipeProvider(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
    }

    @Override
    public void buildRecipes() {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.DECORATIONS, ModRegistry.WOODEN_SPIKE_ITEM.value(), 3)
                .define('S', Items.WOODEN_SWORD)
                .define('#', ItemTags.LOGS_THAT_BURN)
                .pattern(" S ")
                .pattern("S#S")
                .pattern("###")
                .unlockedBy(getHasName(Items.WOODEN_SWORD), this.has(Items.WOODEN_SWORD))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.DECORATIONS, ModRegistry.STONE_SPIKE_ITEM.value(), 3)
                .define('S', Items.STONE_SWORD)
                .define('#', Items.SMOOTH_STONE)
                .pattern(" S ")
                .pattern("S#S")
                .pattern("###")
                .unlockedBy(getHasName(Items.STONE_SWORD), this.has(Items.STONE_SWORD))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.DECORATIONS, ModRegistry.IRON_SPIKE_ITEM.value(), 3)
                .define('S', Items.IRON_SWORD)
                .define('#', Items.IRON_INGOT)
                .define('@', Items.IRON_BLOCK)
                .pattern(" S ")
                .pattern("S#S")
                .pattern("#@#")
                .unlockedBy(getHasName(Items.IRON_SWORD), this.has(Items.IRON_SWORD))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.DECORATIONS, ModRegistry.GOLDEN_SPIKE_ITEM.value(), 3)
                .define('S', Items.GOLDEN_SWORD)
                .define('#', Items.GOLD_INGOT)
                .define('@', Items.GOLD_BLOCK)
                .pattern(" S ")
                .pattern("S#S")
                .pattern("#@#")
                .unlockedBy(getHasName(Items.GOLDEN_SWORD), this.has(Items.GOLDEN_SWORD))
                .save(this.output);
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.DECORATIONS, ModRegistry.DIAMOND_SPIKE_ITEM.value(), 3)
                .define('S', Items.DIAMOND_SWORD)
                .define('#', Items.DIAMOND)
                .define('@', Items.DIAMOND_BLOCK)
                .pattern(" S ")
                .pattern("S#S")
                .pattern("#@#")
                .unlockedBy(getHasName(Items.DIAMOND_SWORD), this.has(Items.DIAMOND_SWORD))
                .save(this.output);
        TransmuteRecipeBuilder.transmute(RecipeCategory.DECORATIONS,
                        Ingredient.of(ModRegistry.DIAMOND_SPIKE_ITEM.value()),
                        Ingredient.of(Items.NETHERITE_INGOT),
                        ModRegistry.NETHERITE_SPIKE_ITEM.value())
                .unlockedBy(getHasName(Items.NETHERITE_INGOT), this.has(Items.NETHERITE_INGOT))
                .save(this.output);
    }
}
