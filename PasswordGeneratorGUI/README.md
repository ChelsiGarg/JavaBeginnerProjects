# Password Generator

This project is a simple **Password Generator** application with a graphical user interface (GUI). It allows users to generate random passwords based on customizable options.

## 🔧 Features

- Users can specify the **desired length** of the password.
- There are **four toggle buttons** to include different character types:
  - **Lowercase** letters
  - **Uppercase** letters
  - **Numbers**
  - **Symbols**
- Each button can be toggled on or off:
  - When a button is **clicked once**, that character type is included in the password.
  - When clicked **again**, the character type is excluded.
- Users can select **one or more** character type buttons.
- After setting the password length and desired character types, clicking the **"Generate"** button will create a password with the selected criteria.

## ✅ Example

If the user selects:
- Password length: `5`
- Toggles ON: `Uppercase`, `Lowercase`

Then a random password of 5 characters will be generated using only **uppercase** and **lowercase** letters.

## ⚠️ Validation Rules

A password **will not** be generated if:
1. The provided password length is less than or equal to `0`.
2. None of the toggle buttons (`Lowercase`, `Uppercase`, `Numbers`, `Symbols`) are selected.

## 🛠️ Technologies Used

- **Programming Language**: Java
- **GUI Library**: Swing
- **Swing Components Used**:
  - `JLabel`
  - `JFrame`
  - `JTextArea`
  - `JScrollPane`
  - `JToggleButton`
  - `JButton`

---

## 🧩 Project Structure

### 1. `passwordGeneratorGUI` (Frontend)

- This class is responsible for the **graphical user interface**.
- It **extends `JFrame`** and contains all the GUI setup inside its constructor.
- Key configuration:
  - **Layout Manager is disabled** using `setLayout(null)` to allow **manual placement** of components by setting their exact position & size using `setBounds(x,y,height,width)` function.
- GUI Components:
  - A title label
  - A `JTextArea` to take password length input
  - **Four `JToggleButton`s** for selecting character types
  - A **"Generate" button**
  - An output `JTextArea` to display the final password
  - A `JScrollPane` added to the output area to handle long passwords
- The **"Generate" button** is attached with an `ActionListener` that triggers the `generatePassword()` method in the backend class when clicked.

---

### 2. `passwordGenerator` (Backend)

- This class contains the core **logic to generate the password**.
- Steps followed:
  1. A `String` is built containing all **valid characters**, based on the states of the toggle buttons.
  2. A `Random` ckass object is used to **pick characters randomly** from the valid character string.
  3. Characters are appended one by one to a `StringBuilder` to form the password.
     - `StringBuilder` is used instead of `String` for better **performance** during concatenation in loops.
  4. The final password is returned as a `String`.

---