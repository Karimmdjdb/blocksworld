# BlocksWorld — AI Planning, Constraint Solving & Data Mining

![Java](https://img.shields.io/badge/Java-21+-red?style=flat&logo=openjdk&logoColor=white)
![Domain](https://img.shields.io/badge/Domain-BlocksWorld-blue?style=flat)
![Topic](https://img.shields.io/badge/AI-Planning%20%7C%20CSP%20%7C%20Data%20Mining-green?style=flat)

A coursework project in **Artificial Intelligence & Decision Support**.  
The project tackles the **BlocksWorld domain** through three complementary approaches:

1. **Modelling**: states, variables, constraints.  
2. **Planning**: search algorithms to reach a goal state.  
3. **Constraint Satisfaction (CSP)**: solving configurations with solvers and heuristics.  
4. **Data Mining**: extracting patterns (frequent itemsets, association rules).  

---

## Project Structure

```text
src
├── blocksworld
│   ├── modelling       # Variables & constraints
│   ├── planning        # Planners, actions, heuristics
│   ├── cp              # CSP solvers & heuristics
│   ├── datamining      # Frequent itemsets & association rules
│   └── demonstrations  # Demo1…Demo7 entry points
└── util                # Utilities
```

---

## Modelling

### Variables
Three variable types model the world of `n` blocks and `m` piles:

| Variable    | Domain                     | Meaning |
|-------------|----------------------------|---------|
| `On(b)`     | `{−m,…,−1,0,…,n−1} \ {b}`  | Support of block *b* (another block or a pile) |
| `Fixed(b)`  | `{true, false}`            | True if block *b* supports another block (cannot move) |
| `Free(p)`   | `{true, false}`            | True if pile *p* is empty |

### Constraints
- **NonOverlappingConstraint** → `On(b) ≠ On(b')` two blocs cannot share the same support
- **FixedSupportConstraint** → `On(b) = b' ⇒ Fixed(b') = true` if a bloc is support of another it can't cannot move
- **PileOccupancyConstraint** → `On(b) = p ⇒ Free(p) = false` if a pile supports a bloc it's not free
- **ConstantGapConstraint** → `On(b) = b' & On(b') > 0 ⇒ b - b' = b' - On(b')` regularity within piles, no cycles
- **SuperiorityConstraint** → `b > On(b)` increasing order, no cycles

### Providers
Providers generate all **variables and constraints** for an instance `(n blocks, m piles)`:

- `VariablesProvider`  
- `GeneralConstraintsProvider` (basic)  
- `RegularityConstraintsProvider` (regularity)  
- `GrowingConstraintsProvider` (superiority)  

### Demonstrations
- **Demo1** — Generate variables and constraints for `(n, m)`  
  ```bash
  java -cp bin blocksworld.demonstrations.Demo1 n m
  ```
- **Demo2** — Instantiate examples and check **regularity** and **growth**  
  ```bash
  java -cp bin blocksworld.demonstrations.Demo2
  ```

---

## Planning

### Actions
Four movement operators:

1. **BlocToBlocMovement** — move a block from one block to another block.  
2. **BlocToStackMovement** — move a block from a block to an empty pile.  
3. **StackToBlocMovement** — move a block from a pile to another block.  
4. **StackToStackMovement** — move a block from a pile to an empty pile.  

The **MovementsProvider** generates  
`n × (n + m − 2) × (n + m − 1)` possible actions for a world with `n` blocks and `m` piles.

### Planners
- `BFSPlanner` (Breadth-First)  
- `DFSPlanner` (Depth-First)  
- `DijkstraPlanner` (uniform cost)  
- `AStarPlanner` (with heuristics)

### Heuristics
- `MisplacedHeuristic` → +1 per misplaced block.  
- `BlockedMisplacedHeuristic` → +1 per misplaced block, +2 if also blocked.  

### Demonstrations
- **Demo3** — Compare planners (**DFS, BFS, Dijkstra, A\***).  
  ```bash
  java -cp bin blocksworld.demonstrations.Demo3
  ```
- **Demo4** — Simulate a plan with a chosen algorithm.  
  ```bash
  java -cp bin blocksworld.demonstrations.Demo4 a
  ```
  with `a = 1` DFS, `2` BFS, `3` Dijkstra, `4` A*.

  #### Preview
  ![blocks world preview](demo.gif)

> [!WARNING]
> The number of possible actions grows combinatorially (e.g. 1820 actions for 10 blocks and 5 piles).  
> Planning becomes intractable quickly without heuristics or pruning.

---

## CSP (Constraint Satisfaction)

### Solvers
- `BacktrackSolver`  
- `MACSolver` (Maintaining Arc Consistency)  
- `HeuristicMACSolver` (MAC + heuristics)  

### Heuristics
- **Variable selection**: `DomainSizeVariableHeuristic`, `NbConstraintsVariableHeuristic`  
- **Value selection**: `RandomValueHeuristic`  

### Optimization
Arc Consistency is applied **only once before search**, not at every recursive call.  
This improves performance without changing results.

### Demonstrations
- **Demo5** — Run solvers (**Backtrack, MAC, MAC+heuristics**).  
  ```bash
  java -cp bin blocksworld.demonstrations.Demo5 n m
  ```
- **Demo6** — Solve a configuration with chosen solver & constraints.  
  ```bash
  java -cp bin blocksworld.demonstrations.Demo6 n m c s
  ```
  - `c = 1` regular, `2` growing, `3` both  
  - `s = 1` Backtrack, `2` MAC, `3` MAC+heuristics  

> [!NOTE]
> Many configurations are already locally consistent → Backtrack and MAC may perform similarly in those cases.

---

## Data Mining

### Components
- `BooleanVariablesProvider` — build boolean variables for an instance.  
- `BooleanDatabase` — encodes transactions.  
- `ItemsetMiner`: BruteForce, `Apriori`.  
- `AssociationRuleMiner` — extract rules with support & confidence.  

### Workflow
1. Build a boolean DB of 10,000 random transactions.  
2. Extract frequent itemsets.  
3. Generate association rules with thresholds for support and confidence.  

### Demonstration
- **Demo7** — Mine association rules.  
  ```bash
  java -cp bin blocksworld.demonstrations.Demo7 f c
  ```
  - `f` = minimum frequency  
  - `c` = minimum confidence  

---

## Build & Run

From the project root:

```bash
javac -d bin $(find src -name "*.java")
java -cp bin blocksworld.demonstrations.Demo1 5 3
```

On Windows (PowerShell):

```powershell
$files = Get-ChildItem -Recurse -Filter *.java -Path src | % FullName
javac -d bin $files
java -cp bin blocksworld.demonstrations.Demo1 5 3
```

---

## Results & Observations

- **Planning**: A* explores fewer nodes and finds shorter plans; BFS/DFS quickly become intractable.  
- **CSP**: MAC preprocessing improves efficiency; heuristics reduce backtracks further.  
- **Data Mining**: Apriori scales better; brute force is only viable for small DBs.  

---

## Limitations & Future Work

- Rapid complexity growth with larger `n, m`.  
- Heuristics are simple; domain-specific heuristics could improve search.

The main limitation is the **rapid growth of complexity** with larger `n, m` (state space and number of actions).  
Potential ways to address this include:  
- Search-space reduction techniques.  
- Parallel exploration.  
- Multi-level / hierarchical planning.  
- Integration with external SAT/SMT solvers.  

---

## License

MIT

