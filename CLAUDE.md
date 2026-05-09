# Claude Code Project Instructions — gazebo-kotlin

## Project Overview

Kotlin Multiplatform port home for primitives from
[facebookincubator/gazebo](https://github.com/facebookincubator/gazebo).

The first ported subtree is `cmp_any`, exposed under
`io.github.kotlinmania.gazebo.cmpany`.

The upstream Rust source it was translated from is **not tracked** in this
repo — fetch it into `tmp/gazebo/cmp_any/` by running
`./tools/fetch-rust-source.sh` after cloning.

## Critical Workflows

### 1. Read AGENTS.md first

Every translation rule, idiom mapping, and naming convention is in
[AGENTS.md](./AGENTS.md). Don't translate a file without reading it.

### 2. Port-Lint headers (REQUIRED)

```kotlin
// port-lint: source cmp_any/src/<file>.rs
package io.github.kotlinmania.gazebo.cmpany
```

### 3. Quality verification

```bash
./gradlew compileKotlinMacosArm64
./gradlew macosArm64Test
```

The runtime gate is `./gradlew test`.

## Translation rules

Same as the kotlinmania global rules. The translator's-mindset list applies:
- One Rust file -> one Kotlin file
- Translate top-to-bottom in upstream order
- Comments are content
- Bottom-up, no stubs
- `@Suppress` is forbidden EXCEPT in `OrdAny.new`'s captured-comparator
  cast (documented in AGENTS.md)

## Commit Messages

Follow Sydney's style: no AI branding, no Co-Authored-By lines, no emoji.

## Re-exports from upstream `mod.rs` files

When an upstream Rust `mod.rs` is **only re-exporting** something that actually lives elsewhere
(`pub use <crate-path>::<Name>;`, often under a different name), do **not** preserve that
re-export shape in Kotlin as a "central alias" API. Do not write a `typealias` for the
re-exported name. The existing `Forbidden` rule against "Re-export typealias files at root
packages" is enforced through this procedure.

Workflow:

1. **Identify what the `mod.rs` is re-exporting and the name it's exported as.** Record both
   the original symbol's fully-qualified upstream path and the (possibly different) re-export
   name.

2. **Find callers across the kotlinmania monorepo.** A caller is any Kotlin file in another
   `*-kotlin` repo that has both a `tmp/` folder and a Cargo.toml depending on the Rust
   counterpart of *this* crate, where the file references the re-exported name. Search for:
   - direct imports: `import <reexport-package>.<Name>`
   - wildcard imports of the re-export package, when `<Name>` is used in the file body
   - fully-qualified inline references

3. **Rewrite each caller to reference the upstream/original symbol directly.** If the caller
   still needs to write `<Name>` unchanged, use Kotlin aliasing:
   `import <upstream-fully-qualified-name> as <Name>`. Never bridge with a Kotlin `typealias`.

4. **Keep `Mod.kt` (or the equivalent file for that package) as a tracking file.** It carries
   the translated upstream module-level comments and a literal-quoted reference to each upstream
   `pub use` line (e.g. `// pub use crate::lib::result::Result;`). Each time a caller is migrated
   off the re-export, append the caller's absolute path under a `// Callers migrated:` ledger in
   `Mod.kt`. Append, never delete. Once all callers are migrated, the `typealias` (if any) is
   removed; the tracking file remains as the ledger of the migration.

Reference example: [/Volumes/stuff/Projects/kotlinmania/serde-kotlin/tmp/serde/serde_core/src/private/mod.rs](/Volumes/stuff/Projects/kotlinmania/serde-kotlin/tmp/serde/serde_core/src/private/mod.rs)
re-exports `Result` from `crate::lib::result`. The Kotlin tracking file lives at
[/Volumes/stuff/Projects/kotlinmania/serde-kotlin/src/commonMain/kotlin/io/github/kotlinmania/serde/core/private/Mod.kt](/Volumes/stuff/Projects/kotlinmania/serde-kotlin/src/commonMain/kotlin/io/github/kotlinmania/serde/core/private/Mod.kt).
A caller that previously did `import io.github.kotlinmania.serde.core.private.Result` is
rewritten to `import kotlin.Result as Result` (or just removes the import and relies on the
auto-imported `kotlin.Result`).
