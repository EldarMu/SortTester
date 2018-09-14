package com.eldar;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


//Класс, который приготавливает данные, которые ваш метод будет сортировать
//оценивает, сколько времени сортировка заняла
//и проверяет что результат действительно сортирован
public class SortTimer {
    String sortType;
    public SortTimer()
    {
        sortType = new MySorter().type;
    }
    public String TimeSorter(int size, String type) {
        int[] arrToSort = new int[size];

        //Приготавливаем массив для сортирования
        Random rnd = new Random();
        switch (type) {
            case "unsorted":
                for (int i = 0; i < size; i++) {
                    arrToSort[i] = rnd.nextInt(size);
                }
                break;
            case "sorted":
                for (int i = 0; i < size; i++) {
                    arrToSort[i] = i;
                }
                break;
            case "same":
                for (int i = 0; i < size; i++) {
                    arrToSort[i] = 42;
                }
                break;
        }

        long startTime,endTime;
        String result = "";

        //Запускаем Java контроль потоков
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<int[]> future = es.submit(new SorterTask(arrToSort));
        long timeout = 20;
        startTime =  System.currentTimeMillis();
        try {
            arrToSort = future.get(timeout, TimeUnit.SECONDS);
        }
        catch (Exception e) {
            System.out.println("an exception occured: " + e);
            future.cancel(true);
            return ("Метод сортировки не выдержал " + String.valueOf(size) + " элементов за " +String.valueOf(timeout) +" с");
        }

        endTime = System.currentTimeMillis();
        es.shutdownNow();

        if (isSorted(arrToSort)) {
            return formText(endTime-startTime, size, type);
        }
        else {
            return "Sorting Method failed to sort";
        }
    }

    private String formText(long time, int size, String type) {
        String temp = "";
        StringBuilder sb = new StringBuilder("Массив ");
        switch (size) {
            case 1000:
                temp = "тысячи ";
                break;
            case 10000:
                temp ="десяти тысяч ";
                break;
            case 100000:
                temp = "ста тысяч ";
                break;
            case 1000000:
                temp = "миллиона ";
                break;
        }
        sb.append(temp);
        switch (type) {
            case "sorted":
                temp = "сортированных ";
                break;
            case "same":
                temp = "одинаковых ";
                break;
            default:
                temp = "";
        }
        sb.append(temp + "элементов занял " + String.valueOf(time) + " мс");
        return sb.toString();
    }

    //Метод для проверки сортированного состояния массива
    private boolean isSorted(int[] arr)
    {
        boolean sorted = true;
        int init = arr[0];
        for (int i = 1; i < arr.length; i++ ) {
            if (init > arr[i]) {
                sorted = false;
            }
            init = arr[i];
        }
        return sorted;
    }

    //Task для запуска сортировки в отдельном потоке - требуется для прекращения его после н-ного кол-ва времени
    class SorterTask implements Callable<int[]> {
        int[] internalArr;
        public SorterTask(int[] array)
        {
            internalArr = array;
        }
        @Override
        public int[] call() throws Exception {
            MySorter sortMethod = new MySorter();
            return sortMethod.sort(internalArr);
        }
    }
}
