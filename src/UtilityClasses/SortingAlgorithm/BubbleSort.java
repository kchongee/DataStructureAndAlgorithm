package UtilityClasses.SortingAlgorithm;

public class BubbleSort<T extends Comparable<T>>
{
    T[] arr;


    public BubbleSort(T[] arr)
    {
        this.arr = arr;
    }

    public void sort(Boolean ascending)
    {
        boolean canStopSwap = false;
        while (!canStopSwap) {
            canStopSwap = !swappedWhileTraverse(ascending);
        }
    }

    public boolean swappedWhileTraverse(Boolean ascending)
    {
        boolean swapTakePlace = false;
        for (int i = 0 ; i < arr.length-1 ; i++)
        {
            T currentElement = arr[i];
            T futureElement = arr[i+1];


            if (futureElement.compareTo(currentElement) > 0) // future bigger
            {
                if (!ascending){
                    arr[i] = futureElement;
                    arr[i+1] = currentElement;
                    swapTakePlace = true;
                }
            }
            else if (futureElement.compareTo(currentElement) < 0) // future smaller
            {
                if (ascending){
                    arr[i] = futureElement;
                    arr[i+1] = currentElement;
                    swapTakePlace = true;
                }
            }
            else
            {
                // do nothing
            }

        }

        return swapTakePlace;
    }

    public T[] getArray(){
        return this.arr;
    }


    public static void main(String[] args)
    {
        Integer[] a = new Integer[]{1, 2, 3, 4, 5, 5, 2, 7, 2, 6, 3, 3, 2, 8,2, 8, 2, 8};
        BubbleSort bSort = new BubbleSort(a);
        Integer[] sorted = (Integer[]) bSort.getArray();
        for (int i = 0; i < a.length ; i++){
            System.out.println(sorted[i]);
        }
    }
}
