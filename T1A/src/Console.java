import java.io.InputStream;
import java.util.Scanner;

public class Console {
    private final ProcessManager gp;
    private final GM gm;
    private boolean trace = false; // flag para controlar o modo trace

    public Console(ProcessManager gp, GM gm) {
        this.gp = gp;
        this.gm = gm;
    }

    public void loop(InputStream in) {
        try (Scanner sc = new Scanner(in)) {
            OUTER: while (true) {
                System.out.print("> ");
                String cmd = sc.next();
                try {
                    switch (cmd) {
                        case "new" -> {
                            String nome = sc.next();
                            PCB pcb = gp.criaProcesso(nome);
                            if (pcb == null) {
                                System.out.println("Falha: sem memória.");
                            } else {
                                System.out.println("Criado: " + pcb);
                                if (trace) {
                                    System.out
                                            .println("[TRACE] Processo " + pcb.id + " criado com prog=" + pcb.nomeProg);
                                }
                            }
                        }
                        case "rm" -> {
                            int id = sc.nextInt();
                            gp.desalocaProcesso(id);
                            System.out.println("Removido id=" + id);
                            if (trace) {
                                System.out.println("[TRACE] Processo " + id + " desalocado.");
                            }
                        }
                        case "ps" -> {
                            for (PCB p : gp.lista()) {
                                System.out.println(p);
                            }
                        }
                        case "dump" -> {
                            int id = sc.nextInt();
                            PCB p = gp.proc(id);
                            if (p == null) {
                                System.out.println("Processo não existe.");
                            } else {
                                for (int i = 0; i < 10; i++) {
                                    int valor = gm.read(p.tabela, i);
                                    System.out.println("L[" + i + "] = " + valor);
                                }
                                if (trace) {
                                    System.out.println("[TRACE] Dump feito do processo " + id);
                                }
                            }
                        }
                        case "exec" -> {
                            int id = sc.nextInt();
                            gp.exec(id);
                            if (trace) {
                                System.out.println("[TRACE] Exec chamado para processo " + id);
                            }
                        }
                        case "traceOn" -> {
                            trace = true;
                            System.out.println("Trace ativado.");
                        }
                        case "traceOff" -> {
                            trace = false;
                            System.out.println("Trace desativado.");
                        }
                        case "dumpM" -> {
                            int ini = sc.nextInt();
                            int fim = sc.nextInt();

                            Memory m = ((PagingMemoryManager) gm).memoria();

                            for (int ea = ini; ea <= fim; ea++) {
                                System.out.println("M[" + ea + "] = " + m.readFisico(ea));
                            }

                            if (trace) {
                                System.out.println("[TRACE] Dump de memória física de " + ini + " até " + fim);
                            }
                        }
                        case "exit" -> {
                            break OUTER;
                        }
                        default -> System.out.println("Comando desconhecido.");
                    }
                } catch (MemoryAccessException e) {
                    System.out.println("Erro de memória: " + e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
        }
    }
}
