# gazebo-kotlin

Kotlin Multiplatform port home for primitives from
[facebookincubator/gazebo](https://github.com/facebookincubator/gazebo).

The first ported subtree is `cmp_any`, which provides `PartialEqAny` and
`OrdAny` for type-erased equality and ordering. It is used by
`starlark-kotlin` where upstream Rust depends on `cmp_any`.

## Status

Initial port of the `cmp_any` subtree. The upstream Rust source is the oracle;
see [AGENTS.md](./AGENTS.md) and [CLAUDE.md](./CLAUDE.md) for porting rules.

## Setup

```bash
./tools/fetch-rust-source.sh
```

That populates `tmp/gazebo/cmp_any/`.

## Build

```bash
./gradlew compileKotlinMacosArm64
./gradlew macosArm64Test
```

## Coordinates

```kotlin
implementation("io.github.kotlinmania:gazebo-kotlin:0.1.0")
```

## License

Dual-licensed Apache-2.0 OR MIT, mirroring upstream Gazebo.
