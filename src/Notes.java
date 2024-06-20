import java.util.HashMap;
import java.util.Map;

public class Notes<Q, A> {
    private Map<Note<Q, A>, Integer> notes;

    public Notes() {
        notes = new HashMap<>();
    }

    public void addNote(Note<Q, A> note) {
        notes.put(note, notes.getOrDefault(note, 0) + 1);
    }

    public void removeNote(Note<Q, A> note) {
        if (notes.containsKey(note)) {
            int count = notes.get(note);
            if (count == 1) {
                notes.remove(note);
            } else {
                notes.put(note, count - 1);
            }
        }
    }

    public int getNotesCount(Note<Q, A> note) {
        return notes.getOrDefault(note, 0);
    }

    public void printNotes() {
        for (Map.Entry<Note<Q, A>, Integer> entry : notes.entrySet()) {
            System.out.println(entry.getKey());
        }
    }

}
