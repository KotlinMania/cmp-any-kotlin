# gazebo-kotlin

Kotlin Multiplatform primitives based on
[Gazebo](https://github.com/facebookincubator/gazebo).

The first ported subtree is `cmp_any`, which provides `PartialEqAny` and
`OrdAny` for type-erased equality and ordering. It is used by
`starlark-kotlin` where upstream Rust depends on `cmp_any`.

## Status

Initial release of the `cmp_any` subtree.

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
