# Code Port - Progress Report

**Generated:** 2026-05-04
**Source:** tmp/cmp-any
**Target:** src

## Executive Summary

| Metric | Count | Percentage |
|--------|-------|------------|
| Function parity | 8/13 matched (target 16) | 61.5% |
| Class/type parity | 4/4 matched (target 6) | 100.0% |
| Combined symbol parity | 12/17 matched (target 22) | 70.6% |
| Average function body similarity | 0.46 | inline-code cosine |
| Average documentation similarity | 0.82 | doc text cosine |
| Missing source functions | 0 | 0% parity until ported |
| Missing source classes/types | 0 | 0% parity until ported |
| Missing source symbol files | 0 | 0 symbols |
| Cheat/scoring failures | 0 | forced to 0% |
| Total source files | 3 | 100% |
| Target units (paired) | 4 | - |
| Target files (total) | 4 | - |
| Porting progress | 2 | 66.7% (matched) |
| Missing files | 0 | 0.0% |
| Reexport/wiring files | 1 | consult-only |

## Port Quality Analysis

**Average Function Similarity:** 0.46

Similarity in this report is the required function-by-function body/parameter score. Class/type parity and symbol deficits are reported beside it; whole-file shape is diagnostic only.

**Work Distribution:**
- Critical (<0.60): 2 files (100.0% of matched)
- Needs review (0.60-0.84): 0 files (0.0% of matched)

## Worst Function Scores First

Every matched file is listed from lowest function body/parameter similarity upward. Missing symbol names are not capped.

| Rank | Source | Target | Function similarity | Functions | Missing functions | Types | Missing types | Tests | Symbol deficit | Priority |
|------|--------|--------|---------------------|-----------|-------------------|-------|---------------|-------|----------------|----------|
| 1 | `ord` | `cmpany.OrdAny` | 0.37 | 3/6 matched | `eq`, `partial_cmp`, `cmp` | 1/1 matched (target 2) | _none_ | 1/1 | 3 | 30706.3 |
| 2 | `eq` | `cmpany.PartialEqAny` | 0.55 | 5/7 matched (target 10) | `eq`, `token` | 3/3 matched (target 4) | _none_ | 2/3 | 2 | 21004.5 |

## Cheat Detection / Scoring Failures

_None detected._

### Critical Ports (Similarity < 0.60, Worst First)

These files need significant work:

- `ord` -> `cmpany.OrdAny` (0.37)
- `eq` -> `cmpany.PartialEqAny` (0.55)

## Incorrect Ports (Missing Types)

These files are matched (often via `// port-lint`) but appear to be missing one or more type declarations
present in the Rust source file.

| Source | Target | Missing types | Examples |
|--------|--------|---------------|----------|
| _None detected_ | | | |

## High Priority Missing Files

No missing files detected.

## Documentation Gaps

There is missing documentation that is hurting overall scoring.

**Documentation coverage:** 13 / 20 lines (65%)

Documentation gaps (>20%), complete list:

- `eq` - 29% gap (14 → 10 lines)
- `ord` - 50% gap (6 → 3 lines)

## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `lib` | `Lib` | 0 | `src/lib.rs` | `Lib.kt` |

