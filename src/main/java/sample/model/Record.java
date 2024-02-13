package sample.model;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Record implements ObservableList<Record> {

    private int record_id;

    private int user_id;
    private String date;
    private String weight;
    private String temperature;
    private String low;
    private String high;

    private String notes;
    public Record() {
    }

    public Record(int user_id, String date, String weight, String temperature, String low, String high, String notes) {
        this.date = date;
        this.weight = weight;
        this.temperature = temperature;
        this.low = low;
        this.high = high;
        this.notes = notes;
    }
    public String getDate(){ return this.date;}
    public String getWeight(){
        return this.weight;
    }
    public String getTemperature(){
        return this.temperature;
    }

    public String getLow() {
        return this.low;
    }

    public String getHigh() {
        return this.high;
    }

    public String getNotes(){
        return this.notes;
    }

    public int getRecordId(){
        return record_id;
    }
    public void setDate(String date){
        this.date = date;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public void setHigh(String high) {
        this.high = high;
    }
    public void setNotes(String notes){
        this.notes = notes;
}

    public void setRecordId(int record_id){
        this.record_id = record_id;
    }


    @Override
    public void addListener(ListChangeListener<? super Record> listChangeListener) {

    }

    @Override
    public void removeListener(ListChangeListener<? super Record> listChangeListener) {

    }

    @Override
    public boolean addAll(Record... records) {
        return false;
    }

    @Override
    public boolean setAll(Record... records) {
        return false;
    }

    @Override
    public boolean setAll(Collection<? extends Record> collection) {
        return false;
    }

    @Override
    public boolean removeAll(Record... records) {
        return false;
    }

    @Override
    public boolean retainAll(Record... records) {
        return false;
    }

    @Override
    public void remove(int i, int i1) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<Record> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(Record record) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Record> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Record> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Record get(int index) {
        return null;
    }

    @Override
    public Record set(int index, Record element) {
        return null;
    }

    @Override
    public void add(int index, Record element) {

    }

    @Override
    public Record remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<Record> listIterator() {
        return null;
    }

    @Override
    public ListIterator<Record> listIterator(int index) {
        return null;
    }

    @Override
    public List<Record> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public void addListener(InvalidationListener invalidationListener) {

    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {

    }
}
