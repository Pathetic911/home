package org.example;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        // Przykładowa tablica ciągów do posortowania
        String[] inputArray = {"1171", "99", "2822", "5555", "888", "4", "6766", "34433", "77667"};

        // Wywołanie sortowania przez liczenie pozycyjne (radix sort)
        String[] sortedArray = radixSort(inputArray);

        // Wyświetlenie posortowanej tablicy
        for (int i = 0; i < sortedArray.length; i++)
            System.out.println(sortedArray[i]);
    }

    // Metoda wykonująca sortowanie przez liczenie pozycyjne (radix sort)
    public static String[] radixSort(String[] inputArray) {
        // Mapa przechowująca ciągi znaków pogrupowane według ich długości
        HashMap<Integer, String[]> lengthMap = new HashMap<>();

        // Iteracja po tablicy wejściowej
        for (String str : inputArray) {
            // Początkowa inicjalizacja tablicy ciągów znaków dla danej długości
            String[] strings = {"q"};

            // Sprawdzenie, czy mapa zawiera już klucz o danej długości
            if (lengthMap.get(str.length()) != null) {
                // Aktualizacja tablicy, jeśli klucz istnieje
                strings = new String[lengthMap.get(str.length()).length + 1];
                System.arraycopy(lengthMap.get(str.length()), 0, strings, 0, lengthMap.get(str.length()).length);
            }

            // Dodanie aktualnego ciągu znaków do tablicy
            strings[strings.length - 1] = str;

            // Umieszczenie tablicy w mapie
            lengthMap.put(str.length(), strings);
        }

        // Sortowanie poszczególnych grup ciągów znaków w mapie
        for (Integer key : lengthMap.keySet()) {
            lengthMap.put(key, performRadixSort(lengthMap.get(key), key - 1));
        }

        // Rekonstrukcja posortowanej tablicy na podstawie długości i poszczególnych grup
        Integer currentIndex = 1;
        int innerIndex = 0;
        for (int outerIndex = 0; outerIndex < inputArray.length; outerIndex++) {

            // Pomijanie pustych grup
            while (lengthMap.get(currentIndex) == null)
                currentIndex++;

            // Przypisanie posortowanego ciągu znaków do tablicy wynikowej
            inputArray[outerIndex] = lengthMap.get(currentIndex)[innerIndex];
            innerIndex++;

            // Przechodzenie do następnej grupy, jeśli wszystkie elementy z aktualnej grupy zostały już przypisane
            if (innerIndex == lengthMap.get(currentIndex).length) {
                innerIndex = 0;
                currentIndex++;
            }
        }
        return inputArray;
    }

    // Metoda pomocnicza wykonująca sortowanie pozycyjne dla danej pozycji cyfry
    public static String[] performRadixSort(String[] array, int position) {
        // Warunek zakończenia rekurencji
        if (position == -1)
            return array;

        // Tablica pomocnicza do zliczania wystąpień poszczególnych cyfr na danej pozycji
        int[] countArray = new int[10];

        // Tablica wynikowa po posortowaniu
        String[] resultArray = new String[array.length];

        // Inicjalizacja tablicy zliczającej
        for (int i = 0; i < 10; i++) {
            countArray[i] = 0;
        }

        // Zliczanie wystąpień poszczególnych cyfr na danej pozycji
        for (String value : array) {
            countArray[(int) value.charAt(position) - 48]++;
        }

        // Kumulacja liczby wystąpień
        for (int i = 1; i < 10; i++) {
            countArray[i] += countArray[i - 1];
        }

        // Przypisanie posortowanych elementów do tablicy wynikowej
        for (int i = array.length - 1; i >= 0; i--) {
            resultArray[countArray[(int) array[i].charAt(position) - 48] - 1] = array[i];
            countArray[(int) array[i].charAt(position) - 48]--;
        }

        // Rekurencyjne wywołanie dla kolejnej pozycji cyfry
        return performRadixSort(resultArray, position - 1);
    }
}
