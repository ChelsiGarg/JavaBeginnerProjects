// this class will handle the logic of our GUI

import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class MorseCodeController {
    // we will use hashmap to translate user input to morse code
    // we'll use character as key & corresponsding morse code as its value

    private HashMap<Character, String> morseCodeMap;

    public MorseCodeController(){
        morseCodeMap = new HashMap<>();

         // uppercase
        morseCodeMap.put('A', ".-");
        morseCodeMap.put('B', "-...");
        morseCodeMap.put('C', "-.-.");
        morseCodeMap.put('D', "-..");
        morseCodeMap.put('E', ".");
        morseCodeMap.put('F', "..-.");
        morseCodeMap.put('G', "--.");
        morseCodeMap.put('H', "....");
        morseCodeMap.put('I', "..");
        morseCodeMap.put('J', ".---");
        morseCodeMap.put('K', "-.-");
        morseCodeMap.put('L', ".-..");
        morseCodeMap.put('M', "--");
        morseCodeMap.put('N', "-.");
        morseCodeMap.put('O', "---");
        morseCodeMap.put('P', ".--.");
        morseCodeMap.put('Q', "--.-");
        morseCodeMap.put('R', ".-.");
        morseCodeMap.put('S', "...");
        morseCodeMap.put('T', "-");
        morseCodeMap.put('U', "..-");
        morseCodeMap.put('V', "...-");
        morseCodeMap.put('W', ".--");
        morseCodeMap.put('X', "-..-");
        morseCodeMap.put('Y', "-.--");
        morseCodeMap.put('Z', "--..");

        // lowercase
        morseCodeMap.put('a', ".-");
        morseCodeMap.put('b', "-...");
        morseCodeMap.put('c', "-.-.");
        morseCodeMap.put('d', "-..");
        morseCodeMap.put('e', ".");
        morseCodeMap.put('f', "..-.");
        morseCodeMap.put('g', "--.");
        morseCodeMap.put('h', "....");
        morseCodeMap.put('i', "..");
        morseCodeMap.put('j', ".---");
        morseCodeMap.put('k', "-.-");
        morseCodeMap.put('l', ".-..");
        morseCodeMap.put('m', "--");
        morseCodeMap.put('n', "-.");
        morseCodeMap.put('o', "---");
        morseCodeMap.put('p', ".--.");
        morseCodeMap.put('q', "--.-");
        morseCodeMap.put('r', ".-.");
        morseCodeMap.put('s', "...");
        morseCodeMap.put('t', "-");
        morseCodeMap.put('u', "..-");
        morseCodeMap.put('v', "...-");
        morseCodeMap.put('w', ".--");
        morseCodeMap.put('x', "-..-");
        morseCodeMap.put('y', "-.--");
        morseCodeMap.put('z', "--..");

        // numbers
        morseCodeMap.put('0', "-----");
        morseCodeMap.put('1', ".----");
        morseCodeMap.put('2', "..---");
        morseCodeMap.put('3', "...--");
        morseCodeMap.put('4', "....-");
        morseCodeMap.put('5', ".....");
        morseCodeMap.put('6', "-....");
        morseCodeMap.put('7', "--...");
        morseCodeMap.put('8', "---..");
        morseCodeMap.put('9', "----.");

        // special characters
        morseCodeMap.put(' ', "/");
        morseCodeMap.put(',', "--..--");
        morseCodeMap.put('.', ".-.-.-");
        morseCodeMap.put('?', "..--..");
        morseCodeMap.put(';', "-.-.-.");
        morseCodeMap.put(':', "---...");
        morseCodeMap.put('(', "-.--.");
        morseCodeMap.put(')', "-.--.-");
        morseCodeMap.put('[', "-.--.");
        morseCodeMap.put(']', "-.--.-");
        morseCodeMap.put('{', "-.--.");
        morseCodeMap.put('}', "-.--.-");
        morseCodeMap.put('+', ".-.-.");
        morseCodeMap.put('-', "-....-");
        morseCodeMap.put('_', "..--.-");
        morseCodeMap.put('"', ".-..-.");
        morseCodeMap.put('\'', ".----.");
        morseCodeMap.put('/', "-..-.");
        morseCodeMap.put('\\', "-..-.");
        morseCodeMap.put('@', ".--.-.");
        morseCodeMap.put('=', "-...-");
        morseCodeMap.put('!', "-.-.--");
    }

    public String translateToMorse(String textToTranslate){
        StringBuilder translatedText = new StringBuilder();
        for(Character letter: textToTranslate.toCharArray()){
            // get the corresponding morse code for the letter & append it to the returning value
            translatedText.append(morseCodeMap.get(letter)+" ");
        }
        return translatedText.toString();
    }

    // morseMessage - contains the morse message after being translated
    public void playSound(String[] morseMessage) throws LineUnavailableException, InterruptedException{
        // audio format specifies the audio properties (i.e. the type of sound we want)
        AudioFormat audioFormat = new AudioFormat(44100, 16, 1, true, false);

        // create data line (to play incoming audio data)
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
        SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        sourceDataLine.open(audioFormat);
        sourceDataLine.start();

        // duration of the sound to be played
        int dotDuration = 200;
        int dashDuration = (int) (1.5*dotDuration);
        int slashDuration = 2 * dashDuration;

        for(String pattern : morseMessage){
            // play the letter sound
            for(char ch: pattern.toCharArray()){
                if(ch=='.'){
                    playBeep(sourceDataLine, dotDuration);
                    Thread.sleep(dotDuration);
                }else if(ch=='-'){
                    playBeep(sourceDataLine, dashDuration);
                    Thread.sleep(dashDuration);
                }else if(ch=='/'){
                    Thread.sleep(slashDuration);
                }
            }

            // waits a bit before playing the next sequence
            Thread.sleep(dotDuration);
        }

        // close audio output line (cleans up resources)
        sourceDataLine.drain();
        sourceDataLine.stop();
        sourceDataLine.close();
    }

    // sends audio data to be played to the data line
    private void playBeep(SourceDataLine line, int duration){
        // create audio data
        byte[] data = new byte[duration*44100/1000];

        for(int i=0; i<data.length; i++){
            // calculates the angle of sine wave for thr current sample based on the sample rate & frequency
            double angle = i / (44100.0/440) * 2 * Math.PI;

            // calculates the amplitude of sine wave at current angle & scale it to fit within the range of a signed byte (-128, 127)
            // also, in the context of audio processing, a signed byte is often used to represent audio data because it can represent both 
            // positive & negative amplitudes of sound waves
            data[i] = (byte) (Math.sin(angle) * 127);
        }

        // write the audio in data line to be played
        line.write(data, 0, data.length);
    }

}
