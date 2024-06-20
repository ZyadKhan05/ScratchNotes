public class Main {
    public static void main(String[] args) {
        Notes notes = new Notes();
        
        Note card1 = new Note("What is the capital of France?", "Paris");
        Note card2 = new Note("What is the capital of UK?", "London");


        notes.addNote(card1);
        notes.addNote(card2);

        notes.printNotes();
    }
}
