# UC9 – Weight Measurement (Kilogram, Gram, Pound)

## 📌 Description

Extends the Quantity Measurement Application to support a new measurement category: **Weight**.

Supports:

- Equality comparison  
- Unit conversion  
- Addition (implicit & explicit target unit)  

**Weight units:**

- `KILOGRAM` (base unit)  
- `GRAM` (1 g = 0.001 kg)  
- `POUND` (1 lb ≈ 0.453592 kg)  

Length (UC1–UC8) remains fully functional and independent.

---

## 🏗 Architecture

### 🔹 WeightUnit (Standalone Enum)

- Stores conversion factor relative to `KILOGRAM`  
- `convertToBaseUnit(double)`  
- `convertFromBaseUnit(double)`  
- Immutable and thread-safe  

### 🔹 QuantityWeight

- `private final double value`  
- `private final WeightUnit unit`  
- `equals()` based on base-unit normalization  
- `convertTo(targetUnit)`  
- `add(other)`  
- `add(other, targetUnit)`  
- Immutable value object  

---

## ✅ Equality Examples

- `Quantity(1.0, KILOGRAM).equals(Quantity(1000.0, GRAM))`  
  → `true`  

- `Quantity(1.0, KILOGRAM).equals(Quantity(~2.20462, POUND))`  
  → `true` (within epsilon)  

- `Quantity(1.0, KILOGRAM).equals(Quantity(1.0, FOOT))`  
  → `false` (category mismatch)  

---

## 🔁 Conversion Examples

- `Quantity(1.0, KILOGRAM).convertTo(GRAM)`  
  → `Quantity(1000.0, GRAM)`  

- `Quantity(2.0, POUND).convertTo(KILOGRAM)`  
  → `Quantity(~0.907184, KILOGRAM)`  

- `Quantity(500.0, GRAM).convertTo(POUND)`  
  → `Quantity(~1.10231, POUND)`  

---

## ➕ Addition Examples

### Implicit target unit

- `Quantity(1.0, KILOGRAM).add(Quantity(1000.0, GRAM))`  
  → `Quantity(2.0, KILOGRAM)`  

### Explicit target unit

- `Quantity(1.0, KILOGRAM).add(Quantity(1000.0, GRAM), GRAM)`  
  → `Quantity(2000.0, GRAM)`  

- `Quantity(2.0, KILOGRAM).add(Quantity(4.0, POUND), KILOGRAM)`  
  → `Quantity(~3.82, KILOGRAM)`  

---

## 🛡 Validation Rules

- Unit must not be null  
- Value must be finite (no NaN / Infinity)  
- Cross-category comparison returns `false`  
- Immutable objects (no mutation)  

---

## 🧠 Concepts Covered

- Multiple Measurement Categories  
- Base Unit Normalization (Kilogram)  
- Enum-Based Conversion Responsibility  
- Category Type Safety  
- Equality & HashCode Contract  
- Arithmetic on Value Objects  
- Method Overloading  
- Floating-Point Precision Handling  
- Architectural Scalability  
- Immutability & Thread Safety  

---

## 🏛 Architectural Impact

- No modification required in Length module  
- Weight mirrors Length design (UC8 pattern)  
- Scalable for Volume, Temperature, etc.  
- Clean separation between categories  
- Enterprise-ready extensible design  
