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
**A:** It makes the dialog **block input** to other windows until closed, ensuring the player responds before continuing.  

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

---

**Q2. Why did you use `BufferedImage` and `ImageIcon` together?**  
**A:**  
- `BufferedImage` → allows manipulation of image pixels.  
- `ImageIcon` → is required by Swing to display the image inside `JLabel`.  

---

**Q3. Why use `String += "*"` in `hideWords` instead of `StringBuilder`?**  
**A:** This is a simpler implementation. However, using `StringBuilder` is more efficient because strings are immutable in Java.  

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
**A:** Add metadata in `data.txt` (like tags), or use separate files for each difficulty. Modify `WordDB` to load based on selected difficulty.  

---

**Q2. How would you implement multiplayer mode?**  
**A:** Allow two players to take turns guessing. Track scores separately. Could be done via socket programming (network multiplayer) or local turns.  

---

**Q3. How would you persist game state?**  
**A:** Store current word, guessed letters, and incorrect guesses in a file or database. On restart, load them back.  

---

**Q4. How would you scale to millions of words?**  
**A:**  
- Use lazy loading or streaming from disk instead of loading all into memory.  
- Use a database for faster querying.  
- Apply caching for frequently used categories.  

---

**Q5. How would you make it thread-safe for multiple players?**  
**A:** Synchronize access to `WordDB` and shared resources. Use `ConcurrentHashMap` if multiple threads access the word list.  

---

## 🔹 Section 4: Real-life Analogies

1. **Hangman UI** = A **quiz host** showing blanks and letters to players.  
2. **WordDB** = A **dictionary in a library**, categories = shelves, words = books.  
3. **CustomTools.hideWords** = Covering letters with **post-it notes** until revealed.  
4. **Incorrect guesses updating image** = A **health bar** depleting in a video game.  
5. **CommonConstants** = A **style guide in a company**, ensuring consistency across everything.  
