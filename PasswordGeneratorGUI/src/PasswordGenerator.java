// this class will work as the backend and will generate the password

import java.util.Random;

public class PasswordGenerator {
    // character pools
    // these strings will contain all the characters that we will use to generate passwords
    public static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    public static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBERS = "0123456789";
    public static final String SPECIAL_SYMBOLS = "!@#$%^&*()_+-=[]{}:<>?;,./~`";

    // the random class allows us to generate random number which can be used to randomly pick characters
    private final Random random;

    // constructor
    public PasswordGenerator(){
        random = new Random();
    }

    // length- length of the password to be generated (taken from user in passwordLengthInputArea variable from GUI)
    // includeUppercase etc.. -  considers if uppercase etc. should be included in password (taken from togglebutton's state)
    public String generatePassword(int length, boolean includeUppercase, boolean includeLowercase, boolean includeNumbers, boolean includeSymbols){
        // we will use StringBuilder over String forbetter efficiency (more details in Readme)
        StringBuilder passwordBuilder = new StringBuilder();

        // validCharacters will store all the characters that have their toggle buttons enabled
        String validCharacters = "";

        if(includeUppercase) validCharacters+=UPPERCASE_CHARACTERS;
        if(includeLowercase) validCharacters+=LOWERCASE_CHARACTERS;
        if(includeNumbers) validCharacters+=NUMBERS;
        if(includeSymbols) validCharacters+=SPECIAL_SYMBOLS;

        // build password
        for(int i=0; i<length; i++){
            // generate a random index b/w 0 to validCharacters.length-1
            int randomIndex = random.nextInt(validCharacters.length());

            // use randomIndex to randomly pick character from validCharacters String
            char randomChar = validCharacters.charAt(randomIndex);

            // store char into password builder
            passwordBuilder.append(randomChar);

            // do this until we have reached the length that the user has provided
        }

        return passwordBuilder.toString();

    }

}
