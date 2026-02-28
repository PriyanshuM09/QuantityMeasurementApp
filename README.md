# UC4 – Extended Unit Support

##  Description

Extends UC3 by adding **YARDS** and **CENTIMETERS** to the generic `QuantityLength` class.

Demonstrates scalability of the DRY-based design.  
New units are added only in the `LengthUnit` enum without modifying the main class logic.

---

##  Objective

- Support equality across **FEET, INCHES, YARDS, and CENTIMETERS**  
- Convert all values to a common base unit before comparison  
- Maintain backward compatibility (UC1–UC3)  

---

##  Implementation

- Updated `LengthUnit` enum:
  - `FEET`  
  - `INCHES`  
  - `YARDS` (1 yard = 3 feet)  
  - `CENTIMETERS` (1 cm = 0.393701 inches)  
- No changes required in `QuantityLength` class  
- Centralized conversion logic  
- Overridden `equals()` using `Double.compare()`  

---

##  Example

**Input:**  
Quantity(1.0, YARDS) and Quantity(3.0, FEET)  

**Output:**  
Equal: `true`  

**Input:**  
Quantity(1.0, YARDS) and Quantity(36.0, INCHES)  

**Output:**  
Equal: `true`  

**Input:**  
Quantity(1.0, CENTIMETERS) and Quantity(0.393701, INCHES)  

**Output:**  
Equal: `true`  

---

##  Concepts Covered

- Scalability of Generic Design  
- DRY Principle Validation  
- Enum Extensibility  
- Cross-Unit Conversion  
- Mathematical Precision  
- Equality Contract  
- Backward Compatibility  

---

##  Benefits

- No code duplication  
- Easy addition of new units  
- Centralized conversion factors  
- Fully backward compatible  
- Clean, maintainable architecture  
