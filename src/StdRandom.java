/******************************************************************************
 *  Compilation:  javac StdRandom.java
 *  Execution:    java StdRandom
 *  Dependencies: System.out.java
 *
 *  A library of static methods to generate pseudo-random numbers from
 *  different distributions (bernoulli, uniform, gaussian, discrete,
 *  and exponential). Also includes a method for shuffling an array.
 *
 *
 *  %  java StdRandom 5
 *  seed = 1316600602069
 *  59 16.81826  true 8.83954  0 
 *  32 91.32098  true 9.11026  0 
 *  35 10.11874  true 8.95396  3 
 *  92 32.88401  true 8.87089  0 
 *  72 92.55791  true 9.46241  0 
 *
 *  % java StdRandom 5
 *  seed = 1316600616575
 *  96 60.17070  true 8.72821  0 
 *  79 32.01607  true 8.58159  0 
 *  81 59.49065  true 9.10423  1 
 *  96 51.65818  true 9.02102  0 
 *  99 17.55771  true 8.99762  0 
 *
 *  % java StdRandom 5 1316600616575
 *  seed = 1316600616575
 *  96 60.17070  true 8.72821  0 
 *  79 32.01607  true 8.58159  0 
 *  81 59.49065  true 9.10423  1 
 *  96 51.65818  true 9.02102  0 
 *  99 17.55771  true 8.99762  0 
 *
 *
 *  Remark
 *  ------
 *    - Relies on randomness of nextDouble() method in java.util.Random
 *      to generate pseudorandom numbers in [0, 1).
 *
 *    - This library allows you to set and get the pseudorandom number seed.
 *
 *    - See http://www.honeylocust.com/RngPack/ for an industrial
 *      strength random number generator in Java.
 *
 ******************************************************************************/

import java.util.Random;

