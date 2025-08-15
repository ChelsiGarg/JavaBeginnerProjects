# Java Sound API & Audio Playback - Q&A Reference

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
- **DataLine.Info** → DataLine.Info is like a request form telling the system:  
&nbsp;&nbsp;&nbsp;&nbsp;- "I need a SourceDataLine (a type of line used to play audio, not record it)."  
&nbsp;&nbsp;&nbsp;&nbsp;- "It must support the format I just described (audioFormat)."
- **AudioSystem.getLine(...)**
&nbsp;&nbsp;&nbsp;&nbsp;- Requests a line from the system. This line should have all requirements captured in ataLineInfo. 
&nbsp;&nbsp;&nbsp;&nbsp;- We typecasted this line to SourceDataLine because SourceDataLine can send raw audio data to speakers).
&nbsp;&nbsp;&nbsp;&nbsp;- We cast it to SourceDataLine because getLine returns the generic Line interface. 
- **sourceDataLine.open(audioFormat)**
&nbsp;&nbsp;&nbsp;&nbsp;- Opens the audio line with your chosen audioFormat. 
&nbsp;&nbsp;&nbsp;&nbsp;- This reserves system resources and gets the line ready to accept sound data.
- **sourceDataLine.start()**
&nbsp;&nbsp;&nbsp;&nbsp;- Starts the line — now it’s actively ready to play any audio data you send to it.

Real-life analogy
• AudioFormat = the “rules” for how the song will be played (tempo, pitch range, mono/stereo).  
• DataLine.Info = filling out a request form for the sound system saying “I want a speaker line that plays with these rules.”  
• getLine(...) = the sound system gives you the actual speaker connection based on your requirements.  
• open(...) = plugging in and setting the audio format.  
• start() = turning the speaker on, waiting for you to send music.

---

**Question:** Explain the `playBeep` method.

**Answer:** This method is where the actual sound gets generated and sent to the speakers.
- Creates a byte array (that will hold the sound waveform data) sized for given duration(dot, dash or slash) at 44100 samples/sec.
- Loops over every audio sample to fill array with sine wave data for a 440 Hz tone. We are manually creating the waveform.
- Writes this data to the `SourceDataLine` for playback.

---

**Question:** What does “audio samples per second” signify?

**Answer:**
- Number of times per second the amplitude (or value) of the sound wave is measured.
- Higher sample rate → more accurate representation of sound.
- 44,100 samples/sec (44.1 kHz) = CD quality.

---

## 5. Visual Example of Sampling Rates
**Answer:** 44.1 kHz captures the waveform smoothly, 8 kHz produces a jagged approximation.
<img width="1947" height="980" alt="image" src="https://github.com/user-attachments/assets/5812d1a1-57ee-4918-9a33-2e5290ebef0f" />

---


