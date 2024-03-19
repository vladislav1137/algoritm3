package org.example;

import org.example.Exceptions.ElementNotFoundException;
import org.example.Exceptions.InvalidIndexException;

import java.util.Arrays;

public class IntegerListImpl implements org.example.IntegerList {
    private Integer[] storage;
    private int size;
    public IntegerListImpl(Integer[] storage) {
        this.storage = storage;
    }
    public IntegerListImpl() {
        storage = new Integer[10];
    }
    public IntegerListImpl(int initSize) {
        storage = new Integer[initSize];
    }
    private void validateItem(Integer item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
    }
    private void growIfNeeded() {
        if (size == storage.length) {
            grow();
        }
    }
    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new InvalidIndexException();
        }
    }
    @Override
    public Integer add(Integer item) {
        growIfNeeded();
        validateItem(item);
        storage[size++] = item;
        return item;
    }
    @Override
    public Integer add(int index, Integer item) {
        growIfNeeded();
        validateItem(item);
        validateIndex(index);
        if (index == size) {
            storage[size++] = item;
            return item;
        }
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = item;
        size++;
        return item;
    }
    @Override
    public Integer set(int index, Integer item) {
        validateIndex(index);
        validateItem(item);
        storage[index] = item;
        return item;
    }
    @Override
    public Integer remove(Integer item) {
        validateItem(item);
        int index = indexOf(item);
        if (index == -1) {
            throw new ElementNotFoundException("Element " + item + " is not present in the list");
        }
        if (index != size) {
            System.arraycopy(storage, index, storage, index + 1, size - index);
        }
        size--;
        return item;
    }
    @Override
    public Integer remove(int index) {
        validateIndex(index);
        Integer removedItem = storage[index];
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        storage[size - 1] = null;
        size--;
        return removedItem;
    }
    @Override
    public boolean contains(Integer item) {
        Integer[] storageCopy = toArray();
        sort(storageCopy);
        return binarySearch(storageCopy, item);
    }
    @Override
    public int indexOf(Integer item) {
        validateItem(item);
        for (int i = 0; i < size; i++) {
            if (storage[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }
    @Override
    public int lastIndexOf(Integer item) {
        validateItem(item);
        for (int i = size - 1; i >= 0; i--) {
            if (storage[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }
    @Override
    public Integer get(int index) {
        validateIndex(index);
        return storage[index];
    }
    @Override
    public boolean equals(org.example.IntegerList otherList) {
        return Arrays.equals(this.toArray(), otherList.toArray());
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    @Override
    public void clear() {
        size = 0;
    }
    @Override
    public Integer[] toArray() {
        return Arrays.copyOf(storage, size);
    }
    private void sort(Integer[] arr) {
        quickSort(arr, 0,arr.length-1);
    }
    private void quickSort(Integer[] arr,int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);
            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private static int partition(Integer[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);
        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;
                swapElements(arr, i, j);
            }
        }
        swapElements(arr, i + 1, end);
        return i + 1;
    }

    private static void swapElements(Integer[] arr, int i1, int i2) {
        int temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }
    public static boolean binarySearch(Integer[] arr, int item) {
        int min = 0;
        int max = arr.length - 1;
        while (min <= max) {
            int mid = (min + max) / 2;
            if (item == arr[mid]) {
                return true;
            }
            if (item < arr[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return false;
    }
    private void grow() {
        storage = Arrays.copyOf(storage, size+size / 2);
    }
}
