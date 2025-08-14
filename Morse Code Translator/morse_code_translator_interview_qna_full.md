
# Morse Code Translator – Interview Q&A

## 📌 Project Summary Table

| Feature                  | Description |
|--------------------------|-------------|
| **Language**             | Java |
| **GUI Framework**        | Swing |
| **Core Functions**       | Real-time text to Morse translation, audio playback of Morse code |
| **Key Classes**          | `MorseCodeTranslatorGUI`, `MorseCodeController` |
| **Audio API Used**       | `javax.sound.sampled` |
| **Threading**            | Used for audio playback to prevent UI freeze |
| **Event Handling**       | KeyListener for real-time text updates, ActionListener for button click |

---

## 📌 Flow of the Project

1. **User Input**
   - User types text in `textInputArea`.
   - `keyReleased()` in `MorseCodeTranslatorGUI` triggers on every key release except Shift.

2. **Translation**
   - The typed text is passed to `morseCodeController.translateToMorse()`.
   - This method converts characters to Morse code using a predefined map.

3. **Display Output**
   - The translated Morse code is displayed in `morseCodeArea`.

4. **Audio Playback**
   - Clicking "Play Sound" triggers an `ActionListener`.
   - A new thread is created to call `morseCodeController.playSound()`.
   - Morse code is played using system audio with short and long beeps for dots and dashes.

---

## 🎯 Interview Q&A

### **General Project Questions**

**Q:** What does your Morse Code Translator do?  
**A:** It converts typed text into Morse code in real-time and allows playback of the Morse code as audio beeps.

**Q:** Why did you choose Swing for the UI?  
**A:** Swing is lightweight, platform-independent, and quick to implement for small GUI projects.

**Q:** How is the UI updated in real-time as the user types?  
**A:** A `KeyListener` is added to the text input area, and `keyReleased()` calls the translation method to update the output text area.

---

### **Audio-Specific Questions**

**Q:** How do you play Morse code sounds in Java?  
**A:** I used `javax.sound.sampled.SourceDataLine` from the Java Sound API. By generating audio samples for specific frequencies, I can simulate beeps for dots and dashes.

**Q:** How do you prevent the UI from freezing while playing sound?  
**A:** I run audio playback in a separate thread so that the Event Dispatch Thread (EDT) remains responsive.

**Q:** What happens if the user clicks "Play Sound" multiple times quickly?  
**A:** I disable the button during playback to avoid overlapping sounds and re-enable it after playback finishes.

**Q:** What frequency and duration did you use for Morse code tones?  
**A:** A fixed frequency (e.g., 800Hz) for dots and dashes, with dots having shorter durations (~200ms) and dashes longer (~600ms).

**Q:** What is `LineUnavailableException` and when can it occur?  
**A:** It occurs when the requested audio line is unavailable, possibly because another process is using the sound system or requested parameters are unsupported.

**Q:** Why do you close the audio line after playback?  
**A:** To release system resources and avoid memory or audio device leaks.

---

### **Threading & Event Handling**

**Q:** Why did you use threads in your project?  
**A:** To handle long-running operations like audio playback without blocking the UI thread.

**Q:** Why did you choose `KeyListener` instead of `DocumentListener`?  
**A:** I used `KeyListener` for simplicity since I only needed to react to key releases, but `DocumentListener` could also be used for more text-change events.

---

### **Code Maintenance & Extensibility**

**Q:** How would you extend this project to support Morse-to-text?  
**A:** By implementing a reverse lookup map for Morse-to-character conversion and adding an input area for Morse code.

**Q:** How could you make audio playback more realistic?  
**A:** By adjusting tone length and pause durations to match official Morse timing standards (1 unit for dot, 3 for dash, 1 unit gap between symbols, etc.).

---

## ✅ Key Takeaways

- Swing + Java Sound API is effective for small GUI + audio projects.
- Multi-threading is essential for keeping the UI responsive during playback.
- Disabling buttons during long operations avoids UI and logic conflicts.
