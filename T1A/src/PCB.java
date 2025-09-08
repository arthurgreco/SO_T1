public class PCB {
    private static int NEXT_ID = 1;
    public final int id;
    public final String nomeProg;
    public final int[] tabela;
    public int pc;
    public final int progLen; // total de instruções do programa

    // Construtor novo (com tamanho do programa)
    public PCB(String nomeProg, int[] tabela, int progLen) {
        this.id = NEXT_ID++;
        this.nomeProg = nomeProg;
        this.tabela = tabela;
        this.pc = 0;
        this.progLen = progLen;
    }

    // Construtor antigo mantido por compatibilidade (assume len = tabela.length * tamPg, mas aqui usamos 0)
    // Evite usar este; preferir o acima.
    public PCB(String nomeProg, int[] tabela) {
        this(nomeProg, tabela, 0);
    }

    @Override
    public String toString() {
        return "PCB{id=" + id + ", prog=" + nomeProg + ", pc=" + pc + ", paginas=" + tabela.length + ", len=" + progLen + "}";
    }
}
