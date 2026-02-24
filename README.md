# UC3 – Generic Quantity Class (DRY Principle)

##  Description

Refactors UC1 and UC2 by replacing separate Feet and Inches classes with a single generic `QuantityLength` class.

Eliminates code duplication and follows the **DRY (Don't Repeat Yourself)** principle while preserving all previous functionality.

---

##  Objective

- Support equality comparison across units (Feet, Inches)  
- Convert values to a common base unit (feet)  
- Maintain clean, scalable, and maintainable design  

---

##  Implementation

- `LengthUnit` enum with conversion factors  
- `QuantityLength` class with:
  - `private final double value`  
  - `private final LengthUnit unit`  
- Conversion to base unit before comparison  
- Overridden `equals()` using `Double.compare()`  
- Null and type safety checks  

---

##  Example

**Input:**  
Quantity(1.0, FEET) and Quantity(12.0, INCH)  

**Output:**  
Equal: `true`  

**Input:**  
Quantity(1.0, INCH) and Quantity(1.0, INCH)  

**Output:**  
Equal: `true`  

---

##  Concepts Covered

- DRY Principle  
- Enum Usage  
- Polymorphism  
- Abstraction  
- Encapsulation  
- Equality Contract  
- Cross-Unit Comparison  
- Single Responsibility Principle  
- Scalable Design  

---

##  Benefits Over UC1 & UC2

- No duplicated code  
- Centralized conversion logic  
- Easier to add new units  
- Improved maintainability  
- Backward compatibility preserved  
