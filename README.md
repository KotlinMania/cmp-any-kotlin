# cmp-any-kotlin

Kotlin Multiplatform port of [facebookexperimental/gazebo](https://github.com/facebookexperimental/gazebo)'s
`cmp_any` crate. Provides `EqAny` (type-erased equality) and `OrdAny`
(type-erased ordering) — small wrappers that hold a value plus a captured
comparison function so two instances of the same erased type can be compared
without exposing the underlying type to callers.

Used by `starlark-kotlin`'s `TyCustom::cmpToken` to express upstream
`gazebo::cmp_any::OrdAny`.

## Status

Skeleton + initial port of `OrdAny` and `EqAny`. The upstream Rust source is
the oracle; see [`AGENTS.md`](./AGENTS.md) and [`CLAUDE.md`](./CLAUDE.md) for
porting rules.

## Setup

```bash
./tools/fetch-rust-source.sh
```

That populates `tmp/cmp-any/`.

## Build

```bash
./gradlew compileKotlinMacosArm64
./gradlew macosArm64Test
```

## Coordinates

```kotlin
implementation("io.github.kotlinmania:cmp-any-kotlin:0.1.0")
```

## License

Dual-licensed Apache-2.0 OR MIT, mirroring upstream gazebo.
