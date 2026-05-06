# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 2/3 (66.7%)
- **Function parity:** 8/13 matched (target 16) — 61.5%
- **Class/type parity:** 4/4 matched (target 6) — 100.0%
- **Combined symbol parity:** 12/17 matched (target 22) — 70.6%
- **Average inline-code cosine:** 0.46 (function body across 2 matched files)
- **Average documentation cosine:** 0.82 (doc text across 2 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 2 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. ord

- **Target:** `cmpany.OrdAny`
- **Similarity:** 0.37
- **Dependents:** 0
- **Priority Score:** 30706.3
- **Functions:** 3/6 matched
- **Missing functions:** `eq`, `partial_cmp`, `cmp`
- **Types:** 1/1 matched (target 2)
- **Missing types:** _none_
- **Tests:** 1/1 matched

### 2. eq

- **Target:** `cmpany.PartialEqAny`
- **Similarity:** 0.55
- **Dependents:** 0
- **Priority Score:** 21004.5
- **Functions:** 5/7 matched (target 10)
- **Missing functions:** `eq`, `token`
- **Types:** 3/3 matched (target 4)
- **Missing types:** _none_
- **Tests:** 2/3 matched

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

## Next Commands

```bash
# Initialize task queue for systematic porting
cd tools/ast_distance
./ast_distance --init-tasks ../../tmp/gazebo/cmp_any rust ../../src kotlin tasks.json ../../AGENTS.md

# Get next high-priority task
./ast_distance --assign tasks.json <agent-id>
```
## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `lib` | `Lib` | 0 | `src/lib.rs` | `Lib.kt` |
