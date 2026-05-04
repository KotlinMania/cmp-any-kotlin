# cmp-any-kotlin — Agent Guidelines

Line-by-line Kotlin Multiplatform port of the `cmp_any` crate from
[facebookexperimental/gazebo](https://github.com/facebookexperimental/gazebo).
The crate provides type-erased equality (`EqAny`) and ordering (`OrdAny`) —
small wrappers that hold a value plus a captured comparison function so two
instances of the same erased type can be compared without exposing the
underlying type to the caller.

The upstream Rust source is **not tracked** in this repo — run
`./tools/fetch-rust-source.sh` once after cloning to populate
`tmp/cmp-any/`. That fetched tree is the only authority on what each function
should do; never edit it to make a port easier.

## General Porting Principles

### 1. Line-by-line transliteration

- Maintain file structure and organization from the Rust source.
- Translate functions in the same order they appear upstream.
- Preserve every comment, inline note, and safety/panic doc section —
  translate the language conventions to KDoc but keep the intent
  verbatim. Translate any Rust syntax inside doc strings (e.g. `Vec<T>`,
  `Option<&str>`, `Self::foo()`) to its Kotlin equivalent.
- **NO PORTING NOTES**: Do not add comments explaining Kotlin
  workarounds, "Rust vs Kotlin" rationale, or any other porting
  narratives to the source code.
- **NO RUST IN COMMENTS**: Never leave untranslated Rust code snippets
  or snake_case identifiers in the Kotlin KDocs.
- A missing function is preferable to a stub. If you can't translate
  something, leave the slot empty and track it explicitly rather than
  committing a fake implementation.

### 2. Provenance markers (REQUIRED)

Every ported `.kt` file must start with:

```kotlin
// port-lint: source src/<file>.rs
package io.github.kotlinmania.cmpany
```

The path on the `// port-lint:` line is relative to `tmp/cmp-any/`.

### 3. License

Upstream `gazebo` is dual-licensed Apache-2.0 OR MIT. This Kotlin port
preserves that.

### 4. No JVM-only APIs in shared code

KMP with native, JS, Wasm targets. No `kotlin.jvm.*`, `java.*`, or
`javax.*` from `commonMain`.

### 5. No `@Suppress`, no warnings-as-errors bypass — exception documented

`allWarningsAsErrors` is on. The general rule is "fix the cause, do not
silence with `@Suppress`". The one exception in this crate is
`OrdAny.new`'s captured comparator closure: it must perform
`@Suppress("UNCHECKED_CAST")` on the `(a as T).compareTo(b as T)`
operation, because Kotlin's type system cannot encode the runtime
invariant "both operands have the same `KClass<*>`, therefore safe".
The cast is paired with a runtime `typeId == other.typeId` check that
makes it equivalent to Rust's `unsafe` block in upstream `OrdAny::new`.
Document this at the call site; never extend the Suppress to other
files.

### 6. mod.rs / lib.rs → no Mod.kt / Lib.kt

`lib.rs` is pure reexport glue; drop it and rewire callers to import
the canonical defining package (`io.github.kotlinmania.cmpany`)
directly.

## What this crate exposes

From `src/lib.rs`:

- `pub use eq::EqAny;`
- `pub use ord::OrdAny;`

From `src/eq.rs`:

- `EqAny<'a>` — type-erased equality. `EqAny::new(a)` boxes any `T:
  PartialEq + 'static`; two `EqAny` values are equal iff their underlying
  types match and the underlying `PartialEq::eq` returns true.

From `src/ord.rs`:

- `OrdAny<'a>` — type-erased ordering. `OrdAny::new(a)` boxes any `T: Ord
  + 'static`. Two `OrdAny` values are compared by `TypeId` first, then by
  the underlying `Ord::cmp`.

The Kotlin port substitutes `KClass<*>` for Rust's `TypeId` and
`Comparable<T>`/`equals` for Rust's `Ord`/`PartialEq`. Ordering between
distinct types uses `KClass<*>.qualifiedName.compareTo` (deterministic
across runs) instead of Rust's hash-based `TypeId.cmp` (implementation-
defined). Document this in the file.

## Verification

```bash
./gradlew compileKotlinMacosArm64
./gradlew macosArm64Test
./tools/ast_distance/ast_distance --compare-functions \
    tmp/cmp-any/src/ord.rs rust \
    src/commonMain/kotlin/io/github/kotlinmania/cmpany/OrdAny.kt kotlin
```

## Commit Messages

- No AI branding, no Co-Authored-By
- Clear, descriptive
- No emoji
