public class PCB {
    private static int NEXT_ID = 1;
    public final int id;
    public final String nomeProg;
    public final int[] tabela;
    public int pc;

    public PCB(String nomeProg, int[] tabela) {
        this.id = NEXT_ID++;
        this.nomeProg = nomeProg;
        this.tabela = tabela;
        this.pc = 0;
    }

    @Override
    public String toString() {
        return "PCB{id=" + id + ", prog=" + nomeProg + ", pc=" + pc + ", paginas=" + tabela.length + "}";
    }
}
