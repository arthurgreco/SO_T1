import java.util.*;

public class ProcessManager {
    private final GM gm;
    private final Map<String, int[]> repo;
    private final List<PCB> prontos = new LinkedList<>();
    private PCB rodando = null;

    public ProcessManager(GM gm, Map<String, int[]> repo) {
        this.gm = gm;
        this.repo = repo;
    }

    public PCB criaProcesso(String nome) {
        int[] prog = repo.get(nome);
        if (prog == null)
            throw new IllegalArgumentException("Programa não encontrado" + nome);
        int[] tabela = gm.aloca(prog.length);
        if (tabela == null)
            return null;
        gm.carregaPrograma(prog, tabela);
        PCB pcb = new PCB(nome, tabela);
        prontos.add(pcb);
        return pcb;
    }

    public void desalocaProcesso(int id) {
        PCB alvo = proc(id);
        if (alvo == null)
            return;
        gm.desaloca(alvo.tabela);
        prontos.remove(alvo);
        if (rodando == alvo)
            rodando = null;
    }

    public PCB proc(int id) {
        for (PCB p : prontos)
            if (p.id == id)
                return p;
        if (rodando != null && rodando.id == id)
            return rodando;
        return null;
    }

    public List<PCB> lista() {
        List<PCB> l = new ArrayList<>(prontos);
        if (rodando != null)
            l.add(rodando);
        return l;
    }

    public void exec(int id) {
        PCB p = proc(id);
        if (p == null) {
            System.out.println("Processo não encontrado");
            return;
        }
        rodando = p;
        System.out.println("Executando " + p);
    }
}
