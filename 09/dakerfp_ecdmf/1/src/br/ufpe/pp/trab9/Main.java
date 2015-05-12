/*
 * Main.java
 * Copyright (C) 2015
 * Daker Fernandes <dakerfp@gmail.com>
 * Emiliano Firmino <emiliano.firmino@gmail.com>
 *
 * Distributed under terms of the MIT license.
 */

package br.ufpe.pp.trab9;

import br.ufpe.pp.trab9.TASLocker;
import br.ufpe.pp.trab9.Counter;
import br.ufpe.pp.trab9.Worker;

public class Main
{
    public static void main(String[] args) {
        int runDuration = args.length > 0 ? Integer.parseInt(args[0]) : 5;
        int numThreads = args.length > 1 ? Integer.parseInt(args[1]) : 10;

        Counter[]   counter = new Counter[10];

        TASLocker[] locker  = new TASLocker[10];

        Worker[] worker = new Worker[numThreads];

        for (int i = 0; i < 10; i++) {
            counter[i] = new Counter();
            locker[i] = new TASLocker();
        }


        for (int i = 0; i < worker.length; i++) {
            worker[i] = new Worker(counter, locker);
        }

        for (int i = 0; i < worker.length; i++) {
            worker[i].start();
        }

        try {
            Thread.sleep(runDuration * 1000);
        } catch (InterruptedException ie) {
            System.exit(-1);
        }

        for (int i = 0; i < worker.length; i++) {
            worker[i].interrupt();
        }

        for (int i = 0; i < worker.length; i++) {
            try {
                worker[i].join();
            } catch (InterruptedException ie) {
                // IGNORED
            }
        }

        long sum = 0;
        for (int i = 0; i < 10; i++) {
            System.out.println("Counter " + i + " counter to " + counter[i].get());
            sum += counter[i].get();
        }
        System.out.println("Total " + sum);

        for (int i = 0; i < worker.length; i++) {
            System.out.println("Worker " + i + " counted to " + worker[i].getMyIncrement());
        }

    }

}


