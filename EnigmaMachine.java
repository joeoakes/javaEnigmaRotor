import java.util.HashMap;
import java.util.Map;

public class EnigmaMachine {
    private Map<Character, Character> plugboard;
    private Rotor rotor;
    private Reflector reflector;

    public EnigmaMachine(Map<Character, Character> plugboard, Rotor rotor, Reflector reflector) {
        this.plugboard = plugboard;
        this.rotor = rotor;
        this.reflector = reflector;
    }

    public char encrypt(char letter) {
        // Pass through plugboard
        char processedLetter = plugboard.getOrDefault(letter, letter);

        // Rotate rotor before encryption
        rotor.rotate();

        // Encryption process: plugboard -> rotor -> reflector -> rotor -> plugboard
        System.out.println("letter after plugboard: " + processedLetter);
        processedLetter = rotor.substitute(processedLetter);
        System.out.println("rotor 1: ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        System.out.println("rotor 1: EKMFLGDQVZNTOWYHXUSPAIBRCJ");
        System.out.println("letter after rotor 1: " + processedLetter);
        System.out.println("rotor 1: ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        System.out.println("rotor 1: YRUHQSLDPXNGOKMIEBFZCWVJAT");
        processedLetter = reflector.substitute(processedLetter);
        System.out.println("letter after reflector: " + processedLetter);
        System.out.println("rotor 1: ZYXWVUTSRQPONMLKJIHGFEDCBA");
        System.out.println("rotor 1: EKMFLGDQVZNTOWYHXUSPAIBRCJ");
        processedLetter = rotor.reverseSubstitute(processedLetter);
        System.out.println("letter after rotor 1: " + processedLetter);

        // Pass through plugboard again
        processedLetter = plugboard.getOrDefault(processedLetter, processedLetter);

        return processedLetter;
    }

    public static void main(String[] args) {
        // Example plugboard settings
        Map<Character, Character> plugboardSettings = new HashMap<>();
        plugboardSettings.put('A', 'B');
        plugboardSettings.put('C', 'D');

        // Example rotor settings
        // ABCDEFGHIJKLMNOPQRSTUVWXYZ
        // EKMFLGDQVZNTOWYHXUSPAIBRCJ
        // ZYXWVUTSRQPONMLKJIHGFEDCBA  //reverse alphabet
        Rotor rotor = new Rotor("EKMFLGDQVZNTOWYHXUSPAIBRCJ");

        // Example reflector settings
        // ABCDEFGHIJKLMNOPQRSTUVWXYZ
        // YRUHQSLDPXNGOKMIEBFZCWVJAT
        Reflector reflector = new Reflector("YRUHQSLDPXNGOKMIEBFZCWVJAT");

        EnigmaMachine enigmaMachine = new EnigmaMachine(plugboardSettings, rotor, reflector);

        // Example encryption
        char letterToEncrypt = 'A';
        System.out.println("Letter to encrypt: " + letterToEncrypt);
        char encryptedLetter = enigmaMachine.encrypt(letterToEncrypt);
        System.out.println("Encrypted letter: " + encryptedLetter);
    }
}

class Rotor {
    private String wiring;
    private int position = 0;

    public Rotor(String wiring) {
        this.wiring = wiring;
    }

    public void rotate() {
        position = (position + 1) % 26;
    }

    public char substitute(char input) {
        int index = (input - 'A' + position) % 26;
        return wiring.charAt(index);
    }

    public char reverseSubstitute(char input) {
        int index = (wiring.indexOf(input) - position + 26) % 26;
        return (char) ('A' + index);
    }
}

class Reflector {
    private String wiring;

    public Reflector(String wiring) {
        this.wiring = wiring;
    }

    public char substitute(char input) {
        return wiring.charAt(input - 'A');
    }
}
