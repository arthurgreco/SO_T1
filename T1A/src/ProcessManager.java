import java.util.*;

public class ProcessManager {
    private final GM gm;
    private final Map<String, int[]> repo;
    private final List<PCB> prontos = new LinkedList<>();
    private PCB rodando = null;

    private int quantum = 3; // fatia de tempo padrão (em instruções)

    public ProcessManager(GM gm, Map<String, int[]> repo) {
        this.gm = gm;
        this.repo = repo;
    }

    public void setQuantum(int q) {
        if (q <= 0) throw new IllegalArgumentException("Delta/quantum deve ser > 0");
        this.quantum = q;
    }

    public PCB criaProcesso(String nome) {
        int[] prog = repo.get(nome);
        if (prog == null) {
            System.out.println("Programa inexistente: " + nome);
            return null;
        }
        int[] tabela = gm.aloca(prog.length);
        gm.carregaPrograma(prog, tabela);

        PCB pcb = new PCB(nome, tabela, prog.length);
        prontos.add(pcb);
        System.out.println("Criado " + pcb);
        return pcb;
    }

    private PCB proc(int id) {
        if (rodando != null && rodando.id == id) return rodando;
        for (PCB p : prontos) if (p.id == id) return p;
        return null;
    }

    public void rm(int id) {
        if (rodando != null && rodando.id == id) {
            gm.desaloca(rodando.tabela);
            System.out.println("Removido " + rodando);
            rodando = null;
            return;
        }
        Iterator<PCB> it = prontos.iterator();
        while (it.hasNext()) {
            PCB p = it.next();
            if (p.id == id) {
                gm.desaloca(p.tabela);
                it.remove();
                System.out.println("Removido " + p);
                return;
            }
        }
        System.out.println("Processo não encontrado");
    }

    public List<PCB> prontos() {
        List<PCB> l = new ArrayList<>(prontos);
        if (rodando != null) l.add(0, rodando);
        return l;
    }

    // Execução sem preempção (mantido para compatibilidade)
    public void exec(int id) {
        PCB p = proc(id);
        if (p == null) {
            System.out.println("Processo não encontrado");
            return;
        }
        rodando = p;
        System.out.println("Executando " + p + " (sem RR)");
        while (p.pc < p.progLen) {
            int val = gm.read(p.tabela, p.pc);
            System.out.println("  instr@" + p.pc + " = " + val);
            p.pc++;
        }
        System.out.println("[EXIT] Processo " + p.id + " terminou.");
        gm.desaloca(p.tabela);
        rodando = null;
    }

    // ===== ROUND-ROBIN =====
    public void execRR(int delta) {
        if (delta <= 0) delta = this.quantum;
        this.quantum = delta; // fixa o quantum atual

        while (!prontos.isEmpty() || rodando != null) {
            if (rodando == null) {
                rodando = prontos.remove(0);
            }

            PCB p = rodando;
            System.out.println("\n[CPU] Rodando " + p + " (quantum=" + delta + ")");

            int steps = 0;
            boolean terminou = false;

            while (steps < delta) {
                if (p.pc >= p.progLen) {
                    terminou = true;
                    break;
                }
                int val = gm.read(p.tabela, p.pc);
                System.out.println("  instr@" + p.pc + " = " + val);
                p.pc++;
                steps++;
            }

            if (terminou || p.pc >= p.progLen) {
                System.out.println("[EXIT] Processo " + p.id + " terminou.");
                gm.desaloca(p.tabela);
                rodando = null;
            } else {
                System.out.println("[TIMER] Preempção: " + p + " (pc=" + p.pc + ")");
                prontos.add(p);
                rodando = null;
            }
        }

        System.out.println("\n[FIM] Não há mais processos prontos.");
    }
}
