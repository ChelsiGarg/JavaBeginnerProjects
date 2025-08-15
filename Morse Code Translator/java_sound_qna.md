# Java Sound API & Audio Playback - Q&A Reference

## 1. `playSound` Method Explanation
**Question:** Explain the `playSound` method.

**Answer:**  
Line-by-line explanation:
```java
public void playSound(String[] morseMessage) throws LineUnavailableException {
    AudioFormat audioFormat = new AudioFormat(44100, 16, 1, true, false);
    DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
    SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
    sourceDataLine.open(audioFormat);
    sourceDataLine.start();
}
```
- **AudioFormat** → Defines sample rate, bits per sample, channels, signed/unsigned, endianness.
- **DataLine.Info** → Specifies desired line type (playback vs record) and format.
- **AudioSystem.getLine** → Requests a matching line from the system.
- **SourceDataLine** → A type of DataLine for playback.
- **open/start** → Prepare and start the line.

---

## 2. Why Pass `dataLineInfo` and Typecast
**Question:** Why did we pass `dataLineInfo` and later typecast to `SourceDataLine`?

**Answer:**
- `dataLineInfo` describes the type and format of line you want.
- `getLine` returns a generic `Line` type.
- Typecasting to `SourceDataLine` is necessary to use playback-specific methods like `write()`.

**Analogy:** Tool rental shop — `dataLineInfo` is your request form, casting is you saying “I know this tool is a drill, let me use it as a drill.”

---

## 3. `playBeep` Method Explanation
**Question:** Explain the `playBeep` method.

**Answer:**
- Creates a byte array sized for given duration at 44100 samples/sec.
- Loops to fill array with sine wave data for a 440 Hz tone.
- Writes this data to the `SourceDataLine` for playback.

---

## 4. Audio Samples per Second
**Question:** What does “audio samples per second” signify?

**Answer:**
- Number of times per second the amplitude of the sound wave is measured.
- Higher sample rate → more accurate representation of sound.
- 44,100 samples/sec (44.1 kHz) = CD quality.
- Governed by Nyquist Theorem.

**Analogy:** Like video frame rate — higher FPS = smoother motion.

---

## 5. Visual Example of Sampling Rates
**Answer:** 44.1 kHz captures the waveform smoothly, 8 kHz produces a jagged approximation.

---

## 6. Shutdown Sequence
**Question:** Explain `drain()`, `stop()`, `close()`.

**Answer:**
- **drain()** → Wait until all data in buffer is played.
- **stop()** → Stop playback (can be restarted if not closed).
- **close()** → Release resources (cannot be restarted).

---

## 7. Why Use a Separate Thread for Audio
**Question:** Why run audio in another thread and why does it still run after `actionPerformed` returns?

**Answer:**
- Prevents blocking the Event Dispatch Thread (UI remains responsive).
- `start()` schedules `run()` on a separate thread — continues even after `actionPerformed` finishes.

---

## 8. Who Calls `run()`?
**Answer:**
- `start()` tells JVM to create a new thread and call `run()` internally.
- Calling `run()` directly runs it on the current thread (no parallelism).

---

## 9. Why Only Check for SHIFT Key
**Question:** Why not check for Caps Lock, Tab, etc.?

**Answer:**
- SHIFT is pressed often during typing → avoiding unnecessary updates is important.
- Other keys are rarely pressed and the cost is negligible.

---

## 10. Java Sound API Definition
**Question:** Is this implementation using Java Sound API, and what is it?

**Answer:**
- Yes — uses `javax.sound.sampled` package.
- Built-in Java library for playing, recording, and processing audio.
- No extra dependencies needed.

---

## 11. Markdown Table Example
```md
| Feature    | JTextArea                      | JTextPane                               |
|------------|--------------------------------|------------------------------------------|
| Purpose    | Simple multi-line text display/edit | Rich text editing & formatting          |
| Formatting | Plain text only                | Supports bold, italic, colors, fonts, styles |
```

---

## 12. Spelling
**Question:** Spelling of "miscellaneous"?  
**Answer:** miscellaneous ✅

