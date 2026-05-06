package futcomp;

public class threadstest {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main thread starts");

        // A thread is a separate path for background work
        // letting the program do more than one thing at the same time.
        Thread workerOne = new Thread(new Runnable() {
            @Override
            public void run() {
                count("Worker 1");
            }
        });

        // This is the same idea, but written shorter with a lambda.
        // A lambda is just a shorter way to write a Runnable.
        Thread workerTwo = new Thread(() -> count("Worker 2"));

        // start() tells Java to run the thread in the background
        //run() would act like a normal method call.
        workerOne.start();
        workerTwo.start();

        System.out.println("Main thread keeps going while workers count");

        // join() makes the main thread wait until these workers finish.
        workerOne.join();
        workerTwo.join();

        System.out.println("Both workers finished");
        System.out.println("Main thread ends");
    }

    private static void count(String workerName) {
        for (int number = 1; number <= 5; number++) {
            System.out.println(workerName + " count: " + number);

            try {
                // sleep() pauses only this thread.
                // Other threads can still keep running.
                Thread.sleep(1000);
            }
            catch (InterruptedException error) {
                System.out.println(workerName + " was interrupted");
            }
        }
    }
}
