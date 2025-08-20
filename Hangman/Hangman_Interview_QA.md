# 🎯 Hangman Game – Interview Q&A

---

## 🔹 Section 1: General Project Questions

**Q1. Explain the architecture of your Hangman game.**  
**A:** The game is structured into four main classes:  
- `Hangman` → handles the **UI (Swing)** and **game logic**  
- `WordDB` → acts as the **backend word provider**, reading categories and words from a file  
- `CustomTools` → contains **helper methods** for images, fonts, and hiding words  
- `CommonConstants` → centralizes **paths, colors, and UI dimensions**  

This separation follows the principle of **modularity**. Each class has a single responsibility (SRP).  

---

**Q2. How does the game flow work?**  
**A:**  
1. `WordDB` loads a random challenge (category + word).  
2. `Hangman` creates the GUI: image, category label, hidden word (`*` for unknown letters), and letter buttons.  
3. User clicks a letter → `actionPerformed` checks correctness.  
   - If correct → reveal letters in the word.  
   - If wrong → increment counter and update hangman image.  
4. Game ends when either all letters are revealed (win) or 6 incorrect guesses (lose).  
5. Result is shown in a `JDialog`, with options to restart or quit.  

---

**Q3. What Java concepts does your project demonstrate?**  
**A:**  
- **OOP principles**: encapsulation (`wordList`, `categories` are private), modular design.  
- **Swing GUI programming**: `JFrame`, `JDialog`, event-driven model with `ActionListener`.  
- **Collections**: `HashMap` for category-to-words mapping, `ArrayList` for category selection.  
- **I/O**: `BufferedReader` + `FileReader` to read words from a file.  
- **Utility methods**: static helper functions (`CustomTools`).  
- **Constants management**: centralized config (`CommonConstants`).

**Q4. Explain OOP concepts used in your project.**  
**A:**  
1. `Encapsulation`  
Meaning: Wrapping data (fields) and behavior (methods) together, restricting direct access to internals (data hiding).  
In my Code:
- WordDB encapsulates the HashMap<String, String[]> wordList and ArrayList<String> categories.  
   - These are private, so outside classes can’t directly modify them.  
   - Instead, access is controlled via the loadChallenge() method.

2. `Abstraction`
Meaning: Hiding implementation details and exposing only the essential functionality (implementation hiding).  
In my Code:
- `CustomTools.createFont(String resource)` hides the internal details of reading a font file, decoding %20, and creating a Font.  
To the user of this method, it’s just “give me a font”.  
- `CustomTools.hideWords()` hides the character-by-character replacement logic — you just pass in a word, and it gives you a masked string.    

3. `Inheritance`  
Meaning: One class acquiring properties and behaviors of another.  
In my Code, I didn’t explicitly create custom subclasses, but I leveraged inheritance heavily:
- JFrame, JLabel, JButton → all inherit from Swing’s component hierarchy.
- `Hangman extends JFrame` → this means Hangman inherits all the functionality of a Swing JFrame (like window creation, layout handling, event management).

4. `Polymorphism`  
Meaning: Ability to use the same method/behavior in different forms.  
In my code, I haven't used this concept

---

## 🔹 Section 2: Class-Specific Questions

---

### **A. Hangman.java**

**Q1. Why does `Hangman` extend `JFrame` and implement `ActionListener`?**  
**A:** Extending `JFrame` makes `Hangman` a GUI window directly, avoiding boilerplate.  
Implementing `ActionListener` allows the same class to handle all button events through `actionPerformed`.  

---

**Q2. Why do you store buttons in an array `letterButtons[26]`?**  
**A:** Storing them in an array makes it easy to reference each button by its index (`c - 'A'`). It’s memory-efficient compared to using a `Map`.  

---

**Q3. What does `setModal(true)` do in your `resultDialog`?**  
**A:** It makes the dialog **block input** to other windows until closed, ensuring the player responds to the dialog window.  

---

**Q4. Why do you disable a button after it’s clicked?**  
**A:** To prevent repeated guesses of the same letter, which keeps the game fair and simplifies logic.  

---

**Q5. How is win condition detected?**  
**A:**  
```java
if(!hiddenWordLabel.getText().contains("*")) {
    resultLabel.setText("You got it right!");
    resultDialog.setVisible(true);
}
```
If there are no `"*"` characters left, the player has revealed the entire word.  

---

### **B. WordDB.java**

**Q1. Why use both `HashMap<String, String[]>` and `ArrayList<String>` for categories?**  
**A:**  
- `HashMap` → stores categories as keys and words as values.  
- `ArrayList` → allows random access by index since `HashMap` doesn’t support direct indexing.  

---

**Q2. Why do you use `Arrays.copyOfRange(parts, 1, parts.length)`?**  
**A:** To extract all words after the category name from each line. It avoids manually looping.  

