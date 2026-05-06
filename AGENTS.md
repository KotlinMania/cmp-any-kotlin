# gazebo-kotlin — Agent Guidelines

Line-by-line Kotlin Multiplatform port home for primitives from
[facebookincubator/gazebo](https://github.com/facebookincubator/gazebo).

The first ported subtree is `cmp_any`. It provides type-erased equality
(`PartialEqAny`) and ordering (`OrdAny`) — small wrappers that hold a value plus
a captured comparison function so two instances of the same erased type can be
compared without exposing the underlying type to the caller.

The upstream Rust source is **not tracked** in this repo — run
`./tools/fetch-rust-source.sh` once after cloning to populate
`tmp/gazebo/cmp_any/`. That fetched tree is the only authority on what each
function should do; never edit it to make a port easier.

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
// port-lint: source cmp_any/src/<file>.rs
package io.github.kotlinmania.gazebo.cmpany
```

The path on the `// port-lint:` line is relative to `tmp/gazebo/`.

### 3. License

Upstream Gazebo is dual-licensed Apache-2.0 OR MIT. This Kotlin port preserves
that.

### 4. No JVM-only APIs in shared code

KMP with native, JS, Wasm targets. No `kotlin.jvm.*`, `java.*`, or `javax.*`
from `commonMain`.

### 5. No `@Suppress`, no warnings-as-errors bypass — exception documented

`allWarningsAsErrors` is on. The general rule is "fix the cause, do not silence
with `@Suppress`". The one exception in this subtree is `OrdAny.new`'s captured
comparator closure: it must perform `@Suppress("UNCHECKED_CAST")` on the
`(a as T).compareTo(b as T)` operation, because Kotlin's type system cannot
encode the runtime invariant "both operands have the same `KClass<*>`,
therefore safe". The cast is paired with a runtime type check that makes it
equivalent to Rust's `unsafe` block in upstream `OrdAny::new`. Document this at
the call site; never extend the suppress to other files.

### 6. Reexports

`cmp_any/src/lib.rs` is pure reexport glue; drop it and rewire callers to import
the canonical defining package (`io.github.kotlinmania.gazebo.cmpany`) directly.

## What the first subtree exposes

From `cmp_any/src/lib.rs`:

- `pub use eq::PartialEqAny;`
- `pub use ord::OrdAny;`

From `cmp_any/src/eq.rs`:

- `PartialEqAny<'a>` — type-erased equality.

From `cmp_any/src/ord.rs`:

- `OrdAny<'a>` — type-erased ordering.

The Kotlin port substitutes `KClass<*>` for Rust's `TypeId` and
`Comparable<T>`/`equals` for Rust's `Ord`/`PartialEq`.

## Verification

```bash
./gradlew compileKotlinMacosArm64
./gradlew macosArm64Test
./tools/ast_distance/ast_distance --compare-functions \
    tmp/gazebo/cmp_any/src/ord.rs rust \
    src/commonMain/kotlin/io/github/kotlinmania/gazebo/cmpany/OrdAny.kt kotlin
```

## Commit Messages

- No AI branding, no Co-Authored-By
- Clear, descriptive
- No emoji