/**
 *  The {@code StdRandom} class provides static methods for generating
 *  random number from various discrete and continuous distributions, 
 *  including Bernoulli, uniform, Gaussian, exponential, pareto,
 *  Poisson, and Cauchy. It also provides method for shuffling an
 *  array or subarray.
 *  <p>
 *  For additional documentation,
 *  see <a href="http://introcs.cs.princeton.edu/22library">Section 2.2</a> of
 *  <i>Introduction to Programming in Java: An Interdisciplinary Approach</i>
 *  by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public final class StdRandom {

    private static Random random;    // pseudo-random number generator
    private static long seed;        // pseudo-random number generator seed

    // static initializer
    static {
        // this is how the seed was set in Java 1.4
        seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    // don't instantiate
    private StdRandom() { }

    /**
     * Sets the seed of the pseudorandom number generator.
     * This method enables you to produce the same sequence of "random"
     * number for each execution of the program.
     * Ordinarily, you should call this method at most once per program.
     *
     * @param s the seed
     */
    public static void setSeed(long s) {
        seed   = s;
        random = new Random(seed);
    }

    /**
     * Returns the seed of the pseudorandom number generator.
     *
     * @return the seed
     */
    public static long getSeed() {
        return seed;
    }

    /**
     * Returns a random real number uniformly in [0, 1).
     *
     * @return a random real number uniformly in [0, 1)
     */
    public static double uniform() {
        return random.nextDouble();
    }

    /**
     * Returns a random integer uniformly in [0, n).
     * 
     * @param n number of possible integers
     * @return a random integer uniformly between 0 (inclusive) and <tt>N</tt> (exclusive)
     * @throws IllegalArgumentException if <tt>n <= 0</tt>
     */
    public static int uniform(int n) {
        if (n <= 0) throw new IllegalArgumentException("Parameter N must be positive");
        return random.nextInt(n);
    }

    ///////////////////////////////////////////////////////////////////////////
    //  STATIC METHODS BELOW RELY ON JAVA.UTIL.RANDOM ONLY INDIRECTLY VIA
    //  THE STATIC METHODS ABOVE.
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Returns a random real number uniformly in [0, 1).
     * 
     * @return     a random real number uniformly in [0, 1)
     * @deprecated Replaced by {@link #uniform()}.
     */
    public static double random() {
        return uniform();
    }

    /**
     * Returns a random integer uniformly in [a, b).
     * 
     * @param  a the left endpoint
     * @param  b the right endpoint
     * @return a random integer uniformly in [a, b)
     * @throws IllegalArgumentException if <tt>b <= a</tt>
     * @throws IllegalArgumentException if <tt>b - a >= Integer.MAX_VALUE</tt>
     */
    public static int uniform(int a, int b) {
        if (b <= a) throw new IllegalArgumentException("Invalid range");
        if ((long) b - a >= Integer.MAX_VALUE) throw new IllegalArgumentException("Invalid range");
        return a + uniform(b - a);
    }

    /**
     * Returns a random real number uniformly in [a, b).
     * 
     * @param  a the left endpoint
     * @param  b the right endpoint
     * @return a random real number uniformly in [a, b)
     * @throws IllegalArgumentException unless <tt>a < b</tt>
     */
    public static double uniform(double a, double b) {
        if (!(a < b)) throw new IllegalArgumentException("Invalid range");
        return a + uniform() * (b-a);
    }

    /**
     * Returns a random boolean from a Bernoulli distribution with success
     * probability <em>p</em>.
     *
     * @param  p the probability of returning <tt>true</tt>
     * @return <tt>true</tt> with probability <tt>p</tt> and
     *         <tt>false</tt> with probability <tt>p</tt>
     * @throws IllegalArgumentException unless <tt>p >= 0.0</tt> and <tt>p <= 1.0</tt>
     */
    public static boolean bernoulli(double p) {
        if (!(p >= 0.0 && p <= 1.0))
            throw new IllegalArgumentException("Probability must be between 0.0 and 1.0");
        return uniform() < p;
    }

    /**
     * Returns a random boolean from a Bernoulli distribution with success
     * probability 1/2.
     * 
     * @return <tt>true</tt> with probability 1/2 and
     *         <tt>false</tt> with probability 1/2
     */
    public static boolean bernoulli() {
        return bernoulli(0.5);
    }

    /**
     * Returns a random real number from a standard Gaussian distribution.
     * 
     * @return a random real number from a standard Gaussian distribution
     *         (mean 0 and standard deviation 1).
     */
    public static double gaussian() {
        // use the polar form of the Box-Muller transform
        double r, x, y;
        do {
            x = uniform(-1.0, 1.0);
            y = uniform(-1.0, 1.0);
            r = x*x + y*y;
        } while (r >= 1 || r == 0);
        return x * Math.sqrt(-2 * Math.log(r) / r);

        // Remark:  y * Math.sqrt(-2 * Math.log(r) / r)
        // is an independent random gaussian
    }

    /**
     * Returns a random real number from a Gaussian distribution with mean &mu;
     * and standard deviation &sigma;.
     * 
     * @param  mu the mean
     * @param  sigma the standard deviation
     * @return a real number distributed according to the Gaussian distribution
     *         with mean <tt>mu</tt> and standard deviation <tt>sigma</tt>
     */
    public static double gaussian(double mu, double sigma) {
        return mu + sigma * gaussian();
    }

    /**
     * Returns a random integer from a geometric distribution with success
     * probability <em>p</em>.
     * 
     * @param  p the parameter of the geometric distribution
     * @return a random integer from a geometric distribution with success
     *         probability <tt>p</tt>
     * @throws IllegalArgumentException unless <tt>p >= 0.0</tt> and <tt>p <= 1.0</tt>
     */
    public static int geometric(double p) {
        if (!(p >= 0.0 && p <= 1.0))
            throw new IllegalArgumentException("Probability must be between 0.0 and 1.0");
        // using algorithm given by Knuth
        return (int) Math.ceil(Math.log(uniform()) / Math.log(1.0 - p));
    }

    /**
     * Returns a random integer from a Poisson distribution with mean &lambda;.
     *
     * @param  lambda the mean of the Poisson distribution
     * @return a random integer from a Poisson distribution with mean <tt>lambda</tt>
     * @throws IllegalArgumentException unless <tt>lambda > 0.0</tt> and not infinite
     */
    public static int poisson(double lambda) {
        if (!(lambda > 0.0))
            throw new IllegalArgumentException("Parameter lambda must be positive");
        if (Double.isInfinite(lambda))
            throw new IllegalArgumentException("Parameter lambda must not be infinite");
        // using algorithm given by Knuth
        // see http://en.wikipedia.org/wiki/Poisson_distribution
        int k = 0;
        double p = 1.0;
        double L = Math.exp(-lambda);
        do {
            k++;
            p *= uniform();
        } while (p >= L);
        return k-1;
    }

    /**
     * Returns a random real number from the standard Pareto distribution.
     *
     * @return a random real number from the standard Pareto distribution
     */
    public static double pareto() {
        return pareto(1.0);
    }

    /**
     * Returns a random real number from a Pareto distribution with
     * shape parameter &alpha;.
     *
     * @param  alpha shape parameter
     * @return a random real number from a Pareto distribution with shape
     *         parameter <tt>alpha</tt>
     * @throws IllegalArgumentException unless <tt>alpha > 0.0</tt>
     */
    public static double pareto(double alpha) {
        if (!(alpha > 0.0))
            throw new IllegalArgumentException("Shape parameter alpha must be positive");
        return Math.pow(1 - uniform(), -1.0/alpha) - 1.0;
    }

    /**
     * Returns a random real number from the Cauchy distribution.
     *
     * @return a random real number from the Cauchy distribution.
     */
    public static double cauchy() {
        return Math.tan(Math.PI * (uniform() - 0.5));
    }

    /**
     * Returns a random integer from the specified discrete distribution.
     *
     * @param  a the probability of occurrence of each integer
     * @return a random integer from a discrete distribution:
     *         <tt>i</tt> with probability <tt>a[i]</tt>
     * @throws NullPointerException if <tt>a</tt> is <tt>null</tt>
     * @throws IllegalArgumentException if sum of array entries is not (very nearly) equal to <tt>1.0</tt>
     * @throws IllegalArgumentException unless <tt>a[i] >= 0.0</tt> for each index <tt>i</tt>
     */
    public static int discrete(double[] a) {
        if (a == null) throw new NullPointerException("argument array is null");
        double EPSILON = 1E-14;
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            if (!(a[i] >= 0.0)) throw new IllegalArgumentException("array entry " + i + " must be nonnegative: " + a[i]);
            sum = sum + a[i];
        }
        if (sum > 1.0 + EPSILON || sum < 1.0 - EPSILON)
            throw new IllegalArgumentException("sum of array entries does not approximately equal 1.0: " + sum);

        // the for loop may not return a value when both r is (nearly) 1.0 and when the
        // cumulative sum is less than 1.0 (as a result of floating-point roundoff error)
        while (true) {
            double r = uniform();
            sum = 0.0;
            for (int i = 0; i < a.length; i++) {
                sum = sum + a[i];
                if (sum > r) return i;
            }
        }
    }

    /**
     * Returns a random real number from an exponential distribution
     * with rate &lambda;.
     * 
     * @param  lambda the rate of the exponential distribution
     * @return a random real number from an exponential distribution with
     *         rate <tt>lambda</tt>
     * @throws IllegalArgumentException unless <tt>lambda > 0.0</tt>
     */
    public static double exp(double lambda) {
        if (!(lambda > 0.0))
            throw new IllegalArgumentException("Rate lambda must be positive");
        return -Math.log(1 - uniform()) / lambda;
    }

    /**
     * Rearranges the elements of the specified array in uniformly random order.
     *
     * @param  a the array to shuffle
     * @throws NullPointerException if <tt>a</tt> is <tt>null</tt>
     */
    public static void shuffle(Object[] a) {
        if (a == null) throw new NullPointerException("argument array is null");
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + uniform(N-i);     // between i and N-1
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified array in uniformly random order.
     *
     * @param  a the array to shuffle
     * @throws NullPointerException if <tt>a</tt> is <tt>null</tt>
     */
    public static void shuffle(double[] a) {
        if (a == null) throw new NullPointerException("argument array is null");
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + uniform(N-i);     // between i and N-1
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified array in uniformly random order.
     *
     * @param  a the array to shuffle
     * @throws NullPointerException if <tt>a</tt> is <tt>null</tt>
     */
    public static void shuffle(int[] a) {
        if (a == null) throw new NullPointerException("argument array is null");
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + uniform(N-i);     // between i and N-1
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }


    /**
     * Rearranges the elements of the specified subarray in uniformly random order.
     *
     * @param  a the array to shuffle
     * @param  lo the left endpoint (inclusive)
     * @param  hi the right endpoint (inclusive)
     * @throws NullPointerException if <tt>a</tt> is <tt>null</tt>
     * @throws IndexOutOfBoundsException unless <tt>(0 <= lo) && (lo <= hi) && (hi < a.length)</tt>
     * 
     */
    public static void shuffle(Object[] a, int lo, int hi) {
        if (a == null) throw new NullPointerException("argument array is null");
        if (lo < 0 || lo > hi || hi >= a.length) {
            throw new IndexOutOfBoundsException("Illegal subarray range");
        }
        for (int i = lo; i <= hi; i++) {
            int r = i + uniform(hi-i+1);     // between i and hi
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified subarray in uniformly random order.
     *
     * @param  a the array to shuffle
     * @param  lo the left endpoint (inclusive)
     * @param  hi the right endpoint (inclusive)
     * @throws NullPointerException if <tt>a</tt> is <tt>null</tt>
     * @throws IndexOutOfBoundsException unless <tt>(0 <= lo) && (lo <= hi) && (hi < a.length)</tt>
     */
    public static void shuffle(double[] a, int lo, int hi) {
        if (a == null) throw new NullPointerException("argument array is null");
        if (lo < 0 || lo > hi || hi >= a.length) {
            throw new IndexOutOfBoundsException("Illegal subarray range");
        }
        for (int i = lo; i <= hi; i++) {
            int r = i + uniform(hi-i+1);     // between i and hi
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified subarray in uniformly random order.
     *
     * @param  a the array to shuffle
     * @param  lo the left endpoint (inclusive)
     * @param  hi the right endpoint (inclusive)
     * @throws NullPointerException if <tt>a</tt> is <tt>null</tt>
     * @throws IndexOutOfBoundsException unless <tt>(0 <= lo) && (lo <= hi) && (hi < a.length)</tt>
     */
    public static void shuffle(int[] a, int lo, int hi) {
        if (a == null) throw new NullPointerException("argument array is null");
        if (lo < 0 || lo > hi || hi >= a.length) {
            throw new IndexOutOfBoundsException("Illegal subarray range");
        }
        for (int i = lo; i <= hi; i++) {
            int r = i + uniform(hi-i+1);     // between i and hi
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Unit test.
     */
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        if (args.length == 2) StdRandom.setSeed(Long.parseLong(args[1]));
        double[] t = { .5, .3, .1, .1 };

        System.out.println("seed = " + StdRandom.getSeed());
        for (int i = 0; i < N; i++) {
            System.out.printf("%2d "  , uniform(100));
            System.out.printf("%8.5f ", uniform(10.0, 99.0));
            System.out.printf("%5b "  , bernoulli(.5));
            System.out.printf("%7.5f ", gaussian(9.0, .2));
            System.out.printf("%2d "  , discrete(t));
            System.out.println();
        }

        String[] a = "A B C D E F G".split(" ");
        for (String s : a)
            System.out.print(s + " ");
        System.out.println();
    }

}

/******************************************************************************
 *  Copyright 2002-2015, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