---

**Q3. Why do you replace `%20` with spaces in file path?**  
**A:** When file paths come from resources, spaces are URL-encoded as `%20`. This fix ensures correct file access on systems where file names contain spaces.  

---

**Q4. What’s the time complexity of loading a challenge?**  
**A:**  
- Random category selection → `O(1)`  
- Random word selection → `O(1)`  
So overall challenge loading is constant time.  

---

### **C. CustomTools.java**

**Q1. Why are all methods static?**  
**A:** Because `CustomTools` is a utility class. It doesn’t hold state, so making methods static avoids creating unnecessary objects.  
&nbsp;&nbsp;&nbsp;Below is detailed explanation:  
&nbsp;&nbsp;&nbsp;👉 Example: Math class in Java:
```
Math.sqrt(25);
Math.max(5, 9);
```

You don’t need to create an object like:
```
Math m = new Math(); // unnecessary
m.sqrt(25);
```

2. Why Static Methods?  
A static method belongs to the class itself, not to an object.  
You can call it directly using the class name:
```
CustomTools.loadImage("path.png");
```

If methods were non-static, you’d need to create an object first:
```
CustomTools tools = new CustomTools();
tools.loadImage("path.png");
```

But since the object doesn’t store any useful information, creating it is wasteful.

3. Real-Life Analogy
Think of a calculator app:  
Functions: add, subtract, multiply.  
You don’t create a new calculator each time you add numbers.  
Instead, you just use it directly:
```
Calculator.add(2, 3); // → 5
```
If you had to create a new calculator object every time, it would be pointless because the calculator has no memory/state—it just computes results.

That’s why the methods are static → they act like global tools.

4. Why Not Always Use Static?  
If a class needs to remember state (like a BankAccount with balance), methods should not be static.
Example:
```
BankAccount account = new BankAccount(1000);
account.withdraw(200); // affects this account's balance
```
Here, withdraw depends on the account’s state.

So:
Utility classes → use static methods.
Stateful classes → use instance methods.

✅ Final Takeaway  
Since CustomTools is only a helper toolbox (no data inside it), making its methods static avoids wasting memory by creating unnecessary objects.


---

**Q2. Why did you use `BufferedImage` and `ImageIcon` together?**  
**A:**  
- `BufferedImage` → allows manipulation of image pixels.  
- `ImageIcon` → is required by Swing to display the image inside `JLabel`.  

---

**Q3. Why did you use `StringBuilder` instead of `String` to append characters?**  
**A:** I chose to use `StringBuilder` instead of `String` because String objects in Java are immutable. This means that every time I append a character to a String, a new object is created in memory, which is inefficient when dealing with frequent modifications. On the other hand, StringBuilder is mutable, so it can modify the same underlying object without creating new ones. This makes it much more efficient for operations like appending, inserting, or deleting character.

---

**Q4. What happens if an image/font resource is missing?**  
**A:** The methods print an error message and return `null`. This avoids a crash but could cause `NullPointerException` if not handled carefully.  

---

### **D. CommonConstants.java**

**Q1. Why use `public static final` for constants?**  
**A:**  
- `public` → accessible anywhere.  
- `static` → shared by all classes, no instance needed.  
- `final` → immutable, prevents accidental modification.  

---

**Q2. Why use `Color.decode("#14212D")` instead of `new Color(r,g,b)`?**  
**A:** `Color.decode` allows defining colors in HEX, which is more readable and aligns with web design conventions.  

---

**Q3. Why centralize constants in a separate class?**  
**A:** It makes code easier to maintain. If we change a path or color, we update only in one place instead of multiple files.  

---

## 🔹 Section 3: Advanced/Extension Questions

**Q1. How would you add difficulty levels (easy/medium/hard)?**  
**A:** Add metadata in `data.txt` (like tags for different levels), or use separate files for each difficulty. Modify `WordDB` to load based on selected difficulty.  

---

**Q2. How would you implement multiplayer mode?**  
**A:** I would start with a local multiplayer mode, where two players take turns guessing. I’d maintain separate scores and alternate turns after each guess, ensuring fair play. For future enhancement, this could be extended to online multiplayer using socket programming or networking concepts once I gain more experience with them.

---

**Q3. Explain local vs online multiplayer mode.**  
**A:** `Local multiplayer mode`: Local multiplayer mode means two or more players play on the same device by taking turns or sharing controls. In my project, it would work as a turn-based system where each player guesses alternately and their scores are tracked separately.

`Online multiplayer mode'`: Online or network multiplayer means players connect from different devices over the internet or a network. The game uses socket programming or networking to sync turns, actions, and scores between players in real time.

---

**Q4. How would you scale to millions of words?**  
**A:**    
- Use a database for faster querying.  
- Apply caching for frequently used categories.  

---

 
