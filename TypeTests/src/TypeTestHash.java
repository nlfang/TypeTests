import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.*;

public class TypeTestHash {
    private ArrayList[] table; //Hash table
    private int numTests; //Number of tests in hash table
    private int tableCapacity; //Capacity of table
    private ArrayList<String> allSwitches; //Names of all switches in hash table
    private ArrayList<String> allKeyboards; //Names of all keyboards in hash table

    //Constructor
    public TypeTestHash(int capacity) {
        table = new ArrayList[getNextPrime(capacity)];
        numTests = 0;
        tableCapacity = getNextPrime(capacity);
        allSwitches = new ArrayList<>();
        allKeyboards = new ArrayList<>();
    }

    public void buildHash(String inputfile) throws Exception {
        FileReader fr = new FileReader(inputfile);
        BufferedReader bfr = new BufferedReader(fr);
        String line = bfr.readLine();

        while (line != null) {
            String[] vars = line.split("\\|");
            TypeTest newTest = new TypeTest(vars[0], vars[1], vars[2], vars[3]);
            put(newTest);
            line = bfr.readLine();
        }
    }

    //Returns the TypeTest with the given keyboard name
    public ArrayList<TypeTest> get(String switches) throws NoSuchAlgorithmException {
        ArrayList<TypeTest> result = new ArrayList<>();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(switches.getBytes());
        String hashString = new String(hash);
        int hashCode = hashString.hashCode();
        int index = hashCode % tableCapacity;

        if (index < 0) {
            index += tableCapacity;
        }

        if (table[index] == null) {
            return null;
        }

        for (int i = 0; i < table[index].size(); i++) {
            TypeTest temp = (TypeTest) table[index].get(i);
            if (temp.getSwitches().equals(switches)) {
                result.add(temp);
            }
        }
        return result;
    }

    //Adds TypeTest to hash table
    public void put(TypeTest c) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(c.getSwitches().getBytes());
        String hashString = new String(hash);
        int hashCode = hashString.hashCode();
        int index = hashCode % tableCapacity;

        if (index < 0) {
            index += tableCapacity;
        }

        if (table[index] == null) {
            table[index] = new ArrayList();
        }
        table[index].add(c);
        allSwitches.add(c.getSwitches());
        allKeyboards.add(c.getKeyboard());
        numTests++;
    }

    //Removes TypeTest from hash table (never used in this program)
    public TypeTest remove(String switches) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(switches.getBytes());
        String hashString = new String(hash);
        int hashCode = hashString.hashCode();
        int index = hashCode % tableCapacity;

        if (index < 0) {
            index += tableCapacity;
        }

        if (table[index] == null) {
            return null;
        }

        TypeTest match;
        for (int i = 0; i < table[index].size(); i++) {
            TypeTest temp = (TypeTest) table[index].get(i);
            if (temp.getSwitches().equals(switches)) {
                match = temp;
                table[index].remove(temp);
                numTests--;
                return match;
            }
        }
        return null;
    }

    public ArrayList<String> getAllSwitches() {
        return this.allSwitches;
    }

    public ArrayList<String> getAllKeyboards() {
        return this.allKeyboards;
    }

    public int size() {
        return numTests;
    }

    //Get the next prime number p >= num
    private int getNextPrime(int num) {
        if (num == 2 || num == 3)
            return num;

        int rem = num % 6;

        switch (rem) {
            case 0:
            case 4:
                num++;
                break;
            case 2:
                num += 3;
                break;
            case 3:
                num += 2;
                break;
        }

        while (!isPrime(num)) {
            if (num % 6 == 5) {
                num += 2;
            } else {
                num += 4;
            }
        }

        return num;
    }

    //Determines if a number > 3 is prime
    private boolean isPrime(int num) {
        if (num % 2 == 0) {
            return false;
        }

        int x = 3;

        for (int i = x; i < num; i += 2) {
            if (num % i == 0) {
                return false;
            }
        }

        return true;
    }
}
