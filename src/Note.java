public class Note<Q, A> {
    private Q front;
    private A back;

    public Note(Q front, A back) {
        this.front = front;
        this.back = back;
    }

    public Q getfront() {
        return front;
    }

    public A getback() {
        return back;
    }

    @Override
    public String toString() {
        return "Front: " + front + "\nBack: " + back;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note<?, ?> note = (Note<?, ?>) o;
        return front.equals(note.front) && back.equals(note.back);
    }

}
