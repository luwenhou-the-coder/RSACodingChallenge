
package rsachallenge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * Generating RSA key pair, a coding challenge from UnifyID.
 * 
 * @author Wenhou Lu
 */
public class RSAChallenge {

    /**
     * A method which fetches a random seed number from Random.org using HTTP
     * APIs.
     *
     * @return a long representation of the random seed
     * @throws IOException
     */
    public static long getRandom() throws IOException {
        StringBuilder result = new StringBuilder();
        // Configure the URL to get a random number.
        URL url = new URL("https://www.random.org/integers/?num=1&min=-1000000000&max=1000000000&col=1&base=10&format=plain&rnd=new");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        // Read from the input stream.
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        // Parse and return a long number.
        return Long.parseLong(result.toString());
    }

    /**
     * Main function which prints the RSA key pairs.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // Each public and private key consists of an exponent and a modulus
        BigInteger n; // n is the modulus for both the private and public keys
        BigInteger e; // e is the exponent of the public key
        BigInteger d; // d is the exponent of the private key

        long seed = getRandom();
        Random rnd = new Random(seed);

        // Step 1: Generate two large random primes.
        // Best practice for security is 2048 bits, which can be changed if needed.
        BigInteger p = new BigInteger(2048, 100, rnd);
        BigInteger q = new BigInteger(2048, 100, rnd);

        // Step 2: Compute n by the equation n = p * q.
        n = p.multiply(q);

        // Step 3: Compute phi(n) = (p-1) * (q-1)
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        // Step 4: Select a small odd integer e that is relatively prime to phi(n).
        // By convention the prime 65537 is used as the public exponent.
        e = new BigInteger("65537");

        // Step 5: Compute d as the multiplicative inverse of e modulo phi(n).
        d = e.modInverse(phi);

        // Step 6: (e,n) is the RSA public key
        System.out.println("e (public key): " + e);
        // Step 7: (d,n) is the RSA private key
        System.out.println("d (private key): " + d);
        // Modulus for both keys
        System.out.println("n (modulus): " + n);

    }
}
