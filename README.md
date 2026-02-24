# ArchitectsMarvelNeo

NeoForge mod project targeting Minecraft `1.21.1` with NeoForge `21.1.214`.

Mod id: `architectsmarvel_port`

## What is included

- NeoForge 1.21.1 project scaffold with Java 21 toolchain.
- Block/item/creative-tab registration for an initial architect-style block set:
  - `marble`, `polished_marble`, `marble_bricks`, `cracked_marble_bricks`, `chiseled_marble`
  - `marble_stairs`, `marble_slab`, `marble_wall`
  - `marble_brick_stairs`, `marble_brick_slab`, `marble_brick_wall`
  - `basalt_tiles`, `basalt_tile_stairs`, `basalt_tile_slab`, `basalt_tile_wall`
- Data generation providers for:
  - blockstates
  - block/item models
  - loot tables
  - block/item tags
  - recipes (including stonecutter recipes)
  - language entries (`en_us`)
- Placeholder block textures for all base textures referenced by models.
- `scripts/import_upstream.sh` for best-effort asset import from upstream when network is available.

## Upstream port status (ArchitectsMarvel)

Attempted to clone:

`https://github.com/RAY-255/ArchitectsMarvel`

Current environment could not resolve `github.com`, so direct upstream copy/port was blocked in this run.

If you run this on a machine with internet access:

```bash
cd ArchitectsMarvelNeo
./scripts/import_upstream.sh
```

Then adapt/expand `ModBlocks` and datagen providers to exactly mirror upstream definitions.

## Build and run

```bash
cd ArchitectsMarvelNeo
./gradlew build
./gradlew runData
```

## Notes

- This workspace did not contain `gradle` and did not have `gradle-wrapper.jar` available locally.
- `gradlew` scripts and wrapper properties are included, but you must provide `gradle/wrapper/gradle-wrapper.jar` (or run `gradle wrapper` on a machine with Gradle installed) before running Gradle tasks.
