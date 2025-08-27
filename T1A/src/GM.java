public interface GM {
    int[] aloca(int nroPalavras);

    void desaloca(int[] tabela);

    void carregaPrograma(int[] programa, int[] tabela);

    int read(int[] tabela, int endLog);

    void write(int[] tabela, int endLog, int v);

    int traduz(int[] tabela, int endLog);
}
